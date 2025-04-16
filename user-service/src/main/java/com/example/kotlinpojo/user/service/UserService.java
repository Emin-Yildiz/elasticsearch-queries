package com.example.kotlinpojo.user.service;

import com.example.global.kafka.NotificationSendEvent;
import com.example.global.kafka.NotificationType;
import com.example.kotlinpojo.config.KafkaConfig;
import com.example.kotlinpojo.domain.exception.exceptions.AlreadyAvailableException;
import com.example.kotlinpojo.domain.exception.exceptions.NotAvailableException;
import com.example.kotlinpojo.kafka.producer.MessageProducer;
import com.example.kotlinpojo.role.Role;
import com.example.kotlinpojo.role.service.RoleService;
import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.mapper.UserMapper;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.model.request.UserUpdateRequestModel;
import com.example.kotlinpojo.user.model.response.UserResponseModel;
import com.example.kotlinpojo.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final MessageProducer messageProducer;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleService roleService, MessageProducer messageProducer) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.messageProducer = messageProducer;
    }

    public UserResponseModel saveUser(UserSaveRequestModel userSaveRequestModel) {
        LOGGER.info("[Save User Service] User Save Starting. Username: {}", userSaveRequestModel.userName());
        checkExistUserNameAndMail(userSaveRequestModel.userName(),userSaveRequestModel.mail());
        User user = userMapper.userSaveRequestModelToUser(userSaveRequestModel);
        user = userRepository.save(user);
        LOGGER.info("[Save User Service] User Save Completed. Username: {}", userSaveRequestModel.userName());
        sendMessageToKafka(KafkaConfig.NOTIFICATION_SEND_TOPIC,new NotificationSendEvent(
                user.getMail(),
                "User Created",
                "your user has been created please login to the system",
                NotificationType.MAIL,
                LocalDateTime.now()
                ));
        return userMapper.userToUserResponseModel(user);
    }

    public UserResponseModel updateUser(UserUpdateRequestModel userUpdateRequestModel, UUID userId) {
        LOGGER.info("[Update User Service] User Update Started. Username: {}", userUpdateRequestModel.userName());

        User user = findUserById(userId);
        checkExistUserNameAndMail(userUpdateRequestModel.userName(),userUpdateRequestModel.mail());
        Role role = userUpdateRequestModel.roleId() == null ? user.getRole() : roleService.getRoleById(userUpdateRequestModel.roleId());
        updateUserField(user,userUpdateRequestModel);
        user.setRole(role);
        user = userRepository.save(user);
        LOGGER.info("[Update User Service] User Update Completed. Username: {}", userUpdateRequestModel.userName());

        return userMapper.userToUserResponseModel(user);
    }

    private void updateUserField(User user, UserUpdateRequestModel userUpdateRequestModel) {
        user.setMail(userUpdateRequestModel.mail());
        user.setPassword(userUpdateRequestModel.password());
        user.setUsername(userUpdateRequestModel.userName());
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public void deleteUser(UUID userId) {
        LOGGER.info("[Delete User Service] User Delete Started. UserId: {}", userId);
        User user = findUserById(userId);
        user.setIsActive(Boolean.FALSE);
        userRepository.save(user);
        LOGGER.info("[Delete User Service] User Delete Completed. UserId: {}", userId);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotAvailableException(String.format("User Not Found. ID: %s", userId)));
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new NotAvailableException(String.format("User Not Found. Username: %s", userName)));
    }

    public User findUserByMail(String mail) {
        return userRepository.findByMail(mail).orElseThrow(() -> new NotAvailableException(String.format("User Not Found. Mail: %s", mail)));
    }

    private void checkExistUserNameAndMail(String userName, String mail) {
        boolean isExist = userRepository.existsUserByUsernameOrMail(userName,mail);
        if (isExist) throw new AlreadyAvailableException("Username '" + userName + "' or Mail '" + mail + "' is exist");
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findUserByUserName(userName);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    private void sendMessageToKafka(String topicName, NotificationSendEvent notificationSendEvent){
        messageProducer.produceNotificationSendEvent(topicName, notificationSendEvent);
    }

}

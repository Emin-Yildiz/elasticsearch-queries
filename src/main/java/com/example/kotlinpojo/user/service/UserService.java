package com.example.kotlinpojo.user.service;

import com.example.kotlinpojo.domain.audit.Audit;
import com.example.kotlinpojo.domain.exception.exceptions.AlreadyAvailableException;
import com.example.kotlinpojo.domain.exception.exceptions.NotAvailableException;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    public UserResponseModel saveUser(UserSaveRequestModel userSaveRequestModel) {
        LOGGER.info("[Save User Service] User Save Starting. Username: {}", userSaveRequestModel.userName());
        checkExistUserNameAndMail(userSaveRequestModel.userName(),userSaveRequestModel.mail());
        User user = userMapper.userSaveRequestModelToUser(userSaveRequestModel);
        user = userRepository.save(user);
        LOGGER.info("[Save User Service] User Save Completed. Username: {}", userSaveRequestModel.userName());
        return userMapper.userToUserResponseModel(user);
    }

    public UserResponseModel updateUser(UserUpdateRequestModel userUpdateRequestModel, UUID userId) {
        LOGGER.info("[Update User Service] User Update Started. Username: {}", userUpdateRequestModel.userName());

        User user = findUserById(userId);
        checkExistUserNameAndMail(userUpdateRequestModel.userName(),userUpdateRequestModel.mail());
        Role role = userUpdateRequestModel.roleId() == null ? user.getRole() : roleService.getRoleById(userUpdateRequestModel.roleId());

        user = user.copy(
                user.getId(),
                userUpdateRequestModel.mail(),
                userUpdateRequestModel.userName(),
                user.getPassword(),
                role,
                user.getAudit()
                );

        user = userRepository.save(user);
        LOGGER.info("[Update User Service] User Update Completed. Username: {}", userUpdateRequestModel.userName());

        return userMapper.userToUserResponseModel(user);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public void deleteUser(UUID userId) {
        LOGGER.info("[Delete User Service] User Delete Started. UserId: {}", userId);
        User user = findUserById(userId);
        Audit userAudit = user.getAudit();

        userAudit = userAudit.copy(
                userAudit.getCreationDate(),
                userAudit.getLastModifiedDate(),
                false);

        user = user.copy(
                user.getId(),
                user.getMail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                userAudit
        );

        userRepository.save(user);
        LOGGER.info("[Delete User Service] User Delete Completed. UserId: {}", userId);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotAvailableException(String.format("User Not Found. ID: %s",userId)));
    }

    private void checkExistUserNameAndMail(String userName, String mail) {
        boolean isExist = userRepository.existsUserByUsernameOrMail(userName,mail);
        if (isExist) throw new AlreadyAvailableException("Username '" + userName + "' or Mail '" + mail + "' is exist");
    }


}

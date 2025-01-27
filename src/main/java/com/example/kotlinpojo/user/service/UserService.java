package com.example.kotlinpojo.user.service;

import com.example.kotlinpojo.domain.exception.exceptions.AlreadyAvailableException;
import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.mapper.UserMapper;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.model.response.UserResponseModel;
import com.example.kotlinpojo.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseModel saveUser(UserSaveRequestModel userSaveRequestModel) {
        checkExistUserNameAndMail(userSaveRequestModel.userName(),userSaveRequestModel.mail());
        User user = userMapper.userSaveRequestModelToUser(userSaveRequestModel);
        user = userRepository.save(user);
        return userMapper.userToUserResponseModel(user);
    }

    private void checkExistUserNameAndMail(String userName, String mail) {
        boolean isExist = userRepository.existsUserByUsernameOrMail(userName,mail);
        if (isExist) throw new AlreadyAvailableException("Username '" + userName + "' or Mail '" + mail + "' is exist");
    }


}

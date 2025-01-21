package com.example.kotlinpojo.user.service;

import com.example.kotlinpojo.domain.exception.exceptions.AlreadyAvailableException;
import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.model.response.UserResponseModel;
import com.example.kotlinpojo.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public UserResponseModel saveUser(UserSaveRequestModel userSaveRequestModel) {
        checkExistUserName(userSaveRequestModel.userName());
        User user = objectMapper.convertValue(userSaveRequestModel, User.class);
        return null;
    }

    private void checkExistUserName(String userName) {
        boolean isExist = userRepository.existsUserByUsername(userName);
        if (isExist) {
            throw new AlreadyAvailableException("Username " + userName + " is exist");
        }
    }


}

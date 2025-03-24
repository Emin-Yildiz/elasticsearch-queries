package com.example.kotlinpojo.user.mapper;

import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.model.response.UserResponseModel;

public interface UserMapper {

    User userSaveRequestModelToUser(UserSaveRequestModel userSaveRequestModel);

    UserResponseModel userToUserResponseModel(User user);
}

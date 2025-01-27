package com.example.kotlinpojo.user.mapper;

import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.model.request.UserSaveRequestModel;
import com.example.kotlinpojo.user.model.response.UserResponseModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapperDecorator implements UserMapper {

    @Override
    public User userSaveRequestModelToUser(UserSaveRequestModel userSaveRequestModel) {
        return new User.UserBuilder()
                .mail(userSaveRequestModel.mail())
                .username(userSaveRequestModel.userName())
                .password(userSaveRequestModel.password())
                .build();
    }

    @Override
    public UserResponseModel userToUserResponseModel(User user) {
        return new UserResponseModel(user.getMail(), user.getUsername());
    }
}

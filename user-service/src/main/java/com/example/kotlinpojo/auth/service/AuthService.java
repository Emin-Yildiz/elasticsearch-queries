package com.example.kotlinpojo.auth.service;

import com.example.kotlinpojo.auth.model.request.LoginRequestModel;
import com.example.kotlinpojo.jwt.JwtService;
import com.example.kotlinpojo.jwt.model.JwtCreateModel;
import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class.getName());

    private final JwtService jwtService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequestModel loginRequestModel) {
        String mail = loginRequestModel.email();
        String password = loginRequestModel.password();
        User user = userService.findUserByMail(mail);
        checkCredentials(user, password);
        return jwtService.generateTokenWithClaims(new JwtCreateModel(user.getId(), user.getUsername(), user.getMail(), user.getRole().getName()));
    }

    private void checkCredentials(User user, String password) {
        LOGGER.info("[Check Credentials] Check User ({}) credentials",user.getMail());
        boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isPasswordMatch) throw new BadCredentialsException("Invalid email or password");
    }
}

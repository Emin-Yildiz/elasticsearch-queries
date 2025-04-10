package org.example.searchservice.user.service;

import org.example.searchservice.domain.annotation.LogExecutionTime;
import org.example.searchservice.domain.exception.exception.NotAvailableException;
import org.example.searchservice.user.User;
import org.example.searchservice.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @LogExecutionTime
    public User getUserById(UUID id) {
        return userRepository.findUserById(id).orElseThrow(() -> new NotAvailableException(String.format("User Not Found. Id: %s", id)));
    }
}

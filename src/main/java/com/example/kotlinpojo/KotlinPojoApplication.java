package com.example.kotlinpojo;

import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class KotlinPojoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KotlinPojoApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(UserRepository userRepository) {
		return args -> {
			if (userRepository.count() == 0) {
				User user = new User(UUID.randomUUID(),"admin@admin.com","admin","admin");
				userRepository.save(user);
			}
		};
	}

}

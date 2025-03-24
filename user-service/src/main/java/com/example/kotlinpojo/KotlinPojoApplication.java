package com.example.kotlinpojo;

import com.example.kotlinpojo.role.Role;
import com.example.kotlinpojo.role.repository.RoleRepository;
import com.example.kotlinpojo.user.User;
import com.example.kotlinpojo.user.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class KotlinPojoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KotlinPojoApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {
			Role adminRole = Role.builder().name("ROLE_ADMIN").build();
			if (roleRepository.count() == 0){
				adminRole = roleRepository.save(adminRole);
			}
			if (userRepository.count() == 0) {
				User user = new User(UUID.randomUUID(),"admin@admin.com","admin",passwordEncoder.encode("admin"),adminRole);
				userRepository.save(user);
			}
		};
	}

}

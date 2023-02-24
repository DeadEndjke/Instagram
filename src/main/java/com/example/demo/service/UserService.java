package com.example.demo.service;

import com.example.demo.entity.enums.ERole;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;

@Service
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {}", signUpRequest.getEmail());
            return userRepository.save(user);
        }catch (Exception e){
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("the user " + user.getUsername() + " already exist");
        }
    }
}

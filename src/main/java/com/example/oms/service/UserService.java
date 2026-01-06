package com.example.oms.service;

import com.example.oms.entity.UserEntity;
import com.example.oms.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserEntity createUser(String userName, String userEmail){
        UserEntity user = new UserEntity(userName, userEmail);
        return userRepository.save(user);
    }

    public UserEntity getById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}

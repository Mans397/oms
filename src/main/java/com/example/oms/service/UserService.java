package com.example.oms.service;

import com.example.oms.entity.UserEntity;
import com.example.oms.exception.UserNotFoundException;
import com.example.oms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<UserEntity> listUsers(){
        return userRepository.findAll();
    }

    public UserEntity updateUser(Long id, String name, String email) {
        UserEntity user = getById(id);
        if (name != null && !name.isBlank()) user.rename(name);
        if (email != null && !email.isBlank()) user.changeEmail(email);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        UserEntity user = getById(id);
        userRepository.delete(user);
    }
}

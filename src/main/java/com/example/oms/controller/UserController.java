package com.example.oms.controller;

import com.example.oms.dto.CreateUserRequest;
import com.example.oms.dto.UpdateUserRequest;
import com.example.oms.dto.UserResponse;
import com.example.oms.entity.UserEntity;
import com.example.oms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request){
        UserEntity user = userService.createUser(request.name(), request.email());
        return toResponse(user);
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id){
        return toResponse(userService.getById(id));
    }

    @GetMapping
    public List<UserResponse> list(){
        return userService.listUsers().stream().map(this::toResponse).toList();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request){
        UserEntity user = userService.updateUser(id, request.name(), request.email());
        return toResponse(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.deleteUser(id);
    }

    private UserResponse toResponse(UserEntity user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}

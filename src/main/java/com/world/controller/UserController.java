package com.world.controller;

import com.world.exception.CustomException;
import com.world.request.user.CreateRequest;
import com.world.request.user.LoginRequest;
import com.world.request.user.UpdateRequest;
import com.world.response.user.UserResponse;
import com.world.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;




    @PostMapping("/users")
    public UserResponse doPost(@Valid @RequestBody CreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String token = userService.register(request);
        return UserResponse.of(request, token);
    }

    @PostMapping("/users/login")
    public UserResponse doPost(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return userService.login(request);
    }

    @GetMapping("/user")
    public UserResponse doGet() {
        return userService.getCurrUser();
    }


    @PutMapping("/user")
    public UserResponse doPut(@Valid @RequestBody UpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return userService.updateUser(request);
    }


}

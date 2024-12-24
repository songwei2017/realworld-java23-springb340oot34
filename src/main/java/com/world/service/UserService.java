package com.world.service;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.world.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.world.request.user.CreateRequest;
import com.world.request.user.LoginRequest;
import com.world.request.user.UpdateRequest;
import com.world.response.user.UserResponse;


public interface UserService extends IService<User> {

    String register(CreateRequest createRequest);


    UserResponse getCurrUser();


    UserResponse updateUser(UpdateRequest request);

    UserResponse login(LoginRequest loginRequest);

    User getUser(SFunction<User, ?> col, String val);

    Long getUserId();

    User getUserById(Long userId);

    User getUserByUserName(String val);

    String encodePassword(String password);

    boolean checkPassword(String password, String encodedPassword);
}

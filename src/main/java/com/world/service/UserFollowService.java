package com.world.service;


import com.world.entity.UserFollow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.world.response.user.ProfileResponse;


public interface UserFollowService extends IService<UserFollow> {
    ProfileResponse getProfile(String username);

    ProfileResponse getProfile(Long userId);

    ProfileResponse getProfile();


    ProfileResponse follow(String username);

    ProfileResponse unFollow(String username);

}

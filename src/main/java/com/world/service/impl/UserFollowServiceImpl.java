package com.world.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.world.entity.User;
import com.world.entity.UserFollow;
import com.world.exception.CustomException;
import com.world.mapper.UserFollowMapper;
import com.world.response.user.ProfileResponse;
import com.world.service.UserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.world.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {


    private final UserService userService;

    public ProfileResponse getProfile(String username) {
        Long userId = userService.getUserId();
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw  new CustomException("用户不存在");
        }
        boolean following;
        if (user.getId().equals(userId)) {
            following = false;
        }else{
            UserFollow userFollow = getOne(new QueryWrapper<UserFollow>().eq("follower_id", userId).eq("following_id",user.getId() ));
            following = userFollow != null;
        }
        return ProfileResponse.of(user,following);
    }

    public ProfileResponse getProfile(Long userId) {
        Long currUserId = userService.getUserId();
        User user = userService.getUserById(userId);
        if (user == null) {
            throw  new CustomException("用户不存在");
        }
        boolean following;
        if (currUserId.equals(userId)) {
            following = false;
        }else{
            UserFollow userFollow = getOne(new QueryWrapper<UserFollow>().eq("follower_id", currUserId).eq("following_id",user.getId() ));
            following = userFollow != null;
        }
        return ProfileResponse.of(user,following);
    }

    public ProfileResponse getProfile() {
        User user = userService.getUserById(userService.getUserId());
        if (user == null) {
            throw  new CustomException("用户不存在");
        }
        boolean  following = false;

        return ProfileResponse.of(user,following);
    }


    public ProfileResponse follow(String username) {
        Long userId = userService.getUserId();
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw  new CustomException("用户不存在");
        }
        if (user.getId().equals(userId)) {
            throw  new CustomException("不能关注自己");
        }else{
            UserFollow userFollow = getOne(new QueryWrapper<UserFollow>().eq("follower_id", userId).eq("following_id",user.getId() ));
            if (userFollow != null) {
                throw  new CustomException("已关注");
            }
        }
        UserFollow userFollow = new UserFollow();
        userFollow.setFollowerId(userId);
        userFollow.setFollowingId(user.getId());
        userFollow.setCreatedAt(new Date());
        save(userFollow);
        return ProfileResponse.of(user,true);
    }

    public ProfileResponse unFollow(String username) {
        Long userId = userService.getUserId();
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw  new CustomException("用户不存在");
        }
        if (user.getId().equals(userId)) {
            throw  new CustomException("不能取关自己");
        }else{
            UserFollow userFollow = getOne(new QueryWrapper<UserFollow>().eq("follower_id", userId).eq("following_id",user.getId() ));
            if (userFollow == null) {
                return ProfileResponse.of(user,false);
            }
            removeById(userFollow.getId());
        }
        return ProfileResponse.of(user,false);
    }


}

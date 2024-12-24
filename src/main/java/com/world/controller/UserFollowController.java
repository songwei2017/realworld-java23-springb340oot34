package com.world.controller;

import com.world.response.user.ProfileResponse;
import com.world.service.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserFollowController {

    private final UserFollowService userFollowService;


    @GetMapping("/profiles/{username}")
    public ProfileResponse getProfile(@PathVariable String username) {
        return userFollowService.getProfile(username);
    }

    @PostMapping("/profiles/{username}/follow")
    public ProfileResponse follow(@PathVariable String username) {
        return userFollowService.follow(username);
    }


    @DeleteMapping("/profiles/{username}/follow")
    public ProfileResponse unFollow(@PathVariable String username) {
        return userFollowService.unFollow(username);
    }


}

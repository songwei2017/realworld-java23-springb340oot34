package com.world.response.user;

import com.world.entity.User;
import com.world.request.user.CreateRequest;

public record UserResponse(Param user) {
    public static UserResponse of(CreateRequest request, String token) {
        return new UserResponse( new Param(token,request.getUser().getEmail(), request.getUser().getUsername(),"","") );
    }

    public static UserResponse ofUser(User user, String token) {
        return new UserResponse( new Param(token,user.getEmail(), user.getUsername(),user.getBio() ,user.getImageUrl() ) );
    }

    public record Param(String token, String email,String username, String bio, String image){}

}

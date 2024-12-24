package com.world.response.user;

import com.world.entity.User;

public record ProfileResponse(Param profile) {

    public static ProfileResponse of(User user , Boolean following) {
        return new ProfileResponse( new Param( user.getUsername(), user.getImageUrl(), user.getBio(), following) );
    }

    public record Param (
            String username,
            String image ,
            String bio,
            boolean following
    ){}
}

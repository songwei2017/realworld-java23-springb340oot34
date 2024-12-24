package com.world.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UpdateRequest(@Valid Params user) {
    public UpdateRequest(String email, String bio, String image, String username, String password) {
        this(new Params(email, bio, image, username, password));
    }
    public record Params(

            String email,

            String bio,

            String image,

            String username,

            String password

    ) {}
}
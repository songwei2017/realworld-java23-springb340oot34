package com.world.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(@Valid Params user) {

    public LoginRequest(  String email, String password) {
        this(new Params(email, password));
    }

    public record Params(
            @Email @NotBlank
            String email,
            @NotNull @NotBlank
            String password
    ) {}
}
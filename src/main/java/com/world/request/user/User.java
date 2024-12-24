package com.world.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
    @NotNull(message = "username cannot be null ")
    @NotBlank(message = "username cannot be blank ")
    @Size(min = 1,max = 30)
    private String username;

    @NotNull(message = "username cannot be null ")
    @NotBlank(message = "password cannot be blank ")
    @Size(min = 1,max = 30)
    private String password;

    @NotNull(message = "email cannot be null ")
    @NotBlank(message = "email cannot be blank ")
    @Email
    @Size(min = 1,max = 30)
    private String email;
}

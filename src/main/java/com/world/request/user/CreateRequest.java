package com.world.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRequest {
    @Valid
    User user;
}
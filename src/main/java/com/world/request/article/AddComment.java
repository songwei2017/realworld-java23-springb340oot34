package com.world.request.article;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddComment(@Valid Params comment) {
    public AddComment(String body){
        this(new Params(body));
    }
    public record Params(
            @NotBlank
            @NotNull
            @Size(min = 1, max = 100)
            String body
    ){}
}

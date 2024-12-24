package com.world.request.article;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateArticle(@Valid Params article) {
    public CreateArticle(
            String title,
            String description,
            String body,
            String[] tagList
    ){
        this(new Params(title,description,body,tagList));
    }
    public record Params (
            @Size(max=50,min=1)
            String title,
            @Size(max=50,min=1)
            String description,
            @Size(max=1000,min=1)
            String body,

            String[] tagList
    ){}
}

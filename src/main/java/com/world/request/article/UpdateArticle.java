package com.world.request.article;

public record UpdateArticle(Params article) {
    public UpdateArticle(
            String title,
            String description,
            String body,
            String[] tagList
    ){
        this(new Params(title,description,body,tagList));
    }
    public record Params (
            String title,
            String description,
            String body,
            String[] tagList
    ){}
}

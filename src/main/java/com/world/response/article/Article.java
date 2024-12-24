package com.world.response.article;

import com.world.response.user.ProfileResponse;

import java.util.Date;


public record Article(
        Long id,
        String slug,
        String title,
        String description,
        String body,
        Date createdAt,
        Date updatedAt,
        int favoritesCount,
        boolean favorited,
        String[] tagList,
        ProfileResponse.Param author
) {
    public static Article of(com.world.entity.Article article, String[] tagList, ProfileResponse author, boolean favorited) {
        return new Article(
                article.getId(),
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getFavoritesCount(),
                favorited,
                tagList,
                author.profile()
        );
    }
}

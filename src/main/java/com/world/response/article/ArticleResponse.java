package com.world.response.article;

import com.world.response.user.ProfileResponse;

public record ArticleResponse(Article article) {
    public static ArticleResponse of(com.world.entity.Article article, String[] tagList, ProfileResponse author, boolean favorited) {
        return new ArticleResponse(
                Article.of(article, tagList, author, favorited)
        );
    }

}

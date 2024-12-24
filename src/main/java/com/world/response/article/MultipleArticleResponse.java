package com.world.response.article;

import java.util.List;

public record MultipleArticleResponse(List<Article> articles, int articlesCount) {

    public MultipleArticleResponse {
        articlesCount = articles.size();
    }
    public MultipleArticleResponse(List<Article> articles) {
        this(articles, articles.size());
    }
}

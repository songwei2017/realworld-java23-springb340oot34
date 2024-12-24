package com.world.service;

import com.world.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.world.request.article.CreateArticle;
import com.world.response.article.ArticleResponse;
import com.world.response.article.MultipleArticleResponse;

import java.util.List;


public interface ArticleService extends IService<Article> {
    ArticleResponse createOne(CreateArticle request);


    ArticleResponse updateOne(CreateArticle request, String slug);

    ArticleResponse getOneBySlug(String slug, Boolean favoriteStatus);


    boolean removeBySlug(String slug);

    boolean checkFavoriteStatus(Long articleId, Long userId);

    MultipleArticleResponse getArticlesFeed(int offset, int limit);

    MultipleArticleResponse getUserAritcles(
            String tag,
            String author,
            String favorited,
            int offset,
            int limit
    );

    MultipleArticleResponse makeMultipleArticleResponse(List<com.world.entity.Article> al, Long userId);


    ArticleResponse favorite(String slug);

    ArticleResponse unFavorite(String slug);
}

package com.world.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.world.entity.ArticleFavorite;
import com.world.mapper.ArticleFavoriteMapper;
import com.world.response.article.ArticleResponse;
import com.world.service.ArticleFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ergou
 * @since 2024-12-19
 */
@Service
@RequiredArgsConstructor
public class ArticleFavoriteServiceImpl extends ServiceImpl<ArticleFavoriteMapper, ArticleFavorite> implements ArticleFavoriteService {

    public boolean checkFavoriteStatus(Long articleId, Long userId) {
        return  getFavorite(articleId,userId) != null;
    }

    public ArticleFavorite getFavorite(Long articleId, Long userId) {
        return getOne(Wrappers.<ArticleFavorite>lambdaQuery().eq(ArticleFavorite::getArticleId, articleId).eq(ArticleFavorite::getUserId,userId));
    }

}

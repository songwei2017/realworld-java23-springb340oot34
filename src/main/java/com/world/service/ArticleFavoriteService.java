package com.world.service;

import com.world.entity.ArticleFavorite;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ArticleFavoriteService extends IService<ArticleFavorite> {

    boolean checkFavoriteStatus(Long articleId, Long userId);

    ArticleFavorite getFavorite(Long articleId, Long userId);
}

package com.world.service;

import com.world.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleTagService extends IService<ArticleTag> {
    boolean createBatch(List<ArticleTag> articleTags);

    Long[] getTagIds(Long articleId);

    String[] getTagNames(Long articleId);


    boolean removeBatch(List<Long> tagIds, Long articleId);
}

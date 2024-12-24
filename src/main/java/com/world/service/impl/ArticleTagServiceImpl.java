package com.world.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.world.entity.ArticleTag;
import com.world.mapper.ArticleTagMapper;
import com.world.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ergou
 * @since 2024-12-19
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    public boolean createBatch(List<ArticleTag> articleTags) {
        return saveBatch(articleTags);
    }
    public Long[] getTagIds(Long articleId) {
        List<ArticleTag> list = list(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, articleId));
        if (list == null) {
            return null;
        }
        return list.stream().map(ArticleTag::getTagId).distinct().toArray(Long[]::new);
    }

    public String[] getTagNames(Long articleId) {
        List<ArticleTag> list = list(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, articleId));
        if (list == null) {
            return null;
        }
        return list.stream().map(ArticleTag::getTagName).distinct().toArray(String[]::new);
    }


    public boolean removeBatch(List<Long> tagIds, Long articleId) {
        return remove(
                Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, articleId).in(ArticleTag::getTagId, tagIds)
        );
    }
}
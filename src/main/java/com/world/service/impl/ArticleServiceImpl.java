package com.world.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.world.entity.Article;
import com.world.entity.ArticleFavorite;
import com.world.entity.ArticleTag;
import com.world.exception.CustomException;
import com.world.mapper.ArticleMapper;
import com.world.mapper.joinquery.ArticleJoinQueryMapper;
import com.world.mapper.subquery.ArticleSubQuery;
import com.world.request.article.CreateArticle;
import com.world.response.article.ArticleResponse;
import com.world.response.article.MultipleArticleResponse;
import com.world.response.user.ProfileResponse;
import com.world.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ergou
 * @since 2024-12-19
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, com.world.entity.Article> implements ArticleService {

    private final UserService userService;
    private final UserFollowService userFollowService;
    private final TagService tagService;
    private final ArticleTagService articleTagService;
    private final ArticleFavoriteService articleFavoriteService;
    private final ArticleSubQuery articleSubQuery;
    private final ArticleJoinQueryMapper articleJoinQueryMapper;
    private final UserFollowService userFollowServiceImpl;

    @Transactional
    public ArticleResponse createOne(CreateArticle request) {
        Long userId = userService.getUserId();
        com.world.entity.Article articleEntity = new com.world.entity.Article();
        articleEntity.setSlug(StrUtil.replace(request.article().title().trim(), " ", "-"));
        articleEntity.setCreatedAt(new Date());
        articleEntity.setUpdatedAt(new Date());
        articleEntity.setTitle(request.article().title().trim());
        articleEntity.setContent(request.article().body().trim());
        articleEntity.setDescription(request.article().description().trim());
        articleEntity.setAuthorId(userId);
        articleEntity.setFavoritesCount(0);

        if (
                getOne(Wrappers.<com.world.entity.Article>lambdaQuery().eq(com.world.entity.Article::getTitle, articleEntity.getTitle())) != null) {
            throw new CustomException("标题已存在");
        }

        save(articleEntity);
        //处理tag
        String[] tagArr = request.article().tagList();
        if (ArrayUtil.isNotEmpty(tagArr)) {
            Long[] tagIdArr = tagService.createTag(tagArr);
            if (ArrayUtil.isNotEmpty(tagIdArr)) {
                List<ArticleTag> articleTagList = new ArrayList<>();
                for (int i = 0; i < tagArr.length; i++) {
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(articleEntity.getId());
                    articleTag.setTagName(tagArr[i]);
                    articleTag.setTagId(tagIdArr[i]);
                    articleTag.setCreatedAt(new Date());
                    articleTag.setUpdatedAt(new Date());
                    articleTagList.add(articleTag);
                }
                articleTagService.saveBatch(articleTagList);
            }
        }

        ProfileResponse author = userFollowService.getProfile();

        return ArticleResponse.of(articleEntity, request.article().tagList(), author, false);
    }


    @Transactional
    public ArticleResponse updateOne(CreateArticle request, String slug) {

        Long userId = userService.getUserId();

        com.world.entity.Article article = getOne(Wrappers.<com.world.entity.Article>lambdaQuery().eq(com.world.entity.Article::getSlug, slug.trim()));
        if (article == null || !article.getAuthorId().equals(userId)) {
            throw new CustomException("文章不存在或者不是本人所有");
        }
        article.setSlug(StrUtil.replace(slug.trim(), " ", "-"));
        article.setUpdatedAt(new Date());
        article.setTitle(request.article().title().trim());
        article.setContent(request.article().body().trim());
        article.setDescription(request.article().description().trim());

        com.world.entity.Article checkTitle = getOne(Wrappers.<com.world.entity.Article>lambdaQuery().eq(com.world.entity.Article::getTitle, article.getTitle()));
        if (checkTitle != null && !checkTitle.getId().equals(article.getId())) {
            throw new CustomException("标题已存在");
        }


        //获取旧的tag_ids
        Long[] oldTagIds = articleTagService.getTagIds(article.getId());

        updateById(article);
        //处理tag
        String[] tagArr = request.article().tagList();
        if (ArrayUtil.isNotEmpty(tagArr)) {
            Long[] tagIdArr = tagService.createTag(tagArr);
            if (ArrayUtil.isNotEmpty(tagIdArr)) {
                List<ArticleTag> articleTagList = new ArrayList<>();
                for (int i = 0; i < tagArr.length; i++) {
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(article.getId());
                    articleTag.setTagName(tagArr[i]);
                    articleTag.setTagId(tagIdArr[i]);
                    if (!ArrayUtil.contains(oldTagIds, tagIdArr[i])) {
                        articleTag.setCreatedAt(new Date());
                        articleTag.setUpdatedAt(new Date());
                        articleTagList.add(articleTag);
                    }
                }
                if (!articleTagList.isEmpty()) {
                    articleTagService.saveBatch(articleTagList);
                }
                //删除无效的tag
                Long[] removeIds = Arrays.stream(oldTagIds)
                        .filter(e -> Arrays.stream(tagIdArr).noneMatch(e::equals))
                        .toArray(Long[]::new);
                if (removeIds.length > 0) {
                    articleTagService.removeBatch(Arrays.asList(removeIds), article.getId());
                }
            }
        }

        ProfileResponse author = userFollowService.getProfile();

        return ArticleResponse.of(article, request.article().tagList(), author, checkFavoriteStatus(article.getId(), userId));
    }

    public ArticleResponse getOneBySlug(String slug,Boolean favoriteStatus) {
        com.world.entity.Article article = getOne(Wrappers.<com.world.entity.Article>lambdaQuery().eq(com.world.entity.Article::getSlug, slug.trim()));
        if (article == null) {
            throw new CustomException("文章不存在");
        }
        ProfileResponse author = userFollowService.getProfile(article.getAuthorId());
        String[] tagArr = articleTagService.getTagNames(article.getId());

        if(favoriteStatus == null){
            favoriteStatus = checkFavoriteStatus(article.getId(), userService.getUserId());
        }

        return ArticleResponse.of(article, tagArr, author, favoriteStatus );
    }


    @Transactional
    public boolean removeBySlug(String slug) {

        Article article = getOne(Wrappers.<Article>lambdaQuery().eq(Article::getSlug, slug.trim()));
        if (article == null) {
            return true;
        }
        removeById(article.getId());
        articleTagService.remove(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, article.getId()));
        articleFavoriteService.remove(Wrappers.<ArticleFavorite>lambdaQuery().eq(ArticleFavorite::getArticleId, article.getId()));

        //TODO 定时清理没用的 tag

        return true;
    }
    public boolean checkFavoriteStatus(Long articleId, Long userId) {
        return articleFavoriteService.checkFavoriteStatus(articleId, userId);
    }

    public MultipleArticleResponse getArticlesFeed(int offset, int limit) {
        Long userId = userService.getUserId();
        List<Article> al = articleSubQuery.getFavoriteArticleFeed(userId,limit,offset);
        return makeMultipleArticleResponse(al,userId);
    }

    public MultipleArticleResponse getUserAritcles(
            String tag,
            String author,
            String favorited,
            int offset,
            int limit
    ) {

        Long userId = userService.getUserId();
        List<Article> al;
        if (tag != null) {
            al = articleSubQuery.getArticlesByTagName(tag,limit,offset);
        }else if (author != null) {
            al = articleSubQuery.getArticlesByUsername(author,limit,offset);
        }else if (favorited != null) {
            al = articleJoinQueryMapper.getFavoriteArticleByUserName(favorited,limit,offset);
        }else{
            al = list(Wrappers.<Article>lambdaQuery().orderByDesc(Article::getId).last(" limit " + limit + " offset " + offset));
        }

        return makeMultipleArticleResponse(al,userId);
    }

    public MultipleArticleResponse makeMultipleArticleResponse(List<com.world.entity.Article> al , Long userId ) {
        return al.stream().map(article -> com.world.response.article.Article.of(
                article,
                articleTagService.getTagNames(article.getId()),
                userFollowServiceImpl.getProfile(article.getAuthorId()),
                checkFavoriteStatus(article.getId(), userId)
        )).collect(collectingAndThen(toList(), MultipleArticleResponse::new));
    }




    @Transactional
    public ArticleResponse favorite(String slug){
        ArticleResponse article = getOneBySlug(slug,true);
        Long userId = userService.getUserId();
        if(articleFavoriteService.getFavorite(article.article().id(), userId) != null){
            return article;
        }
        ArticleFavorite articleFavorite = new ArticleFavorite();
        articleFavorite.setUserId(userId);
        articleFavorite.setCreatedAt(new Date());
        articleFavorite.setUpdatedAt(new Date());
        articleFavorite.setArticleId(article.article().id());
        articleFavoriteService.save(articleFavorite);
        update(Wrappers.<Article>lambdaUpdate().eq(Article::getId, article.article().id()).setSql("favorites_count = favorites_count + 1 "));

        return article;
    }

    public ArticleResponse unFavorite(String slug){
        ArticleResponse article = getOneBySlug(slug,false);
        Long userId = userService.getUserId();
        ArticleFavorite articleFavorite = articleFavoriteService.getFavorite(article.article().id(), userId);
        if(articleFavorite == null){
            return article;
        }
        articleFavoriteService.removeById(articleFavorite.getId());
        update(Wrappers.<Article>lambdaUpdate().eq(Article::getId, article.article().id()).setSql("favorites_count = favorites_count - 1 "));

        return article;
    }
}

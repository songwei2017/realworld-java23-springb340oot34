package com.world.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.world.entity.ArticleComment;
import com.world.mapper.ArticleCommentMapper;
import com.world.request.article.AddComment;
import com.world.response.article.ArticleResponse;
import com.world.response.article.MultipleArticleResponse;
import com.world.response.comment.Comment;
import com.world.response.comment.CommentResponse;
import com.world.response.comment.MultipleCommentResponse;
import com.world.service.ArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.world.service.ArticleService;
import com.world.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

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
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {
    private final ArticleService articleService;
    private final UserService userService;

    public CommentResponse addComment(String slug , AddComment comment ) {
        ArticleResponse article = articleService.getOneBySlug(slug,null);
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(article.article().id());
        articleComment.setCreatedAt(new Date());
        articleComment.setUpdatedAt(new Date());
        articleComment.setContent(comment.comment().body());
        articleComment.setAuthorId(userService.getUserId());
        save(articleComment);
        return CommentResponse.of(articleComment,article.article().author());
    }

    public MultipleCommentResponse getComment(String slug) {
        ArticleResponse article = articleService.getOneBySlug(slug,null);
        List<ArticleComment> commentList = list(Wrappers.<ArticleComment>lambdaQuery().eq(ArticleComment::getArticleId, article.article().id()));
        return commentList.stream().map(comment -> Comment.of(comment,article.article().author())).collect(collectingAndThen(toList(), MultipleCommentResponse::new));
    }

    public boolean deleteComment(Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("author_id",userService.getUserId());
        return removeByMap(map);
    }
}

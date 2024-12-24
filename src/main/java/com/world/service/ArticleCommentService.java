package com.world.service;

import com.world.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.world.request.article.AddComment;
import com.world.response.comment.CommentResponse;
import com.world.response.comment.MultipleCommentResponse;


public interface ArticleCommentService extends IService<ArticleComment> {

    CommentResponse addComment(String slug, AddComment comment);

    MultipleCommentResponse getComment(String slug);

    boolean deleteComment(Long id);
}

package com.world.response.comment;

import com.world.entity.ArticleComment;
import com.world.response.user.ProfileResponse;

import java.util.Date;

public record Comment(Long id , Date createdAt , Date updatedAt , String body , ProfileResponse.Param author) {
    public static Comment of (ArticleComment comment, ProfileResponse.Param author) {
        return new Comment(comment.getId(),comment.getCreatedAt(),comment.getUpdatedAt(),comment.getContent(),author);
    }
}

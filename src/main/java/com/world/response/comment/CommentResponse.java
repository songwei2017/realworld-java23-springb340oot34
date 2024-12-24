package com.world.response.comment;

import com.world.entity.ArticleComment;
import com.world.response.user.ProfileResponse;

public record CommentResponse(Comment comment) {
    public static CommentResponse of (ArticleComment comment, ProfileResponse.Param profile) {
        return new CommentResponse( Comment.of(comment,profile) );
    }
}

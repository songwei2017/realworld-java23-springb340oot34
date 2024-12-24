package com.world.controller;

import com.world.exception.CustomException;
import com.world.request.article.AddComment;
import com.world.response.comment.CommentResponse;
import com.world.response.comment.MultipleCommentResponse;
import com.world.service.ArticleCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/articles/")
@RequiredArgsConstructor
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    @PostMapping("{slug}/comments")
    public CommentResponse doPost(@PathVariable String slug, @Valid @RequestBody AddComment comments, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return articleCommentService.addComment(slug, comments);
    }

    @GetMapping("{slug}/comments")
    public MultipleCommentResponse doGet(@PathVariable String slug) {
        return articleCommentService.getComment(slug);
    }

    @DeleteMapping("{slug}/comments/{id}")
    public boolean doDelete(@PathVariable String slug, @PathVariable Long id) {
        return articleCommentService.deleteComment(id);
    }
}

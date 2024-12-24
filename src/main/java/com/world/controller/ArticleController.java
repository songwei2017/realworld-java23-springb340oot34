package com.world.controller;

import com.world.exception.CustomException;
import com.world.request.article.CreateArticle;
import com.world.response.article.ArticleResponse;
import com.world.response.article.MultipleArticleResponse;
import com.world.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ArticleResponse doPost(@Valid @RequestBody CreateArticle request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return articleService.createOne(request);
    }

    @PutMapping("/articles/{slug}")
    public ArticleResponse doPut(@Valid @RequestBody CreateArticle request, BindingResult bindingResult, @PathVariable String slug) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return articleService.updateOne(request, slug);
    }

    @GetMapping("/articles/{slug}")
    public ArticleResponse doPut(@PathVariable String slug) {

        return articleService.getOneBySlug(slug, null);
    }

    @DeleteMapping("/articles/{slug}")
    public boolean doDelete(@PathVariable String slug) {
        return articleService.removeBySlug(slug);
    }

    @GetMapping("/articles")
    public MultipleArticleResponse getAllArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "favorited", required = false) String favorited,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ) {
        return articleService.getUserAritcles(tag, author, favorited, offset, limit);
    }

    @GetMapping("/articles/feed")
    public MultipleArticleResponse getArticlesFeed(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ) {
        return articleService.getArticlesFeed(offset, limit);
    }


    @PostMapping("/articles/{slug}/favorite")
    public ArticleResponse favorite(@PathVariable("slug") String slug) {
        return articleService.favorite(slug);
    }

    @DeleteMapping("/articles/{slug}/favorite")
    public ArticleResponse unFavorite(@PathVariable("slug") String slug) {
        return articleService.unFavorite(slug);
    }

}

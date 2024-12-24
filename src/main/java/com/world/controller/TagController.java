package com.world.controller;

import com.world.response.tag.TagsResponse;
import com.world.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("tags")
    public TagsResponse tags() {
        return tagService.getTags();
    }
}

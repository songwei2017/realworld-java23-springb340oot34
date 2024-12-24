package com.world.service;

import com.world.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.world.response.tag.TagsResponse;


public interface TagService extends IService<Tag> {
    Long[] createTag(String[] tags);

    TagsResponse getTags();
}

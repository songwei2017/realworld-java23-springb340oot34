package com.world.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.world.entity.Tag;
import com.world.mapper.TagMapper;
import com.world.response.article.MultipleArticleResponse;
import com.world.response.tag.TagsResponse;
import com.world.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collector;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    public Long[] createTag(String[] tags) {

        if (tags == null || tags.length == 0) {
            return null;
        }

        Long[] result = new Long[tags.length];

        for (int i = 0; i < tags.length; i++) {
            Tag tagEntity = getOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getName, tags[i]));
            if (tagEntity == null) {
                tagEntity = new Tag();
                tagEntity.setName(tags[i]);
                tagEntity.setCreatedAt(new Date());
                tagEntity.setUpdatedAt(new Date());
                save(tagEntity);
            }
            result[i] = tagEntity.getId();
        }
        return result;
    }

    public TagsResponse getTags() {
        return list().stream().map(Tag::getName).collect(collectingAndThen(toList(), TagsResponse::new));
    }
}

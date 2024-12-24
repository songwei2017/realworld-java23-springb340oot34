package com.world.mapper;

import com.world.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ergou
 * @since 2024-12-19
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
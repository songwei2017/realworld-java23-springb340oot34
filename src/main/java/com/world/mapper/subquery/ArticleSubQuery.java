package com.world.mapper.subquery;

import com.world.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleSubQuery {
    //根据 tagName 查询
    @Select(
        "SELECT * from article where id  in ( SELECT article_id from article_tag where tag_name = #{tagName}) order by id desc Limit  #{offset} , #{limit}" )
    List<Article> getArticlesByTagName(String tagName,int limit ,int offset );

    //根据作者查询
    @Select(
            "SELECT * from article where author_id = ( SELECT id from user where username = #{username}) order by id desc Limit  #{offset} , #{limit}" )
    List<Article> getArticlesByUsername(String username ,int limit ,int offset  );


    @Select(
            "SELECT * FROM article " +
                    " WHERE author_id in (SELECT following_id FROM user_follow WHERE follower_id = #{userId}) order by id desc Limit #{offset},#{limit}"
    )
    List<Article> getFavoriteArticleFeed(Long userId, int limit, int offset );





}

package com.world.mapper.joinquery;

import com.world.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleJoinQueryMapper {
    @Select(
                "SELECT a.* FROM article AS a INNER JOIN article_favorite AS af on  a.id = af.article_id " +
                    " WHERE af.user_id = (SELECT id FROM user WHERE username = #{userName}) Limit #{offset},#{limit}"
           )
    List<Article> getFavoriteArticleByUserName(String userName, int limit, int offset );


}

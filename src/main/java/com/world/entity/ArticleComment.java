package com.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ergou
 * @since 2024-12-19
 */
@Getter
@Setter
  @TableName("article_comment")
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("article_id")
    private Long articleId;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    @TableField("created_at")
    private Date createdAt;

    @TableField("updated_at")
    private Date updatedAt;

    @TableField("author_id")
    private Long authorId;

    @TableField("content")
    private String content;
}

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
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("created_at")
    private Date createdAt;

    @TableField("updated_at")
    private Date updatedAt;

    @TableField("author_id")
    private Long authorId;

    @TableField("description")
    private String description;

    @TableField("slug")
    private String slug;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("favorites_count")
    private int favoritesCount;
}

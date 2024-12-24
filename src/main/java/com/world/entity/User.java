package com.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
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
@Data
@Getter
@Setter
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("created_at")
    private Date createdAt;

    @TableField("updated_at")
    private Date updatedAt;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    @TableField("email")
    private String email;

    @TableField("username")
    private String username;

    @TableField("image_url")
    private String imageUrl;

    @TableField("password")
    private String password;

    @TableField("bio")
    private String bio;

}

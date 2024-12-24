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
  @TableName("user_follow")
public class UserFollow implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    @TableField("created_at")
    private Date createdAt;

    @TableField("follower_id")
    private Long followerId;

    @TableField("following_id")
    private Long followingId;
}

package com.world.mapper;

import com.world.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.Setter;
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
public interface UserMapper extends BaseMapper<User> {

}

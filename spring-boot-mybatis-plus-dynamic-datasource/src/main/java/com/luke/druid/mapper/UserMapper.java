package com.luke.druid.mapper;

import com.luke.druid.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/10/13 11:22
 */
public interface UserMapper {

    @Select("SELECT * FROM users limit 1")
    List<User> selectUsers();
}

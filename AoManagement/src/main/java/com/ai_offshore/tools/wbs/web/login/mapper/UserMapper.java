package com.ai_offshore.tools.wbs.web.login.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

import com.ai_offshore.tools.wbs.web.login.entity.User;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("""
                SELECT u.user_id, u.username, u.password, u.is_active
                FROM users u
                WHERE u.username = #{username}
                AND u.is_active = true
            """)
    @Results({
        @Result(property = "roles", column = "user_id", 
                many = @Many(select = "findRolesByUserId"))
    })
    User findByUsername(String username);

    @Select("""
                SELECT r.role_name
                FROM roles r
                JOIN user_roles ur ON r.role_id = ur.role_id
                WHERE ur.user_id = #{userId}
            """)
    List<String> findRolesByUserId(Long userId);
} 
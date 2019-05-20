package com.lee.jblog.mapper;

import com.lee.jblog.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User getUserByPhone(String phone);

    @Select("SELECT username FROM user WHERE id = #{ID}")
    String getUsernameByID(int ID);

    @Select("SELECT role FROM user WHERE id = #{ID}")
    int getRoleByID(int ID);

    @Select("SELECT id FROM user WHERE username = #{username}")
    int getIDByUsername(String username);

    @Select("SELECT COUNT(*) FROM user")
    int getUserCount();

    @Insert("INSERT INTO user (username, password, phone, gender)" +
            "VALUES(#{username}, #{password}, #{phone}, #{gender}")
    void addUser(User user);

    @Insert("INSERT INTO user_role (user_id, role_id)" +
            "VALUES(#{userID}, #{roleID)")
    void addRole(@Param("userID") int userID, @Param("roleID") int roleID);

    @Update("UPDATE user SET password = #{password} WHERE username = #{username}")
    void updatePassword(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE user SET real_name = #{realName}, birthday = #{birthday}" +
            "email = #{email}, personal_profile = #{personal_profile} WHERE username = #{username}")
    User updateUser(User user, String username);

    @Update("UPDATE user SET recently_login = #{recentlyLogin} WHERE username = #{username}")
    void updateRecentlyLogin(@Param("username") String username, @Param("recentlyLogin") String recentlyLogin);
}

package com.xkk.dao;

import com.xkk.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM tbl_user WHERE userid=#{uid}")
    List<User> getUserByUserid(int uid);

    @Select("SELECT * FROM tbl_user WHERE username=#{user.username} and password=#{user.password}")
    List<User> getUserByUsername_Password(@Param("user") User user);

    @Insert("INSERT INTO tbl_user(username,password,nickname,university,major,age,email) VALUES(#{user.username},#{user.password},#{user.nickname},#{user.university},#{user.major},#{user.age},#{user.email})")
    int addUser(@Param("user") User user);

    @Insert("INSERT INTO tbl_user(nickname,qqopenid,touxiang,sex) VALUES(#{user.nickname},#{user.qqopenid},#{user.touxiang},#{user.sex})")
    int addQQUser(@Param("user") User user);

    @Select("SELECT * FROM tbl_user WHERE qqopenid=#{qqopenid}")
    User getUserByQQOpenid(String qqopenid);

    @Update("UPDATE tbl_user SET username=#{user.username},password=#{user.password},nickname=#{user.nickname}," +
            "university=#{user.university},major=#{user.major},age=#{user.age},email=#{user.email} " +
            "WHERE qqopenid=#{user.qqopenid}")
    int updateQQOpenidByLogin(@Param("user") User user);

    @Update("UPDATE tbl_user SET touxiang=#{user.touxiang} WHERE userid=#{user.userid}")
    int updateTouxiang(@Param("user") User user);

    @Select("SELECT * FROM tbl_user WHERE username=#{username}")
    User getUserByUsername(String username);
}

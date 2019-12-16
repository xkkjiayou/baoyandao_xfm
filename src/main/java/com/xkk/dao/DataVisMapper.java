package com.xkk.dao;

import com.xkk.pojo.DataVis;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataVisMapper {
    @Select("SELECT COUNT(*) from tbl_user")
    public int getUserCount();
    @Select("SELECT sex,COUNT(*) AS count from tbl_user GROUP BY sex")
    @Results(
            value = {
                    @Result(property="key",column="sex"),
                    @Result(property="value",column="count")
            }
    )
    public List<DataVis> getUserSexCountByGroup();


    @Select("SELECT age,COUNT(*) AS count from tbl_user GROUP BY age")
    @Results(
            value = {
                    @Result(property="key",column="age"),
                    @Result(property="value",column="count")
            }
    )
    public List<DataVis> getUserAgeCountByGroup();

    @Select("SELECT COUNT(*) from tbl_post")
    public int getPostCount();
    @Select("SELECT COUNT(*) from tbl_reply")
    public int getReplyCount();
    @Select("SELECT COUNT(*) from tbl_comment")
    public int getCommentCount();
    @Select("SELECT COUNT(*) from tbl_university")
    public int getUniversityCount();
    @Select("SELECT COUNT(*) from tbl_summer")
    public int getSummerCount();



    //记录IP
    @Insert("INSERT INTO tbl_ip(ip,city) VALUES(#{ip},#{city})")
    public int addIp(@Param("ip") String ip ,@Param("city") String city);

    @Select("SELECT city FROM tbl_ip GROUP BY city")
    public List<String> getCity();

}

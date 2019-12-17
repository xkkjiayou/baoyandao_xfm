package com.xkk.dao;

import com.xkk.pojo.Summer;
import com.xkk.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SummerMapper {

    @Select("SELECT summername,summerid,starttime,endtime,universityid,universityname FROM tbl_summer")
    public List<Summer> getALLSummer();
    @Select("SELECT summername,summerid,starttime,endtime,universityid,universityname FROM tbl_summer WHERE universityname=#{universityname}")
    public List<Summer> getALLSummerByUniversityname(String universityname);

    @Select("SELECT COUNT(*) FROM tbl_summer")
    public int getSummerCount();
    @Select("SELECT COUNT(*) FROM tbl_summer WHERE universityname=#{universityname}")
    public int getSummerCountByUniversityname(String universityname);
    @Select("SELECT tbl_summer.universityname AS typename,COUNT(*) AS nums ,tbl_university.universitylevel AS universitylevel " +
            "FROM tbl_summer " +
            "LEFT JOIN tbl_university ON tbl_university.universityid = tbl_summer.universityid " +
            "GROUP BY tbl_summer.universityname,tbl_university.universitylevel ")
    List<Type> getALLSummerUniversityNames();

    @Select("SELECT * FROM tbl_summer WHERE summerid=#{summerid}")
    public Summer getSummerByid(int summerid);

    @Select("SELECT summername,summerid,starttime,endtime,universityid FROM tbl_summer WHERE universityid=#{universityid}")
    public List<Summer> getSummerByUniversityid(int universityid);


}

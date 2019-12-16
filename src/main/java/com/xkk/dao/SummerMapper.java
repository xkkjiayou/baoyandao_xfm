package com.xkk.dao;

import com.xkk.pojo.Summer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SummerMapper {

    @Select("SELECT summername,summerid,starttime,endtime,universityid,universityname FROM tbl_summer")
    public List<Summer> getALLSummer();

    @Select("SELECT COUNT(*) FROM tbl_summer")
    public int getSummerCount();

    @Select("SELECT * FROM tbl_summer WHERE summerid=#{summerid}")
    public Summer getSummerByid(int summerid);

    @Select("SELECT summername,summerid,starttime,endtime,universityid FROM tbl_summer WHERE universityid=#{universityid}")
    public List<Summer> getSummerByUniversityid(int universityid);


}

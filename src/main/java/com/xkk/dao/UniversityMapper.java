package com.xkk.dao;

import com.xkk.pojo.Guide;
import com.xkk.pojo.Post;
import com.xkk.pojo.Type;
import com.xkk.pojo.University;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UniversityMapper {

    //模糊查询
    @Select("SELECT universityname,universityid FROM tbl_university WHERE universityname like CONCAT(#{name},'%') LIMIT 10 ")
    public List<University> getUniversityByLikelyUniveristyName(String universityname);

    //获得所有学校
    @Select("SELECT universityname,universityid,logo,universityrank,tags,viewnums FROM tbl_university " +
            "WHERE universityrank>0 " +
            "ORDER BY viewnums desc,universityrank asc")
    public List<University> getALLUniversity(String location);
    //获得所有学校
    @Select("SELECT universityname,universityid,logo,universityrank,tags,viewnums FROM tbl_university " +
            "WHERE universityrank>0 AND university_loc = #{location}" +
            "ORDER BY viewnums desc,universityrank asc")
    public List<University> getALLUniversityByLocation(String location);

    //获得学校总数
    @Select("SELECT COUNT(*) FROM tbl_university WHERE universityrank>0")
    public int getUniversityCount();

    @Select("SELECT COUNT(*) FROM tbl_university WHERE university_loc=#{location} AND universityrank>0")
    public int getUniversityCountByLocation(String location);


    @Select("SELECT * FROM tbl_university WHERE universityid=#{universityid}")
    public University getUniversityByid(int universityid);
    @Select("SELECT universityid,logo,university_loc FROM tbl_university WHERE universityid=#{universityid}")
    public University getUniversityByidForSummer(int universityid);

    //减少select的列，有助于加速
    @Select("SELECT universityname,universityid,logo,viewnums FROM tbl_university WHERE universityid=#{universityid}")
    public University getUniversityByidForRecommend(int universityid);

    @Select("SELECT universityname,universityid,logo,universityrank,universitylevel FROM tbl_university WHERE universityname=#{universityname}")
    public University getUniversityByName(String universityname);

    @Select("SELECT universityid,universityname,logo FROM tbl_university WHERE universityrank<#{universityrank} and universityrank>0")
    public List<University> getUniversityByBeforeRank(int universityrank);

    @Select("SELECT university_loc AS typename,COUNT(*) AS nums FROM tbl_university WHERE universityrank>0 GROUP BY university_loc")
    List<Type> getALLUniversityLocations();

    //简章
    @Select("SELECT guideid,guidename,datetime FROM tbl_guide WHERE universityid=#{universityid}")
    public List<Guide> getGuidesByUniversityid(@Param("universityid") int universityid);

    @Select("SELECT * FROM tbl_guide WHERE guideid=#{guideid}")
    public Guide getGuideByGuideid(int guideid);

    @Select("SELECT postid,title,datetime,keyword_seg FROM tbl_post WHERE keyword_seg like  CONCAT('%',#{universityname},'%') LIMIT 50 ")
    public List<Post> getPostByKeywordSegAndUniversityname(@Param("universityname") String universityname);

}

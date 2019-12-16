package com.xkk.dao;

import com.xkk.pojo.Post;
import com.xkk.pojo.Recommend;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecommendMapper {

    //论坛推荐
    @Insert("INSERT INTO tbl_recommend(userid,postid,score) VALUES(#{recommend.userid},#{recommend.postid},#{recommend.score})")
    public int addRecommend(@Param("recommend") Recommend recommend);

    @Select("SELECT * from tbl_recommend WHERE userid=#{userid} ORDER BY score desc")
    public List<Recommend> getRecommendsByUserid(int userid);

    @Select("SELECT * from tbl_recommend WHERE userid=#{recommend.userid} AND postid=#{recommend.postid}")
    public Recommend getRecommendByUseridAndPostid(@Param("recommend") Recommend recommend);

    @Delete("TRUNCATE tbl_recommend")
    public int truncateRecommend();

    @Update("UPDATE tbl_recommend SET score=#{recommend.score} WHERE userid=#{recommend.userid} AND postid=#{recommend.postid}")
    public int updateRecommendScoreByUseridAndPostid(@Param("recommend") Recommend recommend);



    //高校推荐
    @Insert("INSERT INTO tbl_recommend_university(userid,universityid,score) VALUES(#{recommend.userid},#{recommend.universityid},#{recommend.score})")
    public int addRecommendForUniversity(@Param("recommend") Recommend recommend);

    @Select("SELECT * from tbl_recommend_university WHERE userid=#{userid} ORDER BY score desc")
    public List<Recommend> getRecommendsByUseridForUniversity(int userid);

    @Select("SELECT * from tbl_recommend_university WHERE userid=#{recommend.userid} AND universityid=#{recommend.universityid} LIMIT 1")
    public Recommend getRecommendByUseridAndUniversityidForUniversity(@Param("recommend") Recommend recommend);

    @Delete("TRUNCATE tbl_recommend_university")
    public int truncateRecommendForUniversity();

    @Update("UPDATE tbl_recommend_university SET score=#{recommend.score} WHERE userid=#{recommend.userid} AND universityid=#{recommend.universityid}")
    public int updateRecommendScoreByUseridAndUniversityidForUniversity(@Param("recommend") Recommend recommend);


    //热词记录，汇总全站的post comment reply content 给segmentation分析
    @Select("SELECT postid,content FROM tbl_reply WHERE datetime >= CURDATE() AND postid=#{postid}" +
            " UNION ALL SELECT postid,content FROM tbl_comment  WHERE datetime >= CURDATE()  AND postid=#{postid}" +
            " UNION ALL SELECT postid,content FROM tbl_post  WHERE datetime >= CURDATE()  AND typeid<=10  AND postid=#{postid}")
    public List<Post> getAllContent(int postid);

    @Insert("INSERT INTO tbl_hotword(hotword) VALUES(#{hotword})")
    public int addHotword(String hotword);

    @Select("SELECT * FROM tbl_hotword WHERE hotword=#{hotword}")
    public String getHotwordByHotword(String hotword);

//    获取最近5天里的热词
    @Select("SELECT hotword FROM tbl_hotword WHERE datetime >= DATE_SUB(CURDATE(),INTERVAL 30 DAY) ORDER BY rand() limit 20")
    public List<String> getAllHotword();

    @Select("SELECT postid FROM tbl_post")
    List<Integer> getALLPostidForWordSeg();

    @Update("UPDATE tbl_post SET keyword_seg=#{keyword_seg} WHERE postid=#{postid}")
    public int updatePostKeywordSegByPostid(@Param("keyword_seg") String keyword_seg,@Param("postid") int postid);



}

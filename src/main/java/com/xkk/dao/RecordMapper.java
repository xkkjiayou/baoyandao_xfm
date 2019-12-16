package com.xkk.dao;

import com.xkk.pojo.Record;
import com.xkk.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecordMapper {

    //--------------访问论坛------------
    //记录知识点，单个参数可以不加Param这个注释，多个的时候必须要加，不然spring无法识别
    @Insert("INSERT INTO tbl_post_view_record(postid,userid) VALUES(#{postid},#{userid})")
    public int addPostRecord(@Param("postid") int postid,@Param("userid") int userid);

    @Update("UPDATE tbl_post SET viewnums = viewnums + 1 WHERE postid=#{postid}")
    public int updatePostRecordNums(@Param("postid") int postid);

    @Select("SELECT postid,userid,max(datetime) as datetime,COUNT(*) AS nums FROM tbl_post_view_record WHERE userid=#{userid} GROUP BY postid LIMIT 500")
    public List<Record> getRecordPostByUserid(int userid);

    @Select("SELECT * FROM tbl_post_view_record WHERE userid=#{userid} AND postid=#{postid}")
    public Record getRecordPostByPostidAndUserid(@Param("postid") int postid, @Param("userid") int userid);

    @Update("UPDATE tbl_post_view_record SET score = score + #{score} WHERE postid=#{postid} AND userid=#{userid} ")
    public int updateScoreNums(@Param("postid") int postid,@Param("userid") int userid,@Param("score") int score);

    //----------------访问高校---------------

    //记录知识点，单个参数可以不加Param这个注释，多个的时候必须要加，不然spring无法识别
    @Insert("INSERT INTO tbl_university_view_record(universityid,userid,user_universityname,score,user_universitylevel) VALUES(#{universityid},#{userid},#{universityname},1,#{user_universitylevel})")
    public int addUniversityRecord(@Param("universityid") int universityid,@Param("userid") int userid,@Param("universityname") String universityname,@Param("user_universitylevel") String user_universitylevel);

    @Update("UPDATE tbl_university SET viewnums = viewnums + 1 WHERE universityid=#{universityid}")
    public int updateUniversityRecordNums(@Param("universityid") int universityid);

    @Select("SELECT universityid,userid,max(datetime) as datetime,COUNT(*) AS nums FROM tbl_university_view_record WHERE userid=#{userid} GROUP BY postid LIMIT 500")
    public List<Record> getRecordUniversityByUserid(int userid);

    @Select("SELECT * FROM tbl_university_view_record WHERE userid=#{userid} AND universityid=#{universityid}")
    public Record getRecordUniversityByUniversityidAndUserid(@Param("universityid") int universityid, @Param("userid") int userid);

    @Update("UPDATE tbl_university_view_record SET score = score + #{score} WHERE universityid=#{universityid} AND userid=#{userid} ")
    public int updateUniversityScoreNums(@Param("universityid") int universityid,@Param("userid") int userid,@Param("score") int score);

    @Select("SELECT universityid  FROM tbl_university_view_record WHERE user_universityname=#{universityname}")
    public List<Record> getRecordUniversityByUniversityname(String universityname);

    //获得关注这个学校的 同学的学校等级，比如关注清华大学的有20%是985 50%是211 30%是双非
    @Select("SELECT user_universitylevel AS typename,COUNT(*) AS nums FROM tbl_university_view_record WHERE universityid=#{universityid} AND user_universitylevel is not null GROUP BY user_universitylevel")
    @Results({
            @Result(property = "typename",column = "user_universitylevel"),
            @Result(property = "nums",column = "nums")
    })
    public List<Type> getUniversityViewerLevelByName(int universityid);

    //点赞 收藏 都属于record

    //点赞 参数用#{} 表名等非阐述用$
    @Update("UPDATE ${tbl_name} SET like_nums = like_nums + 1 WHERE ${tbl_id_name}=#{tbl_id}")
    int updateLike(@Param("tbl_name") String tbl_name,@Param("tbl_id_name") String tbl_id_name,@Param("tbl_id") int tbl_id);

    @Insert("INSERT INTO tbl_like_record(belikedid,userid,source) VALUES(#{belikedid},#{userid},#{source})")
    int addUserLikeRecord(@Param("belikedid") int belikedid,@Param("userid") int userid,@Param("source") String source);

    @Select("SELECT source FROM tbl_like_record WHERE belikedid=#{belikedid} AND userid=#{userid} AND source=#{source}")
    String getLikeByBelikedidAndUseridAndSource(@Param("belikedid") int belikedid,@Param("userid") int userid,@Param("source") String source);

}

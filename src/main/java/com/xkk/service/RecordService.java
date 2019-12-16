package com.xkk.service;

import com.xkk.pojo.Record;
import com.xkk.pojo.Type;

import java.util.List;

public interface RecordService {

    public int addPostRecord(int postid,int userid);

    public int updatePostRecordNums(int postid);

    public List<Record> getRecordPostByUserid(int userid);

    public Record getRecordPostByPostidAndUserid(int postid, int userid);

    public int updateScoreNums(int postid,int userid,int score);


    //-------------访问高校-------------------

    //记录知识点，单个参数可以不加Param这个注释，多个的时候必须要加，不然spring无法识别
    public int addUniversityRecord(int universityid, int userid,String universityname);

    public int updateUniversityRecordNums(int universityid);

    public List<Record> getRecordUniversityByUserid(int userid);

    public Record getRecordUniversityByUniversityidAndUserid(int universityid, int userid);

    public int updateUniversityScoreNums(int universityid,int userid, int score);

    public List<Record> getRecordUniversityByUniversityname(String universityname);
    public List<Type> getUniversityViewerLevelByName(int universityid);


    //点赞 收藏

    int updateLike(String tbl_name,String tbl_id_name,int tbl_id,int userid);
    int addUserLikeRecord(int belikedid, int userid, String source);
    String getLikeByBelikedidAndUseridAndSource(int belikedid,  int userid,  String source);

}



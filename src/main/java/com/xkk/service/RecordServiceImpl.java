package com.xkk.service;

import com.xkk.dao.BBSMapper;
import com.xkk.dao.RecordMapper;
import com.xkk.dao.UserMapper;
import com.xkk.pojo.Post;
import com.xkk.pojo.Record;
import com.xkk.pojo.Type;
import com.xkk.util.RecommendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    RecordMapper recordMapper;
    @Autowired
    BBSMapper bbsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UniversityService universityService;

    //可以多线程
    @Override
    public int addPostRecord(int postid, int userid) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("222222222222222222");
                System.out.println(postid+"--"+userid);
                Record recordPost = recordMapper.getRecordPostByPostidAndUserid(postid,userid);
                if (recordPost!=null){
//                    System.out.println(postid+"-1-"+userid);
                    //重复浏览+1
                    recordMapper.updateScoreNums(postid,userid, RecommendUtil.SCORE_VIEW);
                }else{
//                    System.out.println(postid+"-2-"+userid);
                    recordMapper.addPostRecord(postid,userid);
                }
                updatePostRecordNums(postid);
            }
        });
        thread.start();
        return 1;
    }

    @Override
    public int updatePostRecordNums(int postid) {
        return recordMapper.updatePostRecordNums(postid);
    }

    @Override
    public List<Record> getRecordPostByUserid(int userid) {
        List<Record> recordPosts = recordMapper.getRecordPostByUserid(userid);
        for (Record recordpost:recordPosts) {
            Post post = bbsMapper.getPostByPostid(recordpost.getPostid()).get(0);
            post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
            recordpost.setPost(post);

        }
        return recordPosts;
    }

    @Override
    public Record getRecordPostByPostidAndUserid(int postid, int userid) {
        return recordMapper.getRecordPostByPostidAndUserid(postid,userid);
    }

    @Override
    public int updateScoreNums(int postid, int userid, int score) {
        return recordMapper.updateScoreNums(postid,userid,score);
    }

    //-----------访问高校------------

    @Override
    public int addUniversityRecord(int universityid, int userid,String universityname) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Record recordUniversity = recordMapper.getRecordUniversityByUniversityidAndUserid(universityid,userid);
                if (recordUniversity!=null){
                    //重复浏览+1
                    recordMapper.updateUniversityScoreNums(universityid,userid, RecommendUtil.SCORE_VIEW);
                }else{
                    String user_universitylevel = universityService.getUniversityByName(universityname).getUniversitylevel();
                    recordMapper.addUniversityRecord(universityid,userid,universityname,user_universitylevel);
                }
                updateUniversityRecordNums(universityid);
            }
        });
        thread.start();
        return 1;
    }

    @Override
    public int updateUniversityRecordNums(int universityid) {
        return recordMapper.updateUniversityRecordNums(universityid);
    }

    @Override
    public List<Record> getRecordUniversityByUserid(int userid) {
        return recordMapper.getRecordUniversityByUserid(userid);
    }

    @Override
    public Record getRecordUniversityByUniversityidAndUserid(int universityid, int userid) {
        return recordMapper.getRecordUniversityByUniversityidAndUserid(universityid,userid);
    }

    @Override
    public int updateUniversityScoreNums(int universityid, int userid, int score) {
        return recordMapper.updateUniversityScoreNums(universityid,userid,score);
    }

    @Override
    public List<Record> getRecordUniversityByUniversityname(String universityname) {
        return recordMapper.getRecordUniversityByUniversityname(universityname);
    }

    @Override
    public List<Type> getUniversityViewerLevelByName(int universityid) {
        return recordMapper.getUniversityViewerLevelByName(universityid);
    }

    //在这里完成 点赞表的更新，对应被点赞对象的+1，post记录表的加分用于推荐
    @Override
    public int updateLike(String tbl_name, String tbl_id_name, int tbl_id,int userid) {
        if(tbl_id_name.equals("postid")){
            recordMapper.updateScoreNums(tbl_id,userid,RecommendUtil.SCORE_LIKE);
        }
        String source = tbl_name.replace("tbl_","");
        String temp = recordMapper.getLikeByBelikedidAndUseridAndSource(tbl_id,userid,source);
        if (temp ==null || temp.equals("") ){
            recordMapper.addUserLikeRecord(tbl_id,userid,source);
            recordMapper.updateLike(tbl_name,tbl_id_name,tbl_id);
            return 1;
        }
        return 0;
    }

    @Override
    public int addUserLikeRecord(int belikedid, int userid, String source) {
        return recordMapper.addUserLikeRecord(belikedid,userid,source);
    }

    @Override
    public String getLikeByBelikedidAndUseridAndSource(int belikedid, int userid, String source) {
        return recordMapper.getLikeByBelikedidAndUseridAndSource(belikedid,userid,source);
    }
}

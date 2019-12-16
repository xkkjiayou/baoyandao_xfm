package com.xkk.service;

import com.xkk.pojo.Post;
import com.xkk.pojo.Recommend;
import com.xkk.pojo.University;

import java.util.List;

public interface RecommendService {

    public int addRecommend(Recommend recommend);
    public List<Post> getRecommendsByUserid(int userid);
    public int truncateRecommend();
    public Recommend getRecommendByUseridAndPostid(Recommend recommend);
    public int updateRecommendScoreByUseridAndPostid(Recommend recommend);


    //高校推荐

    public int addRecommendForUniversity(Recommend recommend);
    public List<University> getRecommendsByUseridForUniversity(int userid);

    public Recommend getRecommendByUseridAndUniversityidForUniversity( Recommend recommend);
    public int truncateRecommendForUniversity();

    public int updateRecommendScoreByUseridAndUniversityidForUniversity( Recommend recommend);
    public List<Post> getAllContent(int postid);
    public int addHotword(String hotword);
    public List<String> getAllHotword();
    public String getHotwordByHotword(String hotword);
    List<Integer> getALLPostidForWordSeg();
    public int updatePostKeywordSegByPostid( String keyword_seg,int postid);


}

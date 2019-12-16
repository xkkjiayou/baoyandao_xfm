package com.xkk.service;

import com.xkk.dao.RecommendMapper;
import com.xkk.pojo.Post;
import com.xkk.pojo.Recommend;
import com.xkk.pojo.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@CacheConfig(cacheNames = "recommend")
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    RecommendMapper recommendMapper;
    @Autowired
    BBSService bbsService;
    @Autowired
    UniversityService universityService;

    @CacheEvict(value = "getRecommendsByUserid",allEntries = true)
    @Override
    public int addRecommend(Recommend recommend) {
        Recommend recommend1 = getRecommendByUseridAndPostid(recommend);
        if(recommend1!=null){
            return updateRecommendScoreByUseridAndPostid(recommend);
        }

        return recommendMapper.addRecommend(recommend);
    }

    @Cacheable(value = "getRecommendsByUserid")
    @Override
    public List<Post> getRecommendsByUserid(int userid) {
        List<Recommend> recommends = recommendMapper.getRecommendsByUserid(userid);
        List<Post> posts = new ArrayList<>();
        for(Recommend recommend:recommends){
//            System.out.println("_-----------"+recommend.getPostid()+"-----====");
            posts.add(bbsService.getPostByPostid(recommend.getPostid(),null).get(0));
        }
        return posts;
    }
    @CacheEvict(value = "getRecommendsByUserid")
    @Override
    public int truncateRecommend() {
        return recommendMapper.truncateRecommend();
    }

    @Cacheable(value = "getRecommendByUseridAndPostid")
    @Override
    public Recommend getRecommendByUseridAndPostid(Recommend recommend) {
        return recommendMapper.getRecommendByUseridAndPostid(recommend);
    }

    @CacheEvict(value = {"getRecommendsByUserid","getRecommendByUseridAndPostid"},allEntries = true)
    @Override
    public int updateRecommendScoreByUseridAndPostid(Recommend recommend) {
        return recommendMapper.updateRecommendScoreByUseridAndPostid(recommend);
    }

    //------------------高校推荐-----------------------
    @CacheEvict(value = "getRecommendsByUseridForUniversity",allEntries = true)
    @Override
    public int addRecommendForUniversity(Recommend recommend) {
        Recommend recommend1 = getRecommendByUseridAndUniversityidForUniversity(recommend);
        if(recommend1!=null){
            return recommendMapper.updateRecommendScoreByUseridAndUniversityidForUniversity(recommend);
        }

        return recommendMapper.addRecommendForUniversity(recommend);
    }

    @Cacheable(value = "getRecommendsByUseridForUniversity")
    @Override
    public List<University> getRecommendsByUseridForUniversity(int userid) {
        List<Recommend> recommends = recommendMapper.getRecommendsByUseridForUniversity(userid);
        List<University> universities = new ArrayList<>();
        for(Recommend recommend:recommends){
            universities.add(universityService.getUniversityByidForRecommend(recommend.getUniversityid()));
        }
        return universities;
    }

    @Cacheable(value = "getRecommendByUseridAndUniversityidForUniversity")
    @Override
    public Recommend getRecommendByUseridAndUniversityidForUniversity(Recommend recommend) {
        return recommendMapper.getRecommendByUseridAndUniversityidForUniversity(recommend);
    }

    @Override
    public int truncateRecommendForUniversity() {
        return recommendMapper.truncateRecommendForUniversity();
    }

    @Override
    public int updateRecommendScoreByUseridAndUniversityidForUniversity(Recommend recommend) {
        return recommendMapper.updateRecommendScoreByUseridAndUniversityidForUniversity(recommend);
    }

    @Cacheable(value = "getAllContent")
    @Override
    public List<Post> getAllContent(int postid) {
        return recommendMapper.getAllContent(postid);
    }


    @Override
    public int addHotword(String hotword) {
        String s = recommendMapper.getHotwordByHotword(hotword);
//        System.out.println(s);
        if(s==null || s.equals("")){
//            System.out.println("更新热词"+hotword);
            return recommendMapper.addHotword(hotword);
        }
        return 0;
    }

    @Cacheable(value = "hotwords")
    @Override
    public List<String> getAllHotword() {
        return recommendMapper.getAllHotword();
    }

    @Cacheable(value = "getHotwordByHotword")
    @Override
    public String getHotwordByHotword(String hotword) {
        return recommendMapper.getHotwordByHotword(hotword);
    }

    @Cacheable(value = "getALLPostidForWordSeg")
    @Override
    public List<Integer> getALLPostidForWordSeg() {
        return recommendMapper.getALLPostidForWordSeg();
    }

    @Override
    public int updatePostKeywordSegByPostid(String keyword_seg, int postid) {
        return recommendMapper.updatePostKeywordSegByPostid(keyword_seg,postid);
    }


}

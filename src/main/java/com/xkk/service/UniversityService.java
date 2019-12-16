package com.xkk.service;

import com.xkk.pojo.Guide;
import com.xkk.pojo.Post;
import com.xkk.pojo.Type;
import com.xkk.pojo.University;

import java.util.List;

public interface UniversityService {

    public List<University> getUniversityByLikelyUniveristyName(String universityname);
    public List<University> getALLUniversity(int page,int nums,String location);
    public int getUniversityCount(String location);
    public University getUniversityByName(String universityname);
    public University getUniversityByid(int universityid);
    public List<University> getUniversityByBeforeRank(int universityrank);
    public List<University> getUniversityByClassmateView(String universityname);
    public University getUniversityByidForRecommend(int universityid);
    List<Type> getALLUniversityLocations();
    public List<Guide> getGuidesByUniversityid(int universityid);
    public Guide getGuideByGuideid(int guideid);
    public List<Post> getPostByKeywordSegAndUniversityname(String universityname);


}

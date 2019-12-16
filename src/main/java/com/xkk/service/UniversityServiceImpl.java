package com.xkk.service;

import com.github.pagehelper.PageHelper;
import com.xkk.dao.SummerMapper;
import com.xkk.dao.UniversityMapper;
import com.xkk.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityMapper universityMapper;
    @Autowired
    SummerMapper summerMapper;
    @Autowired
    RecordService recordService;

    @Cacheable(value = "getUniversityByLikelyUniveristyName")
    @Override
    public List<University> getUniversityByLikelyUniveristyName(String universityname) {
        return universityMapper.getUniversityByLikelyUniveristyName(universityname);
    }

    @Cacheable(value = "getALLUniversity")
    @Override
    public List<University> getALLUniversity(int page,int nums,String location) {
        PageHelper.startPage(page, nums);
        if(location ==null || location.equals("全部")){
            List<University> universities = universityMapper.getALLUniversity("");
            for(University university:universities){
                Map<String,Double> map = new HashMap<>();
                List<Type> types = recordService.getUniversityViewerLevelByName(university.getUniversityid());
                if(types==null||types.size()==0){
                    continue;
                }
                double sum = 0;
                for(Type t:types){
                    sum += t.getNums();
                }
                for(Type t:types){
                    t.setNums((int) ((t.getNums()/sum*100))+1);
                    switch (t.getTypename()){
                        case "985":t.setColor("#6699cc");break;
                        case "211":t.setColor("#9966cc");break;
                        case "双非":t.setColor("#cc6699");break;
                    }
                }
                university.setView_level_types(types);
            }
            return universities;
        }else{
            List<University> universities = universityMapper.getALLUniversityByLocation(location);
            for(University university:universities){
                Map<String,Double> map = new HashMap<>();
                List<Type> types = recordService.getUniversityViewerLevelByName(university.getUniversityid());
                if(types==null||types.size()==0){
                    continue;
                }
                double sum = 0;
                for(Type t:types){
                    sum += t.getNums();
                }
                for(Type t:types){
                    t.setNums((int) ((t.getNums()/sum*100))+1);
                    switch (t.getTypename()){
                        case "985":t.setColor("#6699cc");break;
                        case "211":t.setColor("#9966cc");break;
                        case "双非":t.setColor("#cc6699");break;
                    }
                }
                university.setView_level_types(types);
            }
            return universities;
        }
    }

    @Cacheable(value = "getUniversityCount")
    @Override
    public int getUniversityCount(String location) {
        if(location!=null && !location.equals("全部")){
            return universityMapper.getUniversityCountByLocation(location);
        }
        if(location!=null && location.equals("全部")){
            return universityMapper.getUniversityCount();
        }

        return universityMapper.getUniversityCount();
    }

    @Cacheable(value = "getUniversityByName")
    @Override
    public University getUniversityByName(String universityname) {
        return universityMapper.getUniversityByName(universityname);
    }

    @Cacheable(value = "getUniversityByid")
    @Override
    public University getUniversityByid(int universityid) {
        University university = universityMapper.getUniversityByid(universityid);
        List<Summer> summers = summerMapper.getSummerByUniversityid(universityid);
        university.setSummers(summers.size()>0?summers:null);
        List<Guide> guides = universityMapper.getGuidesByUniversityid(universityid);
        university.setGuides(guides.size()>0?guides:null);
        recordService.updateUniversityRecordNums(universityid);
        return university;
    }

    @Cacheable(value = "getUniversityByBeforeRank")
    //用于推荐，比我的学校排名更靠前的学校
    @Override
    public List<University> getUniversityByBeforeRank(int universityrank) {
        return universityMapper.getUniversityByBeforeRank(universityrank);
    }

    @Cacheable(value = "getUniversityByClassmateView")
    @Override
    public List<University> getUniversityByClassmateView(String universityname) {
        List<Record> records = recordService.getRecordUniversityByUniversityname(universityname);
        List<University> universities = new ArrayList<>();
        for(Record record:records)
        {
            universities.add(universityMapper.getUniversityByidForRecommend(record.getUniversityid()));
        }
        return universities;
    }

    @Cacheable(value = "getUniversityByidForRecommend")
    @Override
    public University getUniversityByidForRecommend(int universityid) {
        return universityMapper.getUniversityByidForRecommend(universityid);
    }

    @Cacheable(value = "getALLUniversityLocations")
    @Override
    public List<Type> getALLUniversityLocations() {
        return universityMapper.getALLUniversityLocations();
    }

    @Cacheable(value = "getGuidesByUniversityid")
    @Override
    public List<Guide> getGuidesByUniversityid(int universityid) {
        return universityMapper.getGuidesByUniversityid(universityid);
    }

    @Cacheable(value = "getGuideByGuideid")
    @Override
    public Guide getGuideByGuideid(int guideid) {
        return universityMapper.getGuideByGuideid(guideid);
    }

    @Cacheable(value = "getPostByKeywordSegAndUniversityname")
    @Override
    public List<Post> getPostByKeywordSegAndUniversityname(String universityname) {
        return universityMapper.getPostByKeywordSegAndUniversityname(universityname);
    }

}

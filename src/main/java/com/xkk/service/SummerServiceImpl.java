package com.xkk.service;

import com.github.pagehelper.PageHelper;
import com.xkk.dao.SummerMapper;
import com.xkk.dao.UniversityMapper;
import com.xkk.pojo.Summer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "SummerServiceImpl")
public class SummerServiceImpl implements SummerService{

    @Autowired
    SummerMapper summerMapper;
    @Autowired
    UniversityMapper universityMapper;

    @Cacheable(value = "getALLSummer")
    @Override
    public List<Summer> getALLSummer(int page,int nums) {
        PageHelper.startPage(page,nums);
        List<Summer> summers = summerMapper.getALLSummer();
        for (Summer summer:summers) {
            summer.setUniversity(universityMapper.getUniversityByidForSummer(summer.getUniversityid()));
        }
        return summers;
    }

    @Cacheable(value = "getSummerCount")
    @Override
    public int getSummerCount() {
        return summerMapper.getSummerCount();
    }

    @Cacheable(value = "getSummerByid")
    @Override
    public Summer getSummerByid(int summerid) {
        Summer summer = summerMapper.getSummerByid(summerid);
//        System.out.println(summer.getUniversityid());
//        summer.setUniversity(universityMapper.getUniversityByid(summer.getUniversityid()));
        return summer;
    }

    @Cacheable(value = "getSummerByUniversityid")
    @Override
    public List<Summer> getSummerByUniversityid(int universityid) {
        return summerMapper.getSummerByUniversityid(universityid);
    }
}

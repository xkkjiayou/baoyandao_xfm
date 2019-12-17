package com.xkk.service;

import com.github.pagehelper.PageHelper;
import com.xkk.dao.SummerMapper;
import com.xkk.dao.UniversityMapper;
import com.xkk.pojo.Summer;
import com.xkk.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "SummerServiceImpl")
public class SummerServiceImpl implements SummerService{

    @Autowired
    SummerMapper summerMapper;
    @Autowired
    UniversityMapper universityMapper;

    @Cacheable(value = "getALLSummer")
    @Override
    public List<Summer> getALLSummer(int page,int nums,String universityname) {
        PageHelper.startPage(page,nums);
        if(universityname ==null || universityname.equals("全部")) {
            List<Summer> summers = summerMapper.getALLSummer();
            for (Summer summer : summers) {
                summer.setUniversity(universityMapper.getUniversityByidForSummer(summer.getUniversityid()));
            }
            return summers;
        }else{
            List<Summer> summers = summerMapper.getALLSummerByUniversityname(universityname);
            for (Summer summer : summers) {
                summer.setUniversity(universityMapper.getUniversityByidForSummer(summer.getUniversityid()));
            }
            return summers;
        }
    }

    @Cacheable(value = "getSummerCount")
    @Override
    public int getSummerCount(String universityname) {

        if(universityname ==null || universityname.equals("全部")) {
            return summerMapper.getSummerCount();
        }
        else{
            return summerMapper.getSummerCountByUniversityname(universityname);
        }
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

    @Override
    public Map<String,List<Type>> getALLSummerUniversityNames() {
        List<Type> universitynamesTypes = summerMapper.getALLSummerUniversityNames();
        Map<String,List<Type>> map = new HashMap<>();
        List<Type> Types211 = new ArrayList<>();
        List<Type> Types985 = new ArrayList<>();
        List<Type> Typesshuangfei = new ArrayList<>();
        List<Type> Typesshuangyiliu = new ArrayList<>();
        for(Type t:universitynamesTypes){
            switch (t.getUniversitylevel()){
                case "211":Types211.add(t);break;
                case "985":Types985.add(t);break;
                case "双一流":Typesshuangfei.add(t);break;
                case "双非":Typesshuangyiliu.add(t);break;
                default:Typesshuangyiliu.add(t);break;
            }
        }
        map.put("211",Types211);
        map.put("985",Types985);
        map.put("shuangfei",Typesshuangfei);
        map.put("shuangyiliu",Typesshuangyiliu);
        return map;
    }
}

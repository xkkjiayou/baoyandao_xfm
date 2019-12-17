package com.xkk.service;

import com.xkk.pojo.Summer;
import com.xkk.pojo.Type;

import java.util.List;
import java.util.Map;

public interface SummerService {
    public List<Summer> getALLSummer(int page,int nums,String universityname);
    public int getSummerCount(String universityname);
    public Summer getSummerByid(int summerid);
    public List<Summer> getSummerByUniversityid(int universityid);
    Map<String,List<Type>> getALLSummerUniversityNames();
}

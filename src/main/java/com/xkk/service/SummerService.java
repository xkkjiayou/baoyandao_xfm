package com.xkk.service;

import com.xkk.pojo.Summer;

import java.util.List;

public interface SummerService {
    public List<Summer> getALLSummer(int page,int nums);
    public int getSummerCount();
    public Summer getSummerByid(int summerid);
    public List<Summer> getSummerByUniversityid(int universityid);
}

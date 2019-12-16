package com.xkk.service;

import com.xkk.pojo.DataVis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DataVisService {

    public int getUserCount();
    public List<DataVis> getUserSexCountByGroup();
    public List<DataVis> getUserAgeCountByGroup();
    public int getPostCount();
    public int getReplyCount();
    public int getCommentCount();
    public int getUniversityCount();
    public List<String> getCity();
    public int getSummerCount();
    public int addIp(HttpServletRequest request);

}

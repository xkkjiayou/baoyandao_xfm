package com.xkk.service;

import com.xkk.dao.DataVisMapper;
import com.xkk.pojo.DataVis;
import com.xkk.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Service
public class DataVisServiceImpl implements DataVisService {

    @Autowired
    DataVisMapper dataVisMapper;

    @Override
    public int getUserCount() {
        return dataVisMapper.getUserCount();
    }

    @Override
    public List<DataVis> getUserSexCountByGroup() {
        return dataVisMapper.getUserSexCountByGroup();
    }

    @Override
    public List<DataVis> getUserAgeCountByGroup() {
        return dataVisMapper.getUserAgeCountByGroup();
    }

    @Override
    public int getPostCount() {
        return dataVisMapper.getPostCount();
    }

    @Override
    public int getReplyCount() {
        return dataVisMapper.getReplyCount();
    }

    @Override
    public int getCommentCount() {
        return dataVisMapper.getCommentCount();
    }

    @Override
    public int getUniversityCount() {
        return dataVisMapper.getUniversityCount();
    }

    @Override
    public List<String> getCity() {
        return dataVisMapper.getCity();
    }

    @Override
    public int getSummerCount() {
        return dataVisMapper.getSummerCount();
    }

    @Override
    public int addIp( HttpServletRequest request) {
        String ip = DataUtil.getIpAddr(request);
        String city = DataUtil.getJsonContent(ip);
        System.out.println(city);
        return dataVisMapper.addIp(ip,city);
    }
}

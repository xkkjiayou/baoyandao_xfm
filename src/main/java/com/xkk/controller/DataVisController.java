package com.xkk.controller;

import com.xkk.pojo.DataVis;
import com.xkk.service.DataVisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DataVisController {

    @Autowired
    DataVisService dataVisService;
    //暂时弃用，后期做数据分析再用
    @GetMapping("/country_data_vis")
    public String login(ModelMap modelMap){

        //活跃度
        int active = dataVisService.getUserCount()*dataVisService.getPostCount()*dataVisService.getCommentCount()*dataVisService.getReplyCount();
        modelMap.put("active",active);
        List<DataVis> dataViss = dataVisService.getUserSexCountByGroup();
        List<String> cities = dataVisService.getCity();
        return "";
    }
}

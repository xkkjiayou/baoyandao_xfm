package com.xkk.controller;

import com.xkk.pojo.Summer;
import com.xkk.service.SummerService;
import com.xkk.util.PageVariableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SummerController {

    @Autowired
    SummerService summerService;


    @GetMapping(value = {"/summer/{page}/{nums}","/summer"})
    public String newest_summer(@PathVariable(name="page", required = false) Integer page, @PathVariable(name="nums", required = false) Integer nums, ModelMap map, HttpSession session){
        page = page==null?1:page;
        nums = nums==null?1:nums;
        nums = PageVariableUtil.NUMS;

        List<Summer> summers = summerService.getALLSummer(page,nums);
        int count = summerService.getSummerCount();
        int pagetotal = count%nums==0?count/nums:count/nums+1;
//        System.out.println(count);
        List<Integer> page_indexes = PageVariableUtil.Get_Page_indexes(page,pagetotal);
        map.put("page_indexes", page_indexes);
        map.put("page_start", page);//当前页
        map.put("summers",summers);
//        map.put("summer_nums",summers.size());

        return "summer";
    }

    @GetMapping(value = "/summer/{summerid}")
    public String summer(@PathVariable("summerid") int summerid,ModelMap model){
        Summer summer =  summerService.getSummerByid(summerid);
//        System.out.println(summer.getContent());
        model.put("summer",summer);
        return "summer_info";
    }

}

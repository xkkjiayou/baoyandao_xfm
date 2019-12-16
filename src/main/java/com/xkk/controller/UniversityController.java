package com.xkk.controller;

import com.xkk.pojo.*;
import com.xkk.service.RecommendService;
import com.xkk.service.RecordService;
import com.xkk.service.SummerService;
import com.xkk.service.UniversityService;
import com.xkk.util.PageVariableUtil;
import com.xkk.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UniversityController {

    @Autowired
    UniversityService universityService;
    @Autowired
    SummerService summerService;
    @Autowired
    RecordService recordService;
    @Autowired
    RecommendService recommendService;

    @GetMapping(value = {"/university/{page}/{nums}","/university"})
    public String news(@PathVariable(name="page", required = false) Integer page,
                       @PathVariable(name="nums", required = false) Integer nums,
                       ModelMap map,
                       HttpSession session,
                       @RequestParam(value = "location",required = false) String location){
        page = page==null?1:page;
        nums = nums==null?1:nums;
        nums = PageVariableUtil.NUMS;
        List<University> universities = universityService.getALLUniversity(page,nums,location);
        int count = universityService.getUniversityCount(location);
        int pagetotal = count%nums==0?count/nums:count/nums+1;
//        System.out.println(count);
        List<Integer> page_indexes = PageVariableUtil.Get_Page_indexes(page,pagetotal);
        map.put("page_indexes", page_indexes);
        map.put("page_start", page);//当前页
        List<Type> locations = universityService.getALLUniversityLocations();
        map.put("universities",universities);
        map.put("locations",locations);
        if (location!=null && !location.equals("")) {
            map.put("cur_location", location);
        }
        else{
            map.put("cur_location", "全部");
        }
        return "university";
    }

    @GetMapping(value = {"/university_recommend"})
    public String university_recommend(ModelMap map, HttpSession session){

        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            return "login";
        }
        University myuniversity = universityService.getUniversityByName(user.getUniversity());
        if(myuniversity!=null) {
            List<University> universities_teacher = universityService.getUniversityByBeforeRank(myuniversity.getUniversityrank()+5);
            if (universities_teacher.size() > 0) {
                map.put("universities_teacher", universities_teacher);
            }
        }
        List<University> universities_classmate = universityService.getUniversityByClassmateView(user.getUniversity());
        System.out.println(universities_classmate.size());
        if(universities_classmate.size()>0) {
            map.put("universities_classmate", universities_classmate);
        }
        List<University> universities_recommend = recommendService.getRecommendsByUseridForUniversity(user.getUserid());
        System.out.println(universities_recommend.size());
        if(universities_recommend.size()>0) {
            map.put("universities_recommend", universities_recommend);
        }


        return "university_recommend";
    }


    @GetMapping(value = "/university/{universityid}")
    public String university(@PathVariable("universityid") int universityid,ModelMap model,HttpSession session){
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        University university =  universityService.getUniversityByid(universityid);
        List<Post> relative_posts = universityService.getPostByKeywordSegAndUniversityname(university.getUniversityname());
        model.put("university",university);
        model.put("relative_posts",relative_posts.size()>0?relative_posts:null);
//        model.put("summer_nums",university.getSummers().isEmpty());
        if(user!=null)
        {
            recordService.addUniversityRecord(universityid,user.getUserid(),user.getUniversity());
        }
        return "university_info";
    }

    //根据id拿到简章

    @GetMapping(value = "/guide/{guideid}")
    public String summer(@PathVariable("guideid") int guideid,ModelMap model){
        Guide guide =  universityService.getGuideByGuideid(guideid);
//        System.out.println(summer.getContent());
        model.put("guide",guide);
        return "guide_info";
    }


}

package com.xkk.controller;

import com.xkk.pojo.Record;
import com.xkk.pojo.University;
import com.xkk.pojo.User;
import com.xkk.service.*;
import com.xkk.util.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;
    @Autowired
    UniversityService universityService;
    @Autowired
    MessageService messageService;
    @Autowired
    DataVisService dataVisService;
    @RequestMapping("/getUserByUserid/{userid}")
    @ResponseBody
    public List<User> getUserByUserid(@PathVariable int userid){
        List<User> userList = userService.getUserByUserid(userid);
        return userList;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping(value = "/login")
    public String login_post(String username, String password, HttpSession session, ModelMap map,HttpServletRequest request){
        dataVisService.addIp(request);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<User> userList = userService.getUserByUsername_Password(user);
//        session.setAttribute("");
        if(userList.isEmpty()){
            map.put("error_message","小同学，你的账号或密码错误！");
            return "login";
        }else{
            user = userList.get(0);
            int count = messageService.getMessageCountByTouserid(user.getUserid());
            user.setMessage_nums(count);
            session.setAttribute(SessionUtil.USER_SESSION_KRY,user);
            return "index";
        }
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String register_post(String username,
                                String password,
                                String nickname,
                                String university,
                                String age,
                                String major,
                                String email,
                                HttpSession session,
                                ModelMap map){
        //检查用户名是否已存在
        if (userService.getUserByUsername(username)!=null){
            map.put("error_message","小同学，你的用户名已存在！");
            return "register";
        }
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setAge(age);
        user.setUniversity(university);
        user.setMajor(major);
        int rs = userService.addUser(user);
        if(rs>0){
            List<User> userList = userService.getUserByUsername_Password(user);
            session.setAttribute(SessionUtil.USER_SESSION_KRY,userList.get(0));
            return "index";
        }else{
            map.put("error_message","小同学，你的用户名已存在！");
            return "register";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(SessionUtil.USER_SESSION_KRY);
        return "index";
    }


    //其他查看用户信息
    @GetMapping("/view_user_info/{userid}")
    public String view_other_user_info(@PathVariable int userid, HttpSession session,ModelMap model){
        List<User> userList = userService.getUserByUserid(userid);
        if(userList.isEmpty()){
            return "用户不存在";
        }else{
            model.put("user",userList.get(0));
        }
        model.put("messages",userService.ToActiveMeMessage(userid));
        return "view_user_info";
    }

    //查看自己信息
    @GetMapping("/user_info")
    public String view_my_user_info( HttpSession session,ModelMap model){
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            return "login";
        }
        List<Record> recordPosts = recordService.getRecordPostByUserid(user.getUserid());
        model.put("recurrent_view_posts",recordPosts);
        model.put("messages",userService.ToMeMessage(user.getUserid()));
        model.put("message_nums",messageService.getMessageCountByTouserid(user.getUserid()));
        return "user_info";
    }


    //更新头像-返回suerinfo页面
    //必须有C盘，自动创建 byd_touxiang文件夹
    @PostMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, ModelMap model, HttpServletRequest request, HttpSession session) {
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            return "login";
        }
        if (file.isEmpty()) {
            System.out.println("文件为空空");
            return "404";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "C://byd_touxiang//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/byd_touxiang/" + fileName;
        user.setTouxiang(filename);
        session.setAttribute(SessionUtil.USER_SESSION_KRY,user);
        userService.updateTouxiang(user);
//        model.addAttribute("filename", filename);
        return "user_info";
    }

    //    发表评论
    @RequestMapping(value="/getlikelyuniversityname",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject getlikelyuniversityname(@RequestParam("universityname") String universityname, HttpSession session) {
        Map<String,List<University>> map=new HashMap<String, List<University>>();
        List<University> universities = universityService.getUniversityByLikelyUniveristyName(universityname);
//        map.put("status", "success");
        map.put("universities", universities);
        JSONObject json = JSONObject.fromObject(map);
//        System.out.println(json.toString());
        return json;
    }

}

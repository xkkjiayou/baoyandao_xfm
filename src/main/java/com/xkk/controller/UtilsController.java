package com.xkk.controller;

import com.xkk.pojo.User;
import com.xkk.util.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UtilsController {
    //更新头像-返回suerinfo页面
    //必须有C盘，自动创建 byd_touxiang文件夹
    @PostMapping(value = "/bbs_publish_imageupload")
    @ResponseBody
    public JSONObject bbs_publish_imageupload(@RequestParam(value = "file") MultipartFile files[], ModelMap model, HttpServletRequest request, HttpSession session) {
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            model.put("errno",1);//没有登录时 1
            model.put("data","没有登录");
            JSONObject json = JSONObject.fromObject(model);
            return json;
        }
        model.put("errno",0);
        List<String> images = new ArrayList<>();
        System.out.println(files.length);
        for (MultipartFile file:files) {
            if (file.isEmpty()) {
                model.put("errno", 2);//文件为空 2
                model.put("data", "文件为空");
                JSONObject json = JSONObject.fromObject(model);
                continue;
            }
            model.put("errno",0);
            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "C://byd_bbs//"; // 上传后的路径
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
            String filename = "/byd_bbs/" + fileName;
            images.add(filename);
        }
        model.put("data",images);
        JSONObject json = JSONObject.fromObject(model);
        System.out.println(json.toString());
        return json;
    }
}

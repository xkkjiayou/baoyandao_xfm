package com.xkk.controller;

import com.xkk.pojo.Comment;
import com.xkk.pojo.User;
import com.xkk.util.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(ModelMap map, HttpSession session){
        return "index";
    }


}

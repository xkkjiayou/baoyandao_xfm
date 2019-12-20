package com.xkk.util;

import com.google.gson.Gson;
import com.xkk.pojo.User;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QQLogin {
    static final  String AppId = "101838420";
    static final  String AppSecret = "d3816ae6d076c9bd615611269895abe5";
    static final  String RedirectUrl = "http://www.todaydream.cn/qqLogin";



    public static String getAccessToken(String code) {
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="
                + AppId
                + "&client_secret="
                + AppSecret
                + "&code="
                + code
                + "&redirect_uri=" + RedirectUrl;
        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.getForObject(url,
                String.class);
        String access_token = resp.substring(resp.indexOf("=")+1,resp.indexOf("&"));
        System.out.println(resp);
        System.out.println(access_token);
        return access_token;
    }
    public static String getOpenidByAccessToken(String accessToken) {
        String url =  "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.getForObject(url,
                String.class);


        Pattern p = Pattern.compile("client_id\":\"(\\w*)\",");
        Matcher m = p.matcher(resp);
        m.find();
        String appid = m.group(1);
        p = Pattern.compile("openid\":\"(\\w*)\"");
        m = p.matcher(resp);
        m.find();
        //得到openid
        String openid = m.group(1);

        System.out.println(resp);
        System.out.println(openid);
        System.out.println(openid);
        return openid;
    }
    public static User getUserByAccessTokenAndOpenid(String accessToken,String openid) {
        String url =  "https://graph.qq.com/user/get_user_info?access_token="+accessToken+"&oauth_consumer_key="+AppId+"&openid="+openid+"";
        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.getForObject(url,
                String.class);
        Gson gson = new Gson();
        User qquser = gson.fromJson(resp, User.class);
        qquser.setSex(qquser.getGender());
        qquser.setQqopenid(openid);
        qquser.setTouxiang(qquser.getFigureurl_qq_1());
        System.out.println(qquser.getNickname()+qquser.getGender());
        return qquser;
    }
}

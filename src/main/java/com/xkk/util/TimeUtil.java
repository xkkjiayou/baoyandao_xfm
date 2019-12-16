package com.xkk.util;

public class TimeUtil {
    public static String cal_howlong_last_reply(long now,long before){
        long elapsetime = (now-before)/1000;//转为秒
        String rs = "";
        if(elapsetime<=60){
            rs = "刚刚新鲜";
        }
        if(elapsetime>60 && elapsetime<3600){
            rs =  elapsetime/60+"分钟前新鲜";
        }
        if(elapsetime>=3600 && elapsetime<3600*24){
            rs =  elapsetime/3600+"小时前新鲜";
        }
        if(elapsetime>=3600*24 ){
            rs =  elapsetime/(3600*24)+"天前新鲜";
        }
//        System.out.println(rs);
        return rs;
    }
}

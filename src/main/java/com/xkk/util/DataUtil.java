package com.xkk.util;

import com.google.gson.Gson;
import com.xkk.pojo.IpVo;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class DataUtil {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
    public static String getJsonContent(String ip) {
        try{
            String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
            // 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Charset", "utf-8");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200){
                String s = ConvertStream2Json(httpConn.getInputStream());
                Gson gson = new Gson();
                IpVo ipvo = gson.fromJson(s, IpVo.class);
                System.out.println(s);
                String city = ipvo.getData().getCity();
                if(city.equals("内网IP"))
                {
                    city = "北京";
                }
                return city;
            }
        }
        catch (Exception e){
            return "保研岛";
        }
        return "保研岛";
    }

    private static String ConvertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try{
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1){
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray(),"utf-8");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return jsonStr;
    }
}



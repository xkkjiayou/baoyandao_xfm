package com.xkk.service;

import com.xkk.pojo.Post;
import com.xkk.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

//    @Value("${spring.mail.username}")
//    private String from;
    @Autowired
    private JavaMailSender sender;
    /*发送邮件的方法*/
    public void sendEmail(String to, String title, String content, User user, Post post) {
//        SimpleMailMessage message = new SimpleMailMessage();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage message = sender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        System.out.println(from);
                    helper.setFrom("xiaofeimaobaoyan@163.com"); //发送者
//        System.out.println(to);
                    helper.setTo(to); //接受者
                    helper.setSubject(title); //发送标题
                    helper.setText(user.getNickname()+"，在帖子<"+post.getTitle()+">下，给您回复了：<br/>"+content+"<br/><br/><br/>。<a href='http://todaydream.cn/post/"+post.getPostid()+"'>点击查看详情</a><br/><br/>我是你最爱的小飞猫", true); //发送内容
                    sender.send(message);
                    System.out.println("邮件发送成功");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return;


    }

}

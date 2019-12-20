package com.xkk.service;

import com.xkk.pojo.Message;
import com.xkk.pojo.User;

import java.util.List;

public interface UserService {
    List<User> getUserByUserid(int userid);
    List<User> getUserByUsername_Password(User user);
    int addUser(User user);
    int addQQUser(User user);
    int updateTouxiang(User user);
    int updateQQOpenidByLogin(User user);
    User getUserByQQOpenid(String qqopenid);

    List<Message> ToMeMessage(int userid);//这里是查看 userid被回复的消息
    List<Message> ToActiveMeMessage(int userid);//这里是查看 userid主动回复的消息
    User getUserByUsername(String username);
}

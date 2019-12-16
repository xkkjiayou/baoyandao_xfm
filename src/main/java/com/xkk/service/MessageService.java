package com.xkk.service;

import com.xkk.pojo.Message;

import java.util.List;

public interface MessageService {

    //找到该我接收的消息
    public List<Message> getMessagesByTouserid(int touserid);

    //找到该我接收的消息
    public int updateMessageStateByMessageid(int messageid);

    public int addMessageByMessage(Message message);
    public int getMessageCountByTouserid(int touserid);
    public List<Message> getMessagesByFromuserid(int fromuserid);
}

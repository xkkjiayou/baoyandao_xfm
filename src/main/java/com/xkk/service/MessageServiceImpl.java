package com.xkk.service;

import com.xkk.dao.MessageMapper;
import com.xkk.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "MessageServiceImpl")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;


    @Cacheable(value = "getMessagesByTouserid")
    @Override
    public List<Message> getMessagesByTouserid(int touserid) {
        return messageMapper.getMessagesByTouserid(touserid);
    }

    @CacheEvict(value = {"getMessageCountByTouserid","getUserByUserid"})
    @Override
    public int updateMessageStateByMessageid(int messageid) {
        return messageMapper.updateMessageStateByMessageid(messageid);
    }

    @CacheEvict(value = {"getMessageCountByTouserid","getUserByUserid"})
    @Override
    public int addMessageByMessage(Message message) {
        if(message.getFromuserid()==message.getTouserid()){
            return 0;
        }
        return messageMapper.addMessageByMessage(message);
    }

    @Cacheable(value = "getMessageCountByTouserid")
    @Override
    public int getMessageCountByTouserid(int touserid) {
        return messageMapper.getMessageCountByTouserid(touserid);
    }

    @Cacheable(value = "getMessagesByFromuserid")
    @Override
    public List<Message> getMessagesByFromuserid(int fromuserid) {
        return messageMapper.getMessagesByFromuserid(fromuserid);
    }
}

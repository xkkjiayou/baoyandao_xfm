package com.xkk.dao;

import com.xkk.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MessageMapper {

    //找到该我接收的消息
    @Select("SELECT * FROM tbl_message WHERE touserid=#{touserid} AND state=0")
    public List<Message> getMessagesByTouserid(int touserid);

    //找到我主动发出的消息
    @Select("SELECT * FROM tbl_message WHERE fromuserid=#{fromuserid}")
    public List<Message> getMessagesByFromuserid(int fromuserid);

    //找到该我接收的消息
    @Update("UPDATE tbl_message SET state = 1 WHERE messageid=#{messageid}")
    public int updateMessageStateByMessageid(int messageid);

    @Insert("INSERT INTO tbl_message(fromuserid,touserid,content,postid) VALUES(#{message.fromuserid},#{message.touserid},#{message.content},#{message.postid})")
    public int addMessageByMessage(@Param("message") Message message);

    @Select("SELECT COUNT(*) FROM tbl_message WHERE touserid=#{touserid} AND state=0")
    public int getMessageCountByTouserid(int touserid);
}

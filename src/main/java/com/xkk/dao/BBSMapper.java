package com.xkk.dao;

import com.xkk.pojo.*;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface BBSMapper {

    //    帖子相关
    @Select("SELECT tbl_post.userid,tbl_post.postid,tbl_post.title," +
            " tbl_post.datetime,tbl_post.viewnums,tbl_post.typeid,tbl_post.priority," +
            "tbl_post.like_nums,tbl_post.last_reply_datetime,COUNT(tbl_comment.commentid) AS comment_nums " +
            "FROM tbl_post " +
            "LEFT JOIN tbl_comment on tbl_post.postid=tbl_comment.postid "+
            "WHERE tbl_post.typeid<=10  " +
            "GROUP BY tbl_post.postid " +
            "ORDER BY tbl_post.priority desc , tbl_post.datetime desc LIMIT 10")
    List<Post> getPostByAll();

    //    帖子相关 根据typeid搜索post
    @Select("SELECT tbl_post.userid,tbl_post.postid,tbl_post.title,tbl_post.datetime,tbl_post.viewnums," +
            "tbl_post.typeid,tbl_post.priority,tbl_post.like_nums,tbl_post.last_reply_datetime,tbl_post.keyword_seg," +
            "COUNT(tbl_comment.commentid) AS comment_nums " +
            "FROM tbl_post " +
            "LEFT JOIN tbl_comment on tbl_post.postid=tbl_comment.postid "+
            "LEFT JOIN tbl_user on tbl_user.userid=tbl_post.userid "+
            "WHERE tbl_post.typeid=#{typeid} " +
            "GROUP BY tbl_post.postid " +
            "ORDER BY tbl_post.priority desc , tbl_post.datetime desc")
    List<Post> getPostByTypeid(int typeid);

    //    搜索帖子根据关键字
    @Select("SELECT userid,postid,title,datetime,viewnums,typeid,priority," +
            "like_nums,last_reply_datetime " +
            "FROM tbl_post " +
            "WHERE (title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%')) " +
            "ORDER BY priority desc ,datetime desc")
    List<Post> getPostByKeyword(String keyword);

    //    搜索帖子根据关键字+根据主题
    @Select("SELECT userid,postid,title,datetime,viewnums,typeid,priority,like_nums,last_reply_datetime FROM tbl_post WHERE (title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%'))  AND typeid=#{typeid} ORDER BY priority desc ,datetime desc")
    List<Post> getPostByKeywordAndTypeid(@Param("keyword") String keyword,@Param("typeid") int typeid);

    //    帖子总数
    @Select("SELECT COUNT(*) FROM tbl_post")
    int getPostCount();
    //    帖子总数根据类别
    @Select("SELECT COUNT(*) FROM tbl_post WHERE typeid=#{typeid}")
    int getPostCountByTypeid(int typeid);
    //    帖子总数根据类别与关键字
    @Select("SELECT COUNT(*) FROM tbl_post WHERE typeid=#{typeid} AND (title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%') ) ")
    int getPostCountByTypeidAndKeyword(@Param("typeid") int typeid,@Param("keyword") String keyword);
    @Select("SELECT tbl_post.typeid,COUNT(*) AS nums,typename FROM tbl_post JOIN tbl_post_type ON tbl_post_type.typeid=tbl_post.typeid " +
            "WHERE (title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%') ) " +
            "GROUP BY typeid ORDER BY typeid asc")
    List<Type> getTypePostCount(String keyword);

    //    帖子总数
    @Select("SELECT COUNT(*) FROM tbl_post WHERE (title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%') ) ")
    int getPostCountByKeyword(String keyword);

    @Select("SELECT * FROM tbl_post WHERE userid=#{uid} ORDER BY priority desc")
    List<Post> getPostByUserid(int uid);
    @Select("SELECT tbl_post.userid,tbl_post.postid,tbl_post.title,tbl_post.content,tbl_post.priority, " +
            "tbl_post.like_nums,tbl_post.last_reply_datetime," +
            "tbl_post.viewnums,tbl_post.typeid, " +
            "COUNT(tbl_comment.commentid) AS comment_nums " +
            "FROM tbl_post " +
            "LEFT JOIN tbl_comment on tbl_post.postid=tbl_comment.postid "+
            "WHERE tbl_post.postid=#{postid}")
    List<Post> getPostByPostid(@Param("postid") int postid);

    @Insert("INSERT INTO tbl_post(userid,title,content,typeid) VALUES(#{post.userid},#{post.title},#{post.content},#{post.typeid})")
    @Options(useGeneratedKeys = true, keyProperty = "post.postid")//用于获得主键id
    int addPost(@Param("post") Post post);

    //更新帖子最后被 回复 评论的时间
    @Update("UPDATE tbl_post SET last_reply_datetime=#{last_reply_datetime} WHERE postid=#{postid}")
    int updateLast_reply_datetimeByPostid(@Param("last_reply_datetime")Timestamp last_reply_datetime,@Param("postid")int postid);


//    评论相关
    @Select("SELECT * FROM tbl_comment WHERE postid=#{postid} ORDER BY datetime desc")
    List<Comment> getCommentByPostid(int postid);

    @Insert("INSERT INTO tbl_comment(userid,postid,content) VALUES(#{comment.userid},#{comment.postid},#{comment.content})")
    @Options(useGeneratedKeys = true, keyProperty = "comment.commentid")//用于获得主键id
    int addComment(@Param("comment") Comment comment);

    @Select("SELECT COUNT(*) FROM tbl_comment WHERE postid=#{postid}")
    int countComment(int postid);

    @Select("SELECT * FROM tbl_comment WHERE userid=#{userid} ORDER BY datetime desc")
    List<Comment> getCommentByUserid(int userid);

    //回复相关
    @Insert("INSERT INTO tbl_reply(my_userid,other_userid,commentid,postid,content) VALUES(#{reply.my_userid},#{reply.other_userid},#{reply.commentid},#{reply.postid},#{reply.content})")
    @Options(useGeneratedKeys = true, keyProperty = "reply.replyid")//用于获得主键id
    int addReply(@Param("reply") Reply reply);

    @Select("SELECT * FROM tbl_reply WHERE commentid=#{commentid} ORDER BY datetime asc")
    List<Reply> getReplyByCommentid(int commentid);

    //找到被回复人otheruserid的信息
    @Select("SELECT * FROM tbl_reply WHERE other_userid=#{other_userid} ORDER BY datetime asc")
    List<Reply> getReplyByOtherUserid(int other_userid);

    //找到主动回复人myuserid的信息
    @Select("SELECT * FROM tbl_reply WHERE my_userid=#{my_userid} ORDER BY datetime asc")
    List<Reply> getReplyByMyUserid(int my_userid);


    //关于类别
    @Select("SELECT * FROM tbl_post_type WHERE typeid>=#{st} AND typeid<=#{gt}")
    List<Type> getTypeByIdRange(@Param("st") int st,@Param("gt") int gt);//小于大于

    @Select("SELECT * FROM tbl_post_type WHERE typeid=#{typeid}")
    Type getTypeByTypeid(int typeid);


    //智慧搜索-提取所有post的title
    @Select("SELECT title FROM tbl_post ORDER BY postid DESC")
    public List<String> getALLTitleForSuggestSearch();
}

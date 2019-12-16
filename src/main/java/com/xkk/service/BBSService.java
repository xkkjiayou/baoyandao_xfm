package com.xkk.service;

import com.xkk.pojo.*;

import java.util.List;

public interface BBSService {
    List<Post> getPostByUserid(int userid);
    List<Post> getPostByAll(int page_start,int nums);
    List<Post> getPostByPostid(int postid, User user);
    List<Post> getPostByTypeid(int typeid,int page_start,int nums);

    int addPost(Post post);

    List<Comment> getCommentByPostid(int postid,int page_start,int nums,User user);
    int addComment(Comment comment);
    int getPostCountByTypeid(int typeid);
    int getPostCountByTypeidAndKeyword(int typeid,  String keyword);
    List<Type> getTypePostCount(String keyword);

    int addReply(Reply reply);
    List<Reply> getReplyByCommentid(int commentid,User user);

    int countComment(int postid);
    int getPostCount();
    int getPostCountByKeyword(String keyword);
    List<Post> getPostByKeyword(int page_start,int nums,String keyword);
    List<Post> getPostByKeywordAndTypeid(int postid,int page_start,String keyword,int typeid);
    List<Type> getTypeByIdRange(int st,int gt);//小于大于
    public List<String> getALLTitleForSuggestSearch();
}

package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Post  implements Serializable {
    int userid;//发帖人
    int postid;
    String title;
    int like_nums;
    String content;
    Timestamp datetime;
    List<Comment> comments;
    List<String> raw_content;
    String keyword_seg;
    int comment_nums;
    User user;
    int viewnums;
    //11---专业排名类文章\\n12--专业介绍类文章\\n21--学校排名类文章\\n22--学校介绍类文章\n01---用户杂聊
    int typeid;
    Type type;
    //优先级：\n优先级数值\n越大 越靠前
    int priority;
    boolean isLiked;
    Timestamp last_reply_datetime;
    String howlong_last_reply;//5分钟前有人回复

    @Override
    public String toString() {
        return "Post [userid=" + userid +
                ", postid=" + postid +
                ", title=" + title +
                ", like_nums=" + like_nums +
                ", content=" + content +
                ", datetime=" + datetime +
                ", comments=" + comments +
                ", comments=" + comments +
                ", raw_content=" + raw_content +
                ", keyword_seg=" + keyword_seg +
                ", comment_nums=" + comment_nums +
                ", user=" + user +
                ", viewnums=" + viewnums +
                ", type=" + type +
                ", typeid=" + typeid +
                ", priority=" + priority +
                ", isLiked=" + isLiked +
                ", last_reply_datetime=" + last_reply_datetime +
                ", howlong_last_reply=" + howlong_last_reply +
                "]";
    }
    public String getKeyword_seg() {
        return keyword_seg;
    }

    public void setKeyword_seg(String keyword_seg) {
        this.keyword_seg = keyword_seg;
    }

    public List<String> getRaw_content() {
        return raw_content;
    }

    public void setRaw_content(List<String> raw_content) {
        this.raw_content = raw_content;
    }

    public String getHowlong_last_reply() {
        return howlong_last_reply;
    }

    public void setHowlong_last_reply(String howlong_last_reply) {
        this.howlong_last_reply = howlong_last_reply;
    }

    public Timestamp getLast_reply_datetime() {
        return last_reply_datetime;
    }

    public void setLast_reply_datetime(Timestamp last_reply_datetime) {
        this.last_reply_datetime = last_reply_datetime;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getLike_nums() {
        return like_nums;
    }

    public void setLike_nums(int like_nums) {
        this.like_nums = like_nums;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getViewnums() {
        return viewnums;
    }

    public void setViewnums(int viewnums) {
        this.viewnums = viewnums;
    }

    public int getComment_nums() {
        return comment_nums;
    }

    public void setComment_nums(int comment_nums) {
        this.comment_nums = comment_nums;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}

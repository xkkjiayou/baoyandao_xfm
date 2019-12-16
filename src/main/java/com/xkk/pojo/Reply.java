package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reply  implements Serializable {
    int my_userid;
    int other_userid;
    int commentid;
    int postid;
    int replyid;
    Timestamp datetime;
    int like_nums;
    String content;
    User my_user;
    User other_user;
    boolean isLiked;

    @Override
    public String toString() {
        return "Reply [my_userid=" + my_userid +
                ", other_userid=" + other_userid +
                ", commentid=" + commentid +
                ", postid=" + postid +
                ", replyid=" + replyid +
                ", datetime=" + datetime +
                ", like_nums=" + like_nums +
                ", content=" + content +
                ", my_user=" + my_user +
                ", other_user=" + other_user +
                ", isLiked=" + isLiked +
                "]";
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

    public int getReplyid() {
        return replyid;
    }

    public void setReplyid(int replyid) {
        this.replyid = replyid;
    }

    public int getMy_userid() {
        return my_userid;
    }

    public void setMy_userid(int my_userid) {
        this.my_userid = my_userid;
    }

    public int getOther_userid() {
        return other_userid;
    }

    public void setOther_userid(int other_userid) {
        this.other_userid = other_userid;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getMy_user() {
        return my_user;
    }

    public void setMy_user(User my_user) {
        this.my_user = my_user;
    }

    public User getOther_user() {
        return other_user;
    }

    public void setOther_user(User other_user) {
        this.other_user = other_user;
    }
}

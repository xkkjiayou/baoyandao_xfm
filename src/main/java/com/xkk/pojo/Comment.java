package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Comment  implements Serializable {

    int commentid;
    int postid;
    int userid;
    User user;
    String content;
    int like_nums;
    Timestamp datetime;
    List<Reply> replies;
    int reply_nums;
    boolean isLiked;

    @Override
    public String toString() {
        return "Comment [commentid=" + commentid +
                ", postid=" + postid +
                ", userid=" + userid +
                ", user=" + user +
                ", content=" + content +
                ", like_nums=" + like_nums +
                ", datetime=" + datetime +
                ", replies=" + replies +
                ", reply_nums=" + reply_nums +
                ", isLiked=" + isLiked +
                "]";
    }
    public int getReply_nums() {
        return reply_nums;
    }

    public void setReply_nums(int reply_nums) {
        this.reply_nums = reply_nums;
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

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

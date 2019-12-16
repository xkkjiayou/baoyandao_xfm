package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message  implements Serializable {
    int fromuserid;
    int touserid;
    int postid;
    int messageid;
    String content;
    Post post;
    User fromuser;
    User touser;
    Timestamp datetime;
    @Override
    public String toString() {
        return "Message [fromuserid=" + fromuserid +
                ", touserid=" + touserid +
                ", postid=" + postid +
                ", messageid=" + messageid +
                ", content=" + content +
                ", fromuser=" + fromuser +
                ", touser=" + touser +
                ", datetime=" + datetime +
                "]";
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(int fromuserid) {
        this.fromuserid = fromuserid;
    }

    public int getTouserid() {
        return touserid;
    }

    public void setTouserid(int touserid) {
        this.touserid = touserid;
    }

    public User getFromuser() {
        return fromuser;
    }

    public void setFromuser(User fromuser) {
        this.fromuser = fromuser;
    }

    public User getTouser() {
        return touser;
    }

    public void setTouser(User touser) {
        this.touser = touser;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}

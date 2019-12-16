package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Record  implements Serializable {
    int postid;
    int userid;
    int recordid;
    int universityid;
    String user_universityname;
    Post post;
    User user;
    Timestamp dateime;
    int nums;//重复浏览了多少次

    @Override
    public String toString() {
        return "Record [postid=" + postid +
                ", userid=" + userid +
                ", recordid=" + recordid +
                ", universityid=" + universityid +
                ", user_universityname=" + user_universityname +
                ", post=" + post +
                ", user=" + user +
                ", dateime=" + dateime +
                ", nums=" + nums +
                "]";
    }
    public int getUniversityid() {
        return universityid;
    }

    public void setUniversityid(int universityid) {
        this.universityid = universityid;
    }

    public String getUser_universityname() {
        return user_universityname;
    }

    public void setUser_universityname(String user_universityname) {
        this.user_universityname = user_universityname;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
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

    public int getRecordid() {
        return recordid;
    }

    public void setRecordid(int recordid) {
        this.recordid = recordid;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDateime() {
        return dateime;
    }

    public void setDateime(Timestamp dateime) {
        this.dateime = dateime;
    }
}

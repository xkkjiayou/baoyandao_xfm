package com.xkk.pojo;

import java.io.Serializable;

public class Recommend  implements Serializable {
    int userid;
    int postid;
    int universityid;
    String my_universityname;
    int recommendid;
    double score;
    Post post;
    University university;

    @Override
    public String toString() {
        return "Recommend [userid=" + userid +
                ", postid=" + postid +
                ", universityid=" + universityid +
                ", my_universityname=" + my_universityname +
                ", recommendid=" + recommendid +
                ", score=" + score +
                ",post="+post+
                ",university="+university+
                "]";
    }

    public int getUniversityid() {
        return universityid;
    }

    public void setUniversityid(int universityid) {
        this.universityid = universityid;
    }

    public String getMy_universityname() {
        return my_universityname;
    }

    public void setMy_universityname(String my_universityname) {
        this.my_universityname = my_universityname;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public int getRecommendid() {
        return recommendid;
    }

    public void setRecommendid(int recommendid) {
        this.recommendid = recommendid;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

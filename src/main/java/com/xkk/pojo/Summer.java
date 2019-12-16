package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Summer  implements Serializable {

    int universityid;
    int summerid;
    String content;
    University university;
    Timestamp datetime;
    String starttime;
    String summername;
    String endtime;
    String universityname;
    @Override
    public String toString() {
        return "Summer [universityid=" + universityid +
                ", summerid=" + summerid +
                ", content=" + content +
                ", university=" + university +
                ", datetime=" + datetime +
                ", starttime=" + starttime +
                ", summername=" + summername +
                ", endtime=" + endtime +
                ", universityname=" + universityname +
                "]";
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getSummername() {
        return summername;
    }

    public void setSummername(String summername) {
        this.summername = summername;
    }

    public int getUniversityid() {
        return universityid;
    }

    public void setUniversityid(int universityid) {
        this.universityid = universityid;
    }

    public int getSummerid() {
        return summerid;
    }

    public void setSummerid(int summerid) {
        this.summerid = summerid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}

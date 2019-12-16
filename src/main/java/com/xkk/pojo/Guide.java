package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Guide  implements Serializable {
    int universityid;
    int guideid;
    String content;
    University university;
    Timestamp datetime;
    String universityname;
    String collegename;
    String guidename;

    @Override
    public String toString() {
        return "Guide [universityid=" + universityid +
                ", guideid=" + guideid +
                ", content=" + content +
                ", university=" + university +
                ", datetime=" + datetime +
                ", universityname=" + universityname +
                ", collegename=" + collegename +
                ", guidename=" + guidename +
                "]";
    }
    public int getUniversityid() {
        return universityid;
    }

    public void setUniversityid(int universityid) {
        this.universityid = universityid;
    }

    public int getGuideid() {
        return guideid;
    }

    public void setGuideid(int guideid) {
        this.guideid = guideid;
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

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getGuidename() {
        return guidename;
    }

    public void setGuidename(String guidename) {
        this.guidename = guidename;
    }
}

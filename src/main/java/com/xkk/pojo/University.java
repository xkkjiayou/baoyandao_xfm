package com.xkk.pojo;


import java.io.Serializable;
import java.util.List;

public class University implements Serializable {

    int universityid;
    String universityname;
    String logo;
    String tags;
    int universityrank;
    String university_introducation;
    String tags_xuewei;
    String special_major;
    List<Summer> summers;
    List<Guide> guides;
    String university;
    String universitylevel;
    //关注这个学校的人数与级别
    List<Type> view_level_types;
    int viewnums;
    double viewpercent;

    @Override
    public String toString() {
        return "University [universityid=" + universityid +
                ", universityname=" + universityname +
                ", logo=" + logo +
                ", tags=" + tags +
                ", universityrank=" + universityrank +
                ", university_introducation=" + university_introducation +
                ", tags_xuewei=" + tags_xuewei +
                ", special_major=" + special_major +
                ", summers=" + summers +
                ", guides=" + guides +
                ", university=" + university +
                ", universitylevel=" + universitylevel +
                ", view_level_types=" + view_level_types +
                ", viewnums=" + viewnums +
                ", viewpercent=" + viewpercent +
                "]";
    }
    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        this.guides = guides;
    }

    public double getViewpercent() {
        return viewpercent;
    }

    public void setViewpercent(double viewpercent) {
        this.viewpercent = viewpercent;
    }

    public int getViewnums() {
        return viewnums;
    }

    public void setViewnums(int viewnums) {
        this.viewnums = viewnums;
    }

    public List<Type> getView_level_types() {
        return view_level_types;
    }

    public void setView_level_types(List<Type> view_level_types) {
        this.view_level_types = view_level_types;
    }

    public String getUniversitylevel() {
        return universitylevel;
    }

    public void setUniversitylevel(String universitylevel) {
        this.universitylevel = universitylevel;
    }

    public int getUniversityid() {
        return universityid;
    }

    public void setUniversityid(int universityid) {
        this.universityid = universityid;
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getUniversityrank() {
        return universityrank;
    }

    public void setUniversityrank(int universityrank) {
        this.universityrank = universityrank;
    }

    public String getUniversity_introducation() {
        return university_introducation;
    }

    public void setUniversity_introducation(String university_introducation) {
        this.university_introducation = university_introducation;
    }

    public String getTags_xuewei() {
        return tags_xuewei;
    }

    public void setTags_xuewei(String tags_xuewei) {
        this.tags_xuewei = tags_xuewei;
    }

    public String getSpecial_major() {
        return special_major;
    }

    public void setSpecial_major(String special_major) {
        this.special_major = special_major;
    }

    public List<Summer> getSummers() {
        return summers;
    }

    public void setSummers(List<Summer> summers) {
        this.summers = summers;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}

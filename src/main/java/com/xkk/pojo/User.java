package com.xkk.pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User  implements Serializable {
    int userid;
    boolean user_state;
    String username;
    String password;
    String nickname;
    String university;
    String age;
    String sex;
    String desc;
    String major;
    String email;
    String phone;
    String touxiang;
    int message_nums;
    int fromuserid;

    public int getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(int fromuserid) {
        this.fromuserid = fromuserid;
    }

    @Override
    public String toString() {
        return "User [userid=" + userid +
                ", username=" + username +
                ", password=" + password +
                ", nickname=" + nickname +
                ", university=" + password +
                ", age=" + age +
                ", sex=" + sex +
                ", desc=" + desc +
                ", major=" + major +
                ", email=" + email +
                ", phone=" + phone +
                ", touxiang=" + touxiang +
                ", message_nums=" + message_nums +
                "]";
    }

    public int getMessage_nums() {
        return message_nums;
    }

    public void setMessage_nums(int message_nums) {
        this.message_nums = message_nums;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public User() {
        this.user_state = false;
    }

    public boolean isUser_state() {
        return user_state;
    }

    public void setUser_state(boolean user_state) {
        this.user_state = user_state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

}

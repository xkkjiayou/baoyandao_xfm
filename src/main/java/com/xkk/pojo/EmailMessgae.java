package com.xkk.pojo;

import java.io.Serializable;

public class EmailMessgae implements Serializable {
    String toemail;
    String title;
    String content;
    String posttitle;
    String nickname;
    int postid;

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public EmailMessgae(String toemail, String title, String content, String posttitle, String nickname, int postid) {
        this.toemail = toemail;
        this.title = title;
        this.content = content;
        this.posttitle = posttitle;
        this.nickname = nickname;
        this.postid = postid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }


    public String getToemail() {
        return toemail;
    }

    public void setToemail(String toemail) {
        this.toemail = toemail;
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
}

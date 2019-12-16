package com.xkk.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Type  implements Serializable {
    int typeid;
    String typename;
    Timestamp datetime;
    int nums;//记录主题下有多少个帖子
    String color;

    @Override
    public String toString() {
        return "Type [typeid=" + typeid +
                ", typename=" + typename +
                ", datetime=" + datetime +
                ", nums=" + nums +
                ", color=" + color +
                "]";
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}

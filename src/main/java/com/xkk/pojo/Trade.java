package com.xkk.pojo;

import java.sql.Timestamp;

public class Trade {
    String tradeno;
    int tradeid;
    double price;
    int userid;
    int productid;
    Timestamp datetime;

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }

    public int getTradeid() {
        return tradeid;
    }

    public void setTradeid(int tradeid) {
        this.tradeid = tradeid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}

package com.xkk.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Product {
    String productname;
    int productid;
    double price;
    String snapshot;
    String images;
    List<String> images_array;
    Timestamp datetime;
    int viewnums;
    int buynums;
    String downloadurl;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<String> getImages_array() {
        return images_array;
    }

    public void setImages_array(List<String> images_array) {
        this.images_array = images_array;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public int getViewnums() {
        return viewnums;
    }

    public void setViewnums(int viewnums) {
        this.viewnums = viewnums;
    }

    public int getBuynums() {
        return buynums;
    }

    public void setBuynums(int buynums) {
        this.buynums = buynums;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}

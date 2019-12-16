package com.xkk.pojo;

import java.io.Serializable;

public class IpVo implements Serializable {
    private Integer code;
    private Address data;
    @Override
    public String toString() {
        return "IpVo [code=" + code +
                ", data=" + data +
                "]";
    }

    public class Address implements Serializable{
        private String ip;
        private String region;
        private String city;
        @Override
        public String toString() {
            return "Address [ip=" + ip +
                    ", region=" + region +
                    ", city=" + city +
                    "]";
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Address getData() {
        return data;
    }

    public void setData(Address data) {
        this.data = data;
    }
}
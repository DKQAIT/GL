package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class UserListModel  implements Serializable {
//    "uid": 81,
//            "really_name": "张三",
//            "phone": "130000000000",
//            "brand": "京A666666",
//            "start_time": "1537459200",
//            "end_time": "1540079400",
//            "pid": 192,
//            "type": 1

    private  int uid;
    private String really_name;
    private String phone;
    private String brand;
    private String start_time;
    private String end_time;
    private  int pid;
    private  int type;


    public UserListModel() {
    }

    public UserListModel(int uid, String really_name, String phone, String brand, String start_time, String end_time, int pid, int type) {
        this.uid = uid;
        this.really_name = really_name;
        this.phone = phone;
        this.brand = brand;
        this.start_time = start_time;
        this.end_time = end_time;
        this.pid = pid;
        this.type = type;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getReally_name() {
        return really_name;
    }

    public void setReally_name(String really_name) {
        this.really_name = really_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "UserListModel{" +
                "uid=" + uid +
                ", really_name='" + really_name + '\'' +
                ", phone='" + phone + '\'' +
                ", brand='" + brand + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", pid=" + pid +
                ", type=" + type +
                '}';
    }
}

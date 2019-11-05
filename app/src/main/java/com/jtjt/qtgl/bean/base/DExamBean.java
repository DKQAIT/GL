package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class DExamBean  implements Serializable {

//    private "uid": 53,
//            "phone": "13000000000",
//            "really_name": "张三"


    private  int uid;
    private String phone;
    private String really_name;

    private  String address_lbs;
    private long add_time;
    private long authentication_time;

    public long getAuthentication_time() {
        return authentication_time;
    }

    public void setAuthentication_time(long authentication_time) {
        this.authentication_time = authentication_time;
    }

    public String getAddress_lbs() {
        return address_lbs;
    }

    public void setAddress_lbs(String address_lbs) {
        this.address_lbs = address_lbs;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public DExamBean() {
    }

    public DExamBean(int uid, String phone, String really_name) {
        this.uid = uid;
        this.phone = phone;
        this.really_name = really_name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReally_name() {
        return really_name;
    }

    public void setReally_name(String really_name) {
        this.really_name = really_name;
    }

    @Override
    public String toString() {
        return "DExamBean{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", really_name='" + really_name + '\'' +
                '}';
    }
}

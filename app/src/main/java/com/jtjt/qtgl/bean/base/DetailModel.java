package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class DetailModel   implements Serializable {
//
//     "phone": "13000000000",
//             "total_money": 154.2,
//             "add_time": 1529371409,
//             "status": 1,
//             "really_name": "张三",
//             "num_id": "110111199001011234",
//             "brand": "京A123456",
//             "num_positive_img": "/uploads/20180626/10cfec0365708a86a6a588e174cfee1d.jpg",
//             "num_side_img": "/uploads/20180626/016192771d4a2dd3fc2ea00e2e2a00f2.jpg",
//             "deposit": 1

    private String phone;
    private  String total_money;
    private String add_time;
    private  int status;
    private  String really_name;
    private  String num_id;
    private String brand;
    private  String num_positive_img;
    private  String num_side_img;
    private  int deposit;
    private  String face;
    private String authentication_time;


    public String getAuthentication_time() {
        return authentication_time;
    }

    public void setAuthentication_time(String authentication_time) {
        this.authentication_time = authentication_time;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public DetailModel() {
    }

    public DetailModel(String phone, String total_money, String add_time, int status, String really_name, String num_id, String brand, String num_positive_img, String num_side_img, int deposit) {
        this.phone = phone;
        this.total_money = total_money;
        this.add_time = add_time;
        this.status = status;
        this.really_name = really_name;
        this.num_id = num_id;
        this.brand = brand;
        this.num_positive_img = num_positive_img;
        this.num_side_img = num_side_img;
        this.deposit = deposit;
    }


    @Override
    public String toString() {
        return "DetailModel{" +
                "phone='" + phone + '\'' +
                ", total_money='" + total_money + '\'' +
                ", add_time='" + add_time + '\'' +
                ", status=" + status +
                ", really_name='" + really_name + '\'' +
                ", num_id='" + num_id + '\'' +
                ", brand='" + brand + '\'' +
                ", num_positive_img='" + num_positive_img + '\'' +
                ", num_side_img='" + num_side_img + '\'' +
                ", deposit=" + deposit +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReally_name() {
        return really_name;
    }

    public void setReally_name(String really_name) {
        this.really_name = really_name;
    }

    public String getNum_id() {
        return num_id;
    }

    public void setNum_id(String num_id) {
        this.num_id = num_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNum_positive_img() {
        return num_positive_img;
    }

    public void setNum_positive_img(String num_positive_img) {
        this.num_positive_img = num_positive_img;
    }

    public String getNum_side_img() {
        return num_side_img;
    }

    public void setNum_side_img(String num_side_img) {
        this.num_side_img = num_side_img;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }
}

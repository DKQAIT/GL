package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class ParkDetailBean  implements Serializable{
//       "id": 176,
//               "name": "地磁",
//               "model": "6-8",
//               "brand": "品牌名称",
//               "number": "30",
//               "contact": "厂家联系人",
//               "phone": "300000",
//               "enterstatus": "否",
//               "enternum": 0,
//               "outstatus": "否",
//               "outnum": 0
    private int id;
    private String name;
    private String model;
    private String brand;
    private String number;
    private String contact;
    private String phone;
    private String enterstatus;
    private  int enternum;
    private String outstatus;
    private  int outnum;

    public ParkDetailBean() {
    }

    public ParkDetailBean(int id, String name, String model, String brand, String number, String contact, String phone, String enterstatus, int enternum, String outstatus, int outnum) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.number = number;
        this.contact = contact;
        this.phone = phone;
        this.enterstatus = enterstatus;
        this.enternum = enternum;
        this.outstatus = outstatus;
        this.outnum = outnum;
    }

    @Override
    public String toString() {
        return "ParkDetailBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", number='" + number + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", enterstatus='" + enterstatus + '\'' +
                ", enternum=" + enternum +
                ", outstatus='" + outstatus + '\'' +
                ", outnum=" + outnum +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEnterstatus() {
        return enterstatus;
    }

    public void setEnterstatus(String enterstatus) {
        this.enterstatus = enterstatus;
    }

    public int getEnternum() {
        return enternum;
    }

    public void setEnternum(int enternum) {
        this.enternum = enternum;
    }

    public String getOutstatus() {
        return outstatus;
    }

    public void setOutstatus(String outstatus) {
        this.outstatus = outstatus;
    }

    public int getOutnum() {
        return outnum;
    }

    public void setOutnum(int outnum) {
        this.outnum = outnum;
    }
}

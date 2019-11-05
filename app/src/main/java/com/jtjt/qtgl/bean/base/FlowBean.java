package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class FlowBean implements Serializable {
//     "phone": "17600538580",
//             "money": "-300",
//             "time": 1544494659,
//             "pay_type": 3,
//             "type": 2
    private String phone;
    private  String money;
    private long time;
    private  int pay_type;
    private  int type;

    public FlowBean() {
    }

    public FlowBean(String phone, String money, long time, int pay_type, int type) {
        this.phone = phone;
        this.money = money;
        this.time = time;
        this.pay_type = pay_type;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FlowBean{" +
                "phone='" + phone + '\'' +
                ", money='" + money + '\'' +
                ", time=" + time +
                ", pay_type=" + pay_type +
                ", type=" + type +
                '}';
    }
}

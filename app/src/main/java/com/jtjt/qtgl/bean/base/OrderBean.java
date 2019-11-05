package com.jtjt.qtgl.bean.base;

public class OrderBean {
//
//  "aid": 9,
//          "username": "lizhangyong",
//          "logintime": 0,
//          "loginip": "",
//          "islock": 0,
//          "group_id": "1",
//          "module": "admin/root",
//          "is_top": 1,
//          "logintoken": "0ea0c32415f23e58c52b97837997dd0b"

    private String aid;
    private String username;
    private String logintime;
    private String loginip;
    private String islock;
    private String group_id;
    private String module;
    private String is_top;
    private String logintoken;


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public String getIslock() {
        return islock;
    }

    public void setIslock(String islock) {
        this.islock = islock;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getLogintoken() {
        return logintoken;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }


    public OrderBean() {
    }

    public OrderBean(String aid, String username, String logintime, String loginip, String islock, String group_id, String module, String is_top, String logintoken) {
        this.aid = aid;
        this.username = username;
        this.logintime = logintime;
        this.loginip = loginip;
        this.islock = islock;
        this.group_id = group_id;
        this.module = module;
        this.is_top = is_top;
        this.logintoken = logintoken;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "aid='" + aid + '\'' +
                ", username='" + username + '\'' +
                ", logintime='" + logintime + '\'' +
                ", loginip='" + loginip + '\'' +
                ", islock='" + islock + '\'' +
                ", group_id='" + group_id + '\'' +
                ", module='" + module + '\'' +
                ", is_top='" + is_top + '\'' +
                ", logintoken='" + logintoken + '\'' +
                '}';
    }
}

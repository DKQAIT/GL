package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class DeviceBean  implements Serializable {
//      "id": 327,
//              "name": "测试",
//              "create_time": 1543549732,
//              "up_time": 0,
//              "pid": 193,
//              "p_type": 1,
//              "remarks": "测试说明",
//              "status": 1,
//              "username": "dongkaiqiang"
    private  int id;
    private  String name;
    private long  create_time;
    private long up_time;
    private  int pid;
    private  int p_type;
    private  String remarks;
    private  int status;
    private  String username;

    public DeviceBean() {
    }

    public DeviceBean(int id, String name, long create_time, long up_time, int pid, int p_type, String remarks, int status, String username) {
        this.id = id;
        this.name = name;
        this.create_time = create_time;
        this.up_time = up_time;
        this.pid = pid;
        this.p_type = p_type;
        this.remarks = remarks;
        this.status = status;
        this.username = username;
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

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUp_time() {
        return up_time;
    }

    public void setUp_time(long up_time) {
        this.up_time = up_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getP_type() {
        return p_type;
    }

    public void setP_type(int p_type) {
        this.p_type = p_type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "DeviceBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", create_time=" + create_time +
                ", up_time=" + up_time +
                ", pid=" + pid +
                ", p_type=" + p_type +
                ", remarks='" + remarks + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }
}

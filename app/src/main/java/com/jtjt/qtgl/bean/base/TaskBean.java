package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class TaskBean  implements Serializable {

//        "id": 320,
//                "title": "asdfadsf ",
//                "create_time": 1543995837,
//                "up_time": 0,
//                "content": null,
//                "status": 1,
//                "username": "dongkaiqiang",
//                "pid": 193,
//                "p_type": 1,
//                "name": "测试"

    private  int id;
    private String title;
    private long create_time;
    private  long up_time;
    private  String content;
    private  int status;
    private  String username;
    private  int pid;
    private  int p_type;
    private  String name;


    public TaskBean() {
    }

    public TaskBean(int id, String title, long create_time, long up_time, String content, int status, String username, int pid, int p_type, String name) {
        this.id = id;
        this.title = title;
        this.create_time = create_time;
        this.up_time = up_time;
        this.content = content;
        this.status = status;
        this.username = username;
        this.pid = pid;
        this.p_type = p_type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", create_time=" + create_time +
                ", up_time=" + up_time +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", pid=" + pid +
                ", p_type=" + p_type +
                ", name='" + name + '\'' +
                '}';
    }
}

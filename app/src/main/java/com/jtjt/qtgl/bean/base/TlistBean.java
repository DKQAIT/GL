package com.jtjt.qtgl.bean.base;

import java.io.Serializable;
import java.util.List;

public class TlistBean implements Serializable {
//  "id": 317,
//          "img": "[\"\\/uploads\\/20181204\\/631468452a846af3af1a70374c5fd557.jpg\"]",
//          "create_time": 1543893926,
//          "content": "发个啥地方",
//          "aid": 9,
//          "tid": 315,
//          "status": 2,
//          "username": "lizhangyong"

    private  int id;
    private String img;
    private long create_time;
    private String content;
    private  int aid;
    private  int tid;
    private  int status;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String  img) {
        this.img = img;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
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
        return "TlistBean{" +
                "id=" + id +
                ", img=" + img +
                ", create_time=" + create_time +
                ", content='" + content + '\'' +
                ", aid=" + aid +
                ", tid=" + tid +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }

    public TlistBean() {
    }

    public TlistBean(int id,String img, long create_time, String content, int aid, int tid, int status, String username) {
        this.id = id;
        this.img = img;
        this.create_time = create_time;
        this.content = content;
        this.aid = aid;
        this.tid = tid;
        this.status = status;
        this.username = username;
    }
}

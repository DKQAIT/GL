package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class MarkeModel   implements Serializable {
//     "id": 75,
//             "create_time": 1556508514,
//             "content": "测试但是",
//             "username": "chenjingkai"

    private  int id;
    private  long create_time;
    private String content;
    private String username;

    public MarkeModel() {
    }

    public MarkeModel(int id, long create_time, String content, String username) {
        this.id = id;
        this.create_time = create_time;
        this.content = content;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MarkeModel{" +
                "id=" + id +
                ", create_time=" + create_time +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

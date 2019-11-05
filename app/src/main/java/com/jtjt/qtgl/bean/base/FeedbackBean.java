package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

//问题反馈
public class FeedbackBean implements Serializable {

//    "id": 17,
//            "nickname": "17600538580",
//            "phone": "17600538580",
//            "content": "测试说明",
//            "create_time": 1544497116,
//            "status": 0,
//            "result": null,
//            "update_time": 0
    private  int id;
    private String nickname;
    private String phone;
    private String content;
    private long create_time;
    private  int status;
    private String result;
    private long update_time;

    public FeedbackBean() {
    }

    public FeedbackBean(int id, String nickname, String phone, String content, long create_time, int status, String result, long update_time) {
        this.id = id;
        this.nickname = nickname;
        this.phone = phone;
        this.content = content;
        this.create_time = create_time;
        this.status = status;
        this.result = result;
        this.update_time = update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "FeedbackBean{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                ", status=" + status +
                ", result='" + result + '\'' +
                ", update_time=" + update_time +
                '}';
    }
}

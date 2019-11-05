package com.jtjt.qtgl.bean.base;

import java.io.Serializable;
import java.util.List;

public class UserListInfo   implements Serializable {


//    "total": 55,
//            "page": "1",
//            "page_size": "5",
//            "all_page": 11,

    private  int total;
    private  int all_page;
    private String page;
    private String page_size;
    private List<UserListModel> data;

    public UserListInfo() {
    }

    public UserListInfo(int total, int all_page, String page, String page_size, List<UserListModel> data) {
        this.total = total;
        this.all_page = all_page;
        this.page = page;
        this.page_size = page_size;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
        this.all_page = all_page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public List<UserListModel> getData() {
        return data;
    }

    public void setData(List<UserListModel> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "UserListInfo{" +
                "total=" + total +
                ", all_page=" + all_page +
                ", page='" + page + '\'' +
                ", page_size='" + page_size + '\'' +
                ", data=" + data +
                '}';
    }
}

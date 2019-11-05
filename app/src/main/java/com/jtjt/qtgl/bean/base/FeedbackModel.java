package com.jtjt.qtgl.bean.base;

import java.io.Serializable;
import java.util.List;

public class FeedbackModel implements Serializable{
//
//     "total": 62,
//             "page": "1",
//             "page_size": "2",
//             "all_page": 31,
//             "data": [
    private int total;
    private String page;
    private  String page_size;
    private  int all_page;
    private List<FeedbackBean> data;

    public FeedbackModel() {
    }

    public FeedbackModel(int total, String page, String page_size, int all_page, List<FeedbackBean> data) {
        this.total = total;
        this.page = page;
        this.page_size = page_size;
        this.all_page = all_page;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
        this.all_page = all_page;
    }

    public List<FeedbackBean> getData() {
        return data;
    }

    public void setData(List<FeedbackBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FeedbackModel{" +
                "total=" + total +
                ", page='" + page + '\'' +
                ", page_size='" + page_size + '\'' +
                ", all_page=" + all_page +
                ", data=" + data +
                '}';
    }
}

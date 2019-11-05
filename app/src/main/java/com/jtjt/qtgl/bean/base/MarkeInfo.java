package com.jtjt.qtgl.bean.base;

import java.io.Serializable;
import java.util.List;
//留言列表

public class MarkeInfo implements Serializable{
//    "status":1,"msg":"success","data":[{"id":77,"create_time":1556519533,"content":"测试备注","username":"dongkaiqiang"}],"time":1000
//
//        "all_page":14,
//                "total":135,
//                "data":Array[10],
//            "page":"1",
//            "page_size":"10"

    private  int all_page;
    private int total;
    private String page;
    private  String page_size;

    private List<MarkeModel>data;

    public MarkeInfo() {
    }

    public MarkeInfo(int total, String page, String page_size, int all_page, List<MarkeModel> data) {
        this.total = total;
        this.page = page;
        this.page_size = page_size;
        this.all_page = all_page;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ParkInfo{" +
                "total='" + total + '\'' +
                ", page='" + page + '\'' +
                ", page_size='" + page_size + '\'' +
                ", all_page=" + all_page +
                ", data=" + data +
                '}';
    }


    public int getAll_page() {
        return all_page;
    }

    public void setAll_page(int all_page) {
        this.all_page = all_page;
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

    public List<MarkeModel> getData() {
        return data;
    }

    public void setData(List<MarkeModel> data) {
        this.data = data;
    }
}

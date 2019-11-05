package com.jtjt.qtgl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.adapter.MyParkDetailAdapter;
import com.jtjt.qtgl.adapter.MyRecyclerVAdapter;
import com.jtjt.qtgl.adapter.MyRecyclerVListAdapter;
import com.jtjt.qtgl.bean.base.ParkDetailBean;
import com.jtjt.qtgl.bean.base.ParkDetailModel;
import com.jtjt.qtgl.bean.base.TlistModel;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 停车场详情
 */

public class ParkDetailActivity  extends BaseActivity {


    @BindView(R.id.t_z)
    TextView t_z;
    @BindView(R.id.t_g)
    TextView t_g;
    @BindView(R.id.t_f)
    TextView t_f;
    @BindView(R.id.t_name)
    TextView t_name;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.left)
    ImageView left;
    private String uid;
    private ParkDetailModel ss;
    private List<ParkDetailBean> listbean;
    private MyParkDetailAdapter listAdapter;


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {

        setContentView(R.layout.parkdetail_activity);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {
        uid=getBundle().get("UID").toString();
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void initData() {


        ApiUtil.getApiService().PDetail( uid )
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {

                    public JSONArray js=null;

                    @Override
                    public void onHandleSuccess(String s) {

                        Log.e("上传成功返回","上传成功返回"+s.toString());

//                        "name": "国资委建材局停车场",
//                                "month_money": 300,
//                                "ppnum": 81,
//                                "ppsnum": 61,
//                                "ppnsnum": 20

                        JSONObject is= JSONObject.parseObject(s);
                        Log.e("获取信息","获取信息"+is.getString("img"));
                      String  name= is.getString("name");
                        t_name.setText("停车场名称："+is.getString("name"));
                        t_z.setText("车位总数："+is.getString("ppnum"));
                        t_g.setText("共享："+is.getString("ppsnum"));
                        t_f.setText("非共享："+is.getString("ppnsnum"));

    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
                    }

                });

        try {
            Thread.sleep(900);
            chuli();
        } catch (Exception e) {
            chuli();
            Log.e("获取出错","获取出错"+e);
        }




    }




    private void chuli() {


                ApiUtil.getApiService().EquiList( uid,"1" )
                        .compose(this.<String>compose())
                        .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                            @Override
                            public void onHandleSuccess(String s) {
                                Log.e("上传成功返回","上传成功返回"+s.toString());

                                try {
                                    ss= JSON.parseObject(s,ParkDetailModel.class);
                                }catch (Exception e){
                                    Log.e("获取信息","获取解析失败"+ss.getData().get(0).toString());
                                }

                                try {
                                    listbean= new ArrayList<>();
                                    listbean= ss.getData();

                                    Log.e("获取","获取"+listbean.size());
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ParkDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                                    try {
                                        listAdapter = new MyParkDetailAdapter(listbean,context);
//设置添加或删除item时的动画，这里使用默认动画
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
//设置适配器
                                        recyclerView.setAdapter(listAdapter);
                                    }catch (Exception e){
                                        Log.e("获取出错","获取出错"+e);
                                    }
                                }catch (Exception e){
                                    Log.e("获取信息","获取信息失败"+e);

                                }

                            }
                            @Override
                            public void onHandleError(int code, String message) {
                                super.onHandleError(code, message);
                            }

                        });






    }

    @Override
    public void onViewClick(View v) {

    }
}

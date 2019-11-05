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
import com.jtjt.qtgl.adapter.MyRecyclerVAdapter;
import com.jtjt.qtgl.adapter.MyRecyclerVListAdapter;
import com.jtjt.qtgl.bean.base.TlistBean;
import com.jtjt.qtgl.bean.base.TlistModel;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 反馈问题详情
 */
public class FeedbackdetailsActivity extends BaseActivity {

    @BindView(R.id.t_name)
    TextView t_name;
    @BindView(R.id.t_phone)
    TextView t_phone;
    @BindView(R.id.t_icon)
    TextView t_icon;
    @BindView(R.id.left)
    ImageView left;

    @BindView(R.id.t_t1)
    TextView t_t1;
    @BindView(R.id.t_t)
    TextView t_t;
    @BindView(R.id.t_cl)
    TextView t_cl;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String uid= null;

    public MyRecyclerVAdapter mAdapter;
    List<String> aa= new ArrayList<>();
    private String id,status;
    private Bundle bundle;
    List<TlistBean> listbean = new ArrayList<>();
    private TlistModel ss;
    private MyRecyclerVListAdapter listAdapter;

    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.feedbackdetails_activity);
        ButterKnife.bind(this);


    }

    @Override
    public void initView() {

     uid=getBundle().get("UID").toString();

     Log.e("获取UID","获取UID"+uid);

        FullyLinearLayoutManager mLayoutManager = new FullyLinearLayoutManager(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void initData() {
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.e("获取UID","获取UID"+uid);

        Log.e("上传到额","上传的json");
        ApiUtil.getApiService().OpDetail( uid )
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {

                    public JSONArray js=null;

                    @Override
                    public void onHandleSuccess(String s) {
                        Log.e("上传成功返回","上传成功返回"+s.toString());
//                        {"img":"[\"\\/uploads\\/20181130\\/bfe18b625369c6f120dff63467b860d1.jpg\",\"\\/uploads\\/20181130\\/7c557c5bb0ae40c980582ce95c66f3c2.png\"]","name":"国资委建材局停车场","id":333,"remarks":"测试说明失败","status":1}

                        JSONObject is= JSONObject.parseObject(s);
                        Log.e("获取信息","获取信息"+is.getString("img"));
                        id= is.getString("id");
                        status= is.getString("status");
                        t_name.setText("昵称："+is.getString("nickname"));
                        t_phone.setText("手机号："+is.getString("phone"));
                        t_icon.setText("反馈内容："+is.getString("content"));

                        try {
                            t_t1.setText("处理结果："+is.getString("result"));
                        }catch (Exception e){
                            Log.e("处理","处理"+e);
                        }


//                        String start=    AppUtil.getDateTime(Long.parseLong(is.getString("create_time")),"yyyy-MM-dd HH:mm");
//                        t_t.setText("反馈时间："+start);


                        if (status.equals("0")){
                            t_t1.setVisibility(View.GONE);
                            t_cl.setVisibility(View.VISIBLE);
                        }else {
                            t_cl.setVisibility(View.GONE);
                            t_t1.setVisibility(View.VISIBLE);
                        }
                        try {

                            try {
                                js= JSONArray.parseArray(is.getString("img"));
                                for (int i = 0;i<js.size();i++){
                                    aa.add(js.get(i).toString());
                                }

                                Log.e("获取图片","获取图片"+aa.get(0).toString());
                            }catch (Exception e){
                                Log.e("错过","错过"+e);

                            }


                   bundle= new Bundle();
                        bundle.putString("ID",id);
try {
                            Log.e("获取图片状态",aa.size()+"获取图片"+aa.get(0).toString());
    mAdapter = new MyRecyclerVAdapter(aa);
//设置添加或删除item时的动画，这里使用默认动画
    recyclerView.setItemAnimator(new DefaultItemAnimator());
//设置适配器
    recyclerView.setAdapter(mAdapter);


}catch (Exception e){
                            Log.e("获取出错","获取出错"+e);
}


                            Log.e("获取Bundle","获取bundle"+bundle.toString());
                            if (bundle!= null){
                                t_cl.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startAct(FeedActivity.class,bundle);
                                    }
                                });
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

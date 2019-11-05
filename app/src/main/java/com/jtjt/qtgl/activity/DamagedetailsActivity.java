package com.jtjt.qtgl.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.adapter.MyRecyclerVAdapter;
import com.jtjt.qtgl.adapter.MyRecyclerViewAdapter;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 上报异常详情
 */
public class DamagedetailsActivity   extends BaseActivity {

    @BindView(R.id.t_name)
    TextView t_name;
    @BindView(R.id.t_xgsb)
    TextView t_xgsb;

    @BindView(R.id.left)
    ImageView left;


    @BindView(R.id.t_icon)
    TextView t_icon;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String uid= null;

    public MyRecyclerVAdapter mAdapter;
    List<String> aa= new ArrayList<>();
    private String id,status;
    private Bundle bundle;

    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.damagedetails_activity);
        ButterKnife.bind(this);


    }

    @Override
    public void initView() {

     uid=getBundle().get("UID").toString();

     Log.e("获取UID","获取UID"+uid);

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

//        Log.e("上传到额","上传的json"+jsonArrays.toString());
        ApiUtil.getApiService().DEdit( uid )
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
                        t_name.setText("损坏设备："+is.getString("name"));
                        t_icon.setText("描述："+is.getString("remarks"));

                        if (status.equals("3")){
                            t_xgsb.setVisibility(View.GONE);
                        }else {
                            t_xgsb.setVisibility(View.VISIBLE);
                        }
                        try {

                            try {
                                js= JSONArray.parseArray(is.getString("img"));
                                for (int i = 0;i<js.size();i++){
                                    aa.add(js.get(i).toString());
                                }
                            }catch (Exception e){
                                Log.e("错过","错过"+e);

                            }




                   bundle= new Bundle();
                        bundle.putString("ID",id);
                        bundle.putString("STATUS",status);
                        bundle.putString("TNAME",is.getString("name"));
                        bundle.putString("REMARKS",is.getString("remarks"));
                        try {
                            bundle.putString("IMAGE",js.toString());
                        }catch (Exception e){
                            Log.e("获取AA","获取Aa");
                        }




try {
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
                                t_xgsb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startAct(ModifyActivity.class,bundle);
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

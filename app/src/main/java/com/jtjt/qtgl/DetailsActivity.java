package com.jtjt.qtgl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.jtjt.qtgl.bean.base.DExamModel;
import com.jtjt.qtgl.bean.base.DetailModel;
import com.jtjt.qtgl.http.ApiService;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.http.CustomPreferences;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.ComUtil;
import com.jtjt.qtgl.util.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 详情页面
 */

public class DetailsActivity  extends BaseActivity {


    @BindView(R.id.i_head)
    ImageView ihead;

    @BindView(R.id.tgrz)
    TextView tgrz;

    @BindView(R.id.jjrz)
    TextView jjrz;
    @BindView(R.id.l_zong)
    LinearLayout l_zong;

    @BindView(R.id.t_yhm)
    TextView tyhm;
    @BindView(R.id.t_zcsj)
    TextView t_zcsj;



    @BindView(R.id.t_phone)
    TextView tphone;
    @BindView(R.id.t_ye)
    TextView tye;
    @BindView(R.id.t_shsj)
    TextView tshsj;
    @BindView(R.id.t_name)
    TextView tname;
    @BindView(R.id.t_sfz)
    TextView tsfz;
    @BindView(R.id.t_cph)
    TextView tcph;
    @BindView(R.id.t_jnyj)
    TextView t_jnyj;
    @BindView(R.id.i_sfz)
    ImageView isfz;
    @BindView(R.id.i_jsz)
    ImageView i_jsz;


    private String uid;
    private DetailModel ss;
    private String sz="0";


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.details_activity);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        uid = getBundle().get("UID").toString();
        sz = getBundle().get("SZ").toString();
        Log.e("获取登录Tojel","获取登录Token"+uid);

        if (sz.equals("1")){
            l_zong.setVisibility(View.VISIBLE);
        }else{
            l_zong.setVisibility(View.GONE);
        }
        ApiUtil.getApiService().sDetail(uid+"")
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                            Log.e("订单登录信息","获取登录订单信息"+s.toString());
                        ss=JSON.parseObject(s,DetailModel.class);
                        ComUtil.displayHead(ihead, ApiService.BASE_URL_IMG + ss.getFace(),context);
                        tphone.setText("手机号："+ss.getPhone());
                        tcph.setText("车牌号"+ss.getBrand());
                        tye.setText("余额："+ss.getTotal_money());
                        tname.setText("真实姓名："+ss.getReally_name());
                        tsfz.setText("身份证号："+ss.getNum_id());


                        String start=    AppUtil.getDateTime(Long.parseLong(ss.getAdd_time()),"yyyy-MM-dd HH:mm");
                        String end=    AppUtil.getDateTime(Long.parseLong(ss.getAuthentication_time()),"yyyy-MM-dd HH:mm");
                        tshsj.setText("审核时间："+end);


                        t_zcsj.setText("注册时间："+start);
                        if (ss.getDeposit()==1) {
                            t_jnyj.setText("是否缴纳押金：已缴纳");
                        }else {
                            t_jnyj.setText("是否缴纳押金：未缴纳");
                        }
//                        Glide.with(DetailsActivity.this)
//                                .load(ApiService.BASE_URL_IMG + ss.getNum_positive_img())
//                                .into(isfz);
//                        Glide.with(DetailsActivity.this)
//                                .load(ApiService.BASE_URL_IMG + ss.getNum_side_img())
//                                .into(i_jsz);


                        ComUtil.displayImage(isfz, ApiService.BASE_URL_IMG + ss.getNum_positive_img());
                        ComUtil.displayImage(i_jsz, ApiService.BASE_URL_IMG + ss.getNum_side_img());



                    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
                    }
                });


        tgrz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shenhe(Integer.parseInt(uid),1);

            }
        });
        jjrz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shenhe(Integer.parseInt(uid),0);

            }
        });



    }

    @Override
    public void onViewClick(View v) {


    }



    private  void shenhe (int uid,int aa){
        ApiUtil.getApiService().ChangeStatus(uid,aa)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        Log.e("审核结果","审核成功"+s);
                        MyToast.show(context,s);
                            l_zong.setVisibility(View.GONE);
                    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
//                        MyToast.show(context,message+"不成功");
                    }
                });


    }

}

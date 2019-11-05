package com.jtjt.qtgl.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jtjt.qtgl.R;
import com.jtjt.qtgl.activity.FeedbackActivity;
import com.jtjt.qtgl.activity.JmActivity;
import com.jtjt.qtgl.activity.OrderListActivity;
import com.jtjt.qtgl.activity.PinkListActivity;
import com.jtjt.qtgl.activity.RWPinkListActivity;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyToast;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jtjt.qtgl.R.drawable.sharp_360_lan;


/**
 * 首页
 */
public class SYActivity   extends BaseActivity {

    @BindView(R.id.web)
    TextView web;
    @BindView(R.id.shsf)
    TextView shsf;
    @BindView(R.id.zcry)
    TextView zcry;
    @BindView(R.id.tcclb)
    TextView tcclb;
    @BindView(R.id.rwlb)
    TextView rwlb;
    @BindView(R.id.wtlb)
    TextView wtlb;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.cwdd)
    TextView cwdd;
    @BindView(R.id.wt_num)
    TextView wt_num;
    @BindView(R.id.rw_num)
    TextView rw_num;
    @BindView(R.id.tcdl)
    TextView tcdl;


    private int aa,yj,rw;


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sy_activity);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {



    }



    @Override
    public void initData() {
        shsf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("获取ID","获取用户ID"+userBean.getAid());
                    if (!AppUtil.isFastDoubleClick()){
                        // 进行点击事件后的逻辑操作
                        if ( userBean.getAid().equals("") || userBean.getAid()==null){
                            startAct(LoginActivity.class);
                        }else {
                            startAct(ExamineActivity.class);
                        }

                    }
                }catch (Exception e){
                    Log.e("获取","获取"+e);
                }
            }

        });
        zcry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("获取ID","获取用户ID"+userBean.getAid());
                    if (!AppUtil.isFastDoubleClick()){
                        // 进行点击事件后的逻辑操作
                        if ( userBean.getAid().equals("") || userBean.getAid()==null){
                            startAct(LoginActivity.class);
                        }else {
                            startAct(ZCActivity.class);
                        }

                    }
                }catch (Exception e){
                    Log.e("获取","获取"+e);
                }
            }

        });


        wtlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(FeedbackActivity.class);
            }
        });
        rwlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(RWPinkListActivity.class);
            }
        });

        tcclb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(PinkListActivity.class);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAct(JmActivity.class);
                startAct(OrderListActivity.class);
//
//                if ( userBean.getAid().equals("") || userBean.getAid()==null){
//                    startAct(LoginActivity.class);
//                }else {
//                    MyToast.show(context,"已经登录成功了");
////                    startAct(ExamineActivity.class);
//                    startAct(UplodeActivity.class);
//                }
//                startAct(WebActivity.class);
            }
        });

        cwdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAct(OrderListActivity.class);
            }
        });

        tcdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOutDialog();
            }
        });
chuli();
    }


    private void loginOutDialog() {
        final Dialog bottomDialog = new Dialog(SYActivity.this, R.style.BottomDialog);
        LayoutInflater inflater = SYActivity.this.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.layout_alert_login_out, null);
        TextView tv_msgAlertView = (TextView) layout.findViewById(R.id.tv_msgAlertView);
        layout.findViewById(R.id.tv_titleAlertView).setVisibility(View.GONE);
        tv_msgAlertView.setText("您是否要退出？");
        layout.findViewById(R.id.tv_leftAlertView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        layout.findViewById(R.id.tv_rightAlertView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                myShare.clear();
//            startAct(SYActivity.class);
                getApp().clear();
            }
        });
        bottomDialog.setContentView(layout);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layout.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        chuli();
     /*   if ( userBean.getAid().equals("") || userBean.getAid()==null){
            startAct(LoginActivity.class);

        }else {
            MyToast.show(context,"首页"+userBean.getAid());
            chuli();
        }*/

    }

    private void chuli() {

        ApiUtil.getApiService().Finance(0+"")
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        Log.e("获取信息","获取信息"+s);

                        com.alibaba.fastjson.JSONObject is= com.alibaba.fastjson.JSONObject.parseObject(s);
                        aa= Integer.parseInt(is.getString("wd_num"));
                        yj= Integer.parseInt(is.getString("opinion_num"));
                        rw= Integer.parseInt(is.getString("task_num"));
                        wt_num.setText(""+yj);
                        if (yj>0) {
                            wt_num.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MyToast.show(context, "待处理意见反馈总数为" + yj);
                                }
                            });
                        }else {
                            wt_num.setBackgroundResource(R.drawable.sharp_360_lan);
                            MyToast.show(context,"暂未有需要处理的反馈");
                        }

                        rw_num.setText(""+rw);
                        if (rw>0) {
                            rw_num.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MyToast.show(context, "待处理处理中任务总数为" + rw+"请赶紧去处理");
                                }
                            });
                        }else {
                            rw_num.setBackgroundResource(R.drawable.sharp_360_lan);
                            MyToast.show(context,"暂未有需要处理的任务");
                        }

//                        "opinion_num": 1,
//                                "task_num": 7

                        tv_num.setText(""+aa);

                        tv_num.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (aa>0){
                                    MyToast.show(context,"有"+aa+"条需要处理的订单，请联系财务人员及时处理。");
                                }else {

                                    tv_num.setBackgroundResource(R.drawable.sharp_360_lan);

                                    MyToast.show(context,"暂未有需要处理的订单");
                                }
                            }
                        });

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

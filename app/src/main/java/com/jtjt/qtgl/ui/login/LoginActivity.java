package com.jtjt.qtgl.ui.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jtjt.qtgl.MainActivity;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.OrderBean;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseEntity;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;

/**
 * Created by 董凯强 on 2018/11/13.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.login_btns)
    TextView loginBtn;

    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.login_sms)
    TextView loginSms;
    TextView txts;

    String phone="";
    String password ="";




    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);

        try {
        }catch (Exception e){
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
//                startAct(ForgetActivity.class);
                break;
            case R.id.tv_register:
//                startAct(RegisterActivity.class);
                break;
            case R.id.login_sms:
//                startAct(SMSLoginActivity.class);
                break;
            case R.id.login_btns:
                Log.e("获取电话号码",password+"获取淡化好"+phone);

                try {
                    phone =      etPhone.getText().toString().trim();
                    password = etPassword.getText().toString().trim();
                    Log.e("获取电话号码",password+"获取---淡化好"+phone);
                }catch (Exception e){
                    Log.e("获取","获取"+e);
                }


                phone = etPhone.getText().toString();
                password = etPassword.getText().toString();

                Log.e("获取电话号码",password+"获取-------------淡化好"+phone);
//
//                if (AppUtil.isEmpty(password)) {
//                    MyToast.show(context, "请输入密码！");
//                    return;
//                }

//                login("dongkaiqiang", "111111");
                login(phone, password);
                break;
        }
    }

    private void login(String username, String password) {
//    List<String> list= new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        ApiUtil.getApiService().Login(map)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context,buildProgressDialog(true),getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) throws Exception {
                        Log.e("登录成功","登录成功"+s.toString());

                        OrderBean bean = JSON.parseObject(s, OrderBean.class);
                        myShare.putString(Constant.USER_DATA, s);
                        MyToast.show(context, "登录成功！");
                        getApp().putValue("parking_pay_succ", true);
                        if (getApp().acts.size() == 1) {
                            startAct(ExamineActivity.class);
                        }
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e("登陆接口请求失败","接口请求失败"+e);
                    }
                });
    }


    @Override
    public void onLeftClick() {
        goBack();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        if (getApp().acts.size() == 1) {
            startAct(MainActivity.class);
        }
        finish();
    }

}

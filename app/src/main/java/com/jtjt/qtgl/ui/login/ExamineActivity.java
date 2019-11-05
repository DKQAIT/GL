package com.jtjt.qtgl.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.DExamBean;
import com.jtjt.qtgl.bean.base.DExamModel;
import com.jtjt.qtgl.fragment.DExamineFragment;
import com.jtjt.qtgl.fragment.ExamineFragment;
import com.jtjt.qtgl.fragment.YExamineFragment;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.http.CustomPreferences;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.ui.login.view.NavView;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyLog;
import com.jtjt.qtgl.util.MyToast;
import com.jtjt.qtgl.view.NavGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 审核页面
 */

public class ExamineActivity   extends BaseActivity implements NavGroup.OnCheckedChangeListener{

    @BindView(R.id.ordergroup)
    RadioGroup ordergroup;
    @BindView(R.id.tab1)
    RadioButton tab1;
    @BindView(R.id.tab2)
    RadioButton tab2;

    @BindView(R.id.orderVP)
    ViewPager orderVP;

    private NavGroup groupFooter;
//    private boolean isExit; // 是否退出
//    private List<BaseFragment> fragments;
    private int pager;


    List<BaseFragment> examineFragment;
    public static final int STATUE_TAB1 = 1;
    public static final int STATUE_TAB2 = 3;
    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.examine_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

        Log.e("获取登录Tojel","获取登录Token"+userBean.getLogintoken());



    }

    @Override
    public void initData() {

        if (examineFragment == null) {
            examineFragment = new ArrayList<>();
            examineFragment.add(new ExamineFragment());
        }
        setStatusBarMode(true);
    getSupportFragmentManager().beginTransaction().replace(R.id.main_flyContainer, examineFragment.get(0)).commit();


    }

    @Override
    public void onViewClick(View v) {

    }




    @Override
    public void onCheckedChanged(NavGroup group, NavView nav, @IdRes int checkedId) {
        if (nav.getTag() != null) {
            int position = Integer.parseInt(nav.getTag().toString());
            if (position == 0) {
                   setStatusBarMode(true);
            } else if (AppUtil.isEmpty(myShare.getString(Constant.USER_DATA))){
                startAct(LoginActivity.class);
                return;
            }
            changePager(position);
        }
    }

    private void changePager(int position) {
        if (position == pager)
            return;
        BaseFragment from = examineFragment.get(pager);
        BaseFragment to = examineFragment.get(position);
        pager = position;
        switchFragment(from, to);
    }

    /**
     * 切换页面的重载，优化了fragment的切换
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (from == null || to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            transaction.hide(from).add(R.id.main_flyContainer, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
            try {
                BaseFragment bf = (BaseFragment) to;
                bf.onStartData();
            } catch (Exception e) {
                MyLog.e("Fragment not extends BaseFragment!");
            }
        }
    }



    @Override
    protected void onDestroy() {
        MyLog.e(getClass(), "主页挂掉");
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}

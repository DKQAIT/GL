package com.jtjt.qtgl.ui.login.by;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wjw on 2017/2/28.
 */

public interface IBaseFragment {

    /**
     * 初始化布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    View onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化数据
     */
    void onStartData();


    /**
     * 控件点击事件
     */
    void onViewClick(View v);

    /**
     * 头-左边图标点击
     */
    void onLeftClick();

    /**
     * 头-右边图标点击
     */
    void onRightClick(View v);
}

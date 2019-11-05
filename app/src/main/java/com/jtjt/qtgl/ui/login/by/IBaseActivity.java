package com.jtjt.qtgl.ui.login.by;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by wjw on 2017/2/28.
 */

public interface IBaseActivity {

    /**
     * @param savedInstanceState
     */
    void  afterCreate(Bundle savedInstanceState,Intent intent);

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

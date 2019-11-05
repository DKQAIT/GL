package com.jtjt.qtgl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.FlowBean;
import com.jtjt.qtgl.bean.base.FlowModel;
import com.jtjt.qtgl.bean.base.ListBaseAdapter;
import com.jtjt.qtgl.bean.base.SuperViewHolder;
import com.jtjt.qtgl.bean.base.UserListInfo;
import com.jtjt.qtgl.bean.base.UserListModel;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends BaseActivity {
    /**
     * 订单类型列表
     *
     */


    @BindView(R.id.r_list)
    LRecyclerView rList;
    @BindView(R.id.ll_default)
    LinearLayout llDefault;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.left)
    ImageView left;


    List<FlowBean> list;
    ListBaseAdapter adapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int start = 1;
    private FlowModel ss;
    private int uid;
    private String keywords="";


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.orderlist_activity);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {

        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    keywords=  et_search.getText().toString().trim();
                    adapter.clear();
                    initData();

                }
                return false;
            }
        });

//        // 搜索框的文本变化实时监听
//        et_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().trim().length() == 0) {
//                    initData();
//                } else {
//                  keywords=  et_search.getText().toString().trim();
//                    adapter.clear();
//                    initData();
//                }
//
//            }
//        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.e("获取跳转Activity","获取跳转Activity");
        rList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListBaseAdapter<FlowBean>(context) {
            @Override
            public int getLayoutId()
            {
                return R.layout.orderlist_adapter;
            }
            @Override
            public void onBindItemHolder(SuperViewHolder holder, int position) {
                final FlowBean bean = getDataList().get(position);
                LinearLayout l_zong = holder.getView(R.id.l_zong);
                TextView t_je = holder.getView(R.id.t_je);
                TextView t_phone = holder.getView(R.id.t_phone);
                TextView t_fs = holder.getView(R.id.t_fs);
                TextView t_lx = holder.getView(R.id.t_lx);
                TextView t_time = holder.getView(R.id.t_time);
                t_je.setText("金额："+bean.getMoney());
                t_phone.setText("手机号："+bean.getPhone());
//                1支付宝 2微信 3余额
                if (bean.getPay_type()==1) {
                    t_fs.setText("交易方式： 支付宝" );
                }
                else if (bean.getPay_type()==2){
                    t_fs.setText("交易方式： 微信" );
                }
                else if (bean.getPay_type()==3){
                    t_fs.setText("交易方式： 金额" );
                }

//                消费类型 1充值 2交押金 3退还押金 4短期预定 5长期预定 6长期续租

                if (bean.getType()==1) {
                    t_lx.setText("交易类型： 充值" );
                }
                else if (bean.getType()==2){
                    t_lx.setText("交易类型： 交押金" );
                }
                else if (bean.getType()==3){
                    t_lx.setText("交易类型： 退还押金" );
                }
                else if (bean.getType()==4){
                    t_lx.setText("交易类型： 短期预定" );
                }
                else if (bean.getType()==5){
                    t_lx.setText("交易类型： 长期预定" );
                }
                else if (bean.getType()==6){
                    t_lx.setText("交易类型： 长期续租" );
                }

                String start=    AppUtil.getDateTime(bean.getTime(),"yyyy-MM-dd HH:mm");
                t_time.setText("交易时间 ："+ start);
                l_zong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent( getContext(), DetailsActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("UID",bean.getUid()+"");
//                        bundle.putString("SZ","1");
//                        Log.e("跳转UID","跳转UID"+bean.getUid());
//                        startAct(DetailsActivity.class,bundle);
                    }
                });
            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rList.setAdapter(mLRecyclerViewAdapter);
        //禁止下拉刷新功能
        rList.setPullRefreshEnabled(true);
        //禁止自动加载更多功能
        rList.setLoadMoreEnabled(true);
        rList.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        rList.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        rList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置头部加载颜色
        rList.setHeaderViewColor(R.color.colorAccent, R.color.colorPrimary, android.R.color.white);
        //设置底部加载颜色
        rList.setFooterViewColor(R.color.colorAccent, R.color.colorPrimary, android.R.color.white);
        //设置底部加载文字提示
        rList.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        rList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                start++;
                initData();
            }
        });
        rList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                start = 1;
                initData();
            }
        });
    }
    @Override
    public void initData() {
        Log.e("获取登录Tojel",keywords+"获取登录Token"+userBean.getLogintoken());
        ApiUtil.getApiService().FlowIndex(keywords,start,10)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        try {
                            Log.e("订单登录信息","获取登录订单信息"+s.toString());
                            ss= JSON.parseObject(s,FlowModel.class);
                            Log.e("订单登录信息","订单类信息"+ss.getData());
                            Log.e("订单登录信息","订单类信息____"+ss.getData().toString());
                            list= ss.getData();
                            Log.e("获取储存信息","获取储存信息1-"+list.toString());

                            try {
                            }catch (Exception e){
                                Log.e("处处出错","处处出错"+e);
                            }
                            if (list.size() == 0) {
                                rList.setNoMore(true);
                                if (start == 1) {
                                    llDefault.setVisibility(View.VISIBLE);
                                    rList.setVisibility(View.GONE);
                                    MyToast.show(context, "暂无数据");

                                    llDefault.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            initData();
                                        }
                                    });
                                }
                            }else {
                                rList.setVisibility(View.VISIBLE);
                                llDefault.setVisibility(View.GONE);
                            }

                            Log.e("获取List 信息","获取List信息"+list.toString());
                            adapter.addAll(list);
                            rList.refreshComplete(list.size());
                            adapter.notifyDataSetChanged();

                        }catch (Exception e){
                        }
                    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
                        rList.refreshComplete(0);
                        llDefault.setVisibility(View.VISIBLE);
                        rList.setVisibility(View.GONE);
                    }
                });




    }

    @Override
    public void onViewClick(View v) {

    }
}

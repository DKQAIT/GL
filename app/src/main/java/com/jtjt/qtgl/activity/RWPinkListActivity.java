package com.jtjt.qtgl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
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
import com.jtjt.qtgl.bean.base.ListBaseAdapter;
import com.jtjt.qtgl.bean.base.ParkInfo;
import com.jtjt.qtgl.bean.base.ParkModel;
import com.jtjt.qtgl.bean.base.SuperViewHolder;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RWPinkListActivity extends BaseActivity {
    /**
     * 任务停车场列表
     */


    @BindView(R.id.r_list)
    LRecyclerView rList;
    @BindView(R.id.ll_default)
    LinearLayout llDefault;
    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.t_lb)
    TextView t_lb;


    List<ParkModel> list;
    ListBaseAdapter adapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int start = 1;
    private ParkInfo ss;


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.parklist_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        t_lb.setText("任务停车场列表");
        Log.e("获取跳转Activity","获取跳转Activity");
        rList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListBaseAdapter<ParkModel>(context) {
            @Override
            public int getLayoutId()
            {
                return R.layout.rwparklist_adapter;
            }
            @Override
            public void onBindItemHolder(SuperViewHolder holder, int position) {
                final ParkModel bean = getDataList().get(position);
                LinearLayout l_zong = holder.getView(R.id.l_zong);
                TextView tv_name = holder.getView(R.id.t_name);
                TextView ckyh = holder.getView(R.id.ckyh);
                TextView sbsb = holder.getView(R.id.sbsb);
                tv_name.setText(""+bean.getName());
                l_zong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent( getContext(), DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("UID",bean.getId()+"");
                        bundle.putString("UNAME",bean.getName()+"");
                        Log.e("跳转UID","跳转UID"+bean.getId());
                        startAct(TaskListActivity.class,bundle);
                    }
                });
                //              查看任务
                ckyh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent( getContext(), DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("UID",bean.getId()+"");
                        bundle.putString("UNAME",bean.getName()+"");
                        Log.e("跳转UID","跳转UID"+bean.getId());
                        startAct(TaskListActivity.class,bundle);
                    }
                });

//                添加任务
                sbsb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        Bundle bundle = new Bundle();
                        bundle.putString("UID",bean.getId()+"");
                        bundle.putString("UNAME",bean.getName()+"");
                        Log.e("跳转UID","跳转UID"+bean.getId());
                        startAct(UplodeRWActivity.class,bundle);
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

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.e("获取登录Tojel","获取登录Token"+userBean.getLogintoken());
        ApiUtil.getApiService().Parking(start,10)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        try {
                            Log.e("订单登录信息","获取登录订单信息"+s.toString());
////                            JSONObject ss = JSON.parseObject(s, DExamModel.class);
                            ss= JSON.parseObject(s,ParkInfo.class);
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

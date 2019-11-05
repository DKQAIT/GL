package com.jtjt.qtgl.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.activity.TaskListActivity;
import com.jtjt.qtgl.activity.UplodeRWActivity;
import com.jtjt.qtgl.bean.base.DExamModel;
import com.jtjt.qtgl.bean.base.MarkeInfo;
import com.jtjt.qtgl.bean.base.MarkeModel;
import com.jtjt.qtgl.bean.base.ParkInfo;
import com.jtjt.qtgl.bean.base.ParkModel;
import com.jtjt.qtgl.bean.base.SuperViewHolder;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//备注页面

public class BZActivity   extends BaseActivity {

    @BindView(R.id.r_list)
    LRecyclerView rList;
    @BindView(R.id.ll_default)
    LinearLayout llDefault;


    @BindView(R.id.et_content)
    EditText e_content;
    @BindView(R.id.tv_confirm)
    TextView t_tj;
    @BindView(R.id.left)
    ImageView left;
    private String uid;
    List<MarkeModel> list;
    ListBaseAdapter adapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int start = 1;
    private JSONArray ss;

    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bz_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        t_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                qingqiu();

            }
        });


        rList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListBaseAdapter<MarkeModel>(context) {
            @Override
            public int getLayoutId()
            {
                return R.layout.marke_adapter;
            }
            @Override
            public void onBindItemHolder(SuperViewHolder holder, int position) {
                final MarkeModel bean = getDataList().get(position);
                TextView t_content = holder.getView(R.id.t_content);
                TextView t_czy = holder.getView(R.id.t_czy);
                TextView t_time = holder.getView(R.id.t_time);
                t_content.setText("备注："+bean.getContent());


                t_czy.setText("管理员: "+bean.getUsername());
                String start=    AppUtil.getDateTime(bean.getCreate_time(),"yyyy-MM-dd HH:mm");
                t_time.setText("备注时间："+start);



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
        String  sss=e_content.getText().toString();
        uid = getBundle().get("UID").toString();

        ApiUtil.getApiService().Remark(uid)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        try {
                            Log.e("订单登录信息","获取登录订单信息"+s.toString());
////                            JSONObject ss = JSON.parseObject(s, DExamModel.class);
                            list= JSONArray.parseArray(s,MarkeModel.class);

                            Log.e("订单登录信息","订单类信息____"+list.get(0).toString());


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

    public  void  qingqiu(){


        Log.e("获取登录Tojel","获取登录Token"+userBean.getLogintoken());
        ApiUtil.getApiService().Remarkadd(uid,e_content.getText().toString())
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
MyToast.show(context,"提交备注成功");
finish();

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

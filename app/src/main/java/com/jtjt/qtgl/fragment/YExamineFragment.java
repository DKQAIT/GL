package com.jtjt.qtgl.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jtjt.qtgl.DetailsActivity;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.DExamBean;
import com.jtjt.qtgl.bean.base.DExamModel;
import com.jtjt.qtgl.bean.base.ListBaseAdapter;
import com.jtjt.qtgl.bean.base.SuperViewHolder;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.BZActivity;
import com.jtjt.qtgl.ui.login.BaseFragment;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 已审核
 */
public class YExamineFragment extends BaseFragment {
    private View view;
    private Unbinder unbinder;
    @BindView(R.id.r_list)
    LRecyclerView rList;
    @BindView(R.id.ll_default)
    LinearLayout llDefault;


    List<DExamBean> list;
    ListBaseAdapter adapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int start = 1;
    private int length = 10;
    private DExamModel ss;

//    已审核

    public static YExamineFragment newInstance() {
        YExamineFragment newsFragment = new YExamineFragment();
        return newsFragment;
    }

    @Override
    public View onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dex_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {


        rList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListBaseAdapter<DExamBean>(context) {
            @Override
            public int getLayoutId()
            {
                return R.layout.eex_adapter;
            }

            @Override
            public void onBindItemHolder(SuperViewHolder holder, int position) {
                final DExamBean bean = getDataList().get(position);
                LinearLayout l_zong = holder.getView(R.id.l_zong);
                TextView tv_name = holder.getView(R.id.t_name);
                TextView t_phone = holder.getView(R.id.t_phone);
                TextView ckxq = holder.getView(R.id.ckxq);
                TextView bz = holder.getView(R.id.bz);
                TextView t_dz = holder.getView(R.id.t_dz);
                TextView t_time = holder.getView(R.id.t_time);
                tv_name.setText("姓名："+bean.getReally_name());
                t_phone.setText("手机号："+bean.getPhone());


                t_dz.setText("地址："+bean.getAddress_lbs());
                String start=    AppUtil.getDateTime(bean.getAdd_time(),"yyyy-MM-dd HH:mm");
                t_time.setText("注册时间："+start);

                TextView t_times = holder.getView(R.id.t_times);
                String ends=    AppUtil.getDateTime(bean.getAuthentication_time(),"yyyy-MM-dd HH:mm");

                t_times.setText("审核时间："+ends);



                ckxq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("查看详情","查看详情"+bean.getUid());

                        Bundle bundle = new Bundle();
                        bundle.putString("UID",bean.getUid()+"");
                        bundle.putString("SZ",2+"");
                        Log.e("跳转UID","跳转UID"+bean.getUid());
                        startAct(DetailsActivity.class,bundle);
                    }
                });
            bz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("UID",bean.getUid()+"");
                    Log.e("跳转UID","跳转UID"+bean.getUid());
                    startAct(BZActivity.class,bundle);
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
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Log.e("获取登录Tojel","获取登录Token"+userBean.getLogintoken());
        ApiUtil.getApiService().Dexamin(userBean.getLogintoken(),1,start,10)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        try {
                            Log.e("订单登录信息","获取登录订单信息"+s.toString());
//                            JSONObject ss = JSON.parseObject(s, DExamModel.class);
                            ss=JSON.parseObject(s,DExamModel.class);

                            Log.e("订单登录信息","订单类信息"+ss.getData());

                            list= ss.getData();

//                            list= JSON.parseArray(ss.getData().toString(),DExamBean.class);
                            Log.e("获取储存信息","获取储存信息1-"+list.toString());

//                            String data = jsonObject.getString("DS");//获取DS内容  
//                            JSONArray jsonArray = JSONArray.fromObject(data);//并将DS内容取出转为json数组  
//                            for (int i = 0; i < jsonArray.size(); i++) {     //遍历json数组内容  
//    JSONObject object = jsonArray.getJSONObject(i);  
//    System.out.println(object.getString("字段名1"));  
//                            }  

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
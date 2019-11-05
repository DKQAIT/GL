package com.jtjt.qtgl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jtjt.qtgl.R;
import com.jtjt.qtgl.ui.login.BaseFragment;
import com.jtjt.qtgl.view.NavGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 问题反馈页面
 */

public class FeedbackFragment extends BaseFragment {

    @BindView(R.id.ordergroup)
    RadioGroup ordergroup;
    @BindView(R.id.tab1)
    RadioButton tab1;
    @BindView(R.id.tab2)
    RadioButton tab2;
    @BindView(R.id.tab3)
    RadioButton tab3;

    @BindView(R.id.orderVP)
    ViewPager orderVP;

    private NavGroup groupFooter;
//    private boolean isExit; // 是否退出
//    private List<BaseFragment> fragments;
    private int pager;


    List<BaseFragment> examineFragment;
    public static final int STATUE_TAB1 = 1;
    public static final int STATUE_TAB2 = 3;
    public static final int STATUE_TAB3 = 5;
    private View view;
    private Unbinder unbinder;


    @Override
    public View onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.feedback_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

        Log.e("获取登录Tojel","获取登录Token"+userBean.getLogintoken());



    }

    @Override
    public void initData() {

        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        String uid = null;
        String uname = null;
        if(bundle!=null){
            uid = bundle.getString("UID");
            uname = bundle.getString("UNAME");
        }


        Log.e("获取名字和ID",uname+"获取名字和ID"+uid);

        Bundle bundle1 = new Bundle();
        bundle1.putString("UID",uid);
        bundle1.putString("UNAME",uname);



        if (examineFragment == null) {
            examineFragment = new ArrayList<>();
//            examineFragment.add(new DExamineFragment());
//            examineFragment.add(new YExamineFragment());
            examineFragment.add(feedback1Fragment.newInstance());
            examineFragment.add(feedback2Fragment.newInstance());

        }



        examineFragment.get(0).setArguments(bundle1);
        examineFragment.get(1).setArguments(bundle1);
        orderVP.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(),
                examineFragment));
        orderVP.addOnPageChangeListener(new MyPagerChangeListener());
        orderVP.setOffscreenPageLimit(3);

        tab1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    orderVP.setCurrentItem(0);
                }
            }
        });
        tab2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    orderVP.setCurrentItem(1);

                }
            }
        });

    }

    @Override
    public void onViewClick(View v) {

    }



    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:

                    tab1.setChecked(true);
                    break;
                case 1:
                    tab2.setChecked(true);
                    break;
                case 2:
                    tab3.setChecked(true);
                    break;
//			case 3:
//				btnTab4.setChecked(true);
//				break;
            }
        }

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        List<BaseFragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
//        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
//            super(fm);
//        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments != null ? fragments.get(arg0) : null;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}

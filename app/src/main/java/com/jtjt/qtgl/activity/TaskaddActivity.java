package com.jtjt.qtgl.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.adapter.MyRecyclerViewAdapter;
import com.jtjt.qtgl.bean.base.UploderBearn;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseEntity;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.HttpUtil;
import com.jtjt.qtgl.util.MediaUtil;
import com.jtjt.qtgl.util.MyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;

/**
 * 处理任务回复
 */

public class TaskaddActivity extends BaseActivity {


    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.t_sc)
    TextView t_sc;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.t_scwj)
    TextView t_scwj;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.group)
    RadioGroup group;



    List<String> list = new ArrayList<>();
    private List<LocalMedia> selectList1 = new ArrayList<>();
    private MyRecyclerViewAdapter mAdapter;
    private UploderBearn ss;
    private String uid;
    private String uname;
    private int status;


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.taskadd_activity);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {
        uid=getBundle().get("ID").toString();
        status=Integer.parseInt(getBundle().get("STATUS").toString());

        status=2;


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                // 获取选中的RadioButton的id
                int id = group.getCheckedRadioButtonId();
                // 通过id实例化选中的这个RadioButton
                RadioButton choise = (RadioButton) findViewById(id);
                if (choise.getText().toString().equals("处理中")){
                    status=2;

                }else if (choise.getText().toString().equals("已完成")){
                    status=3;
                }else if (choise.getText().toString().equals("待处理")){
                    status=1;
                }


            }
        });


        t_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
                recycle.setVisibility(View.VISIBLE);
            }
        });
        t_scwj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                上传损坏设备
                SCZL();
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
//        /通过findViewById拿到RecyclerView实例
//设置RecyclerView管理器
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//初始化适配器


    }

    @Override
    public void onViewClick(View v) {

    }



    private void SCZL(){
//         * pid	是	int	停车场id
//        name	是	string	设备名称
//        p_type	是	int	车场类型 1包月 2短期
//        img	是	array	图片集合
//        status	是	int	状态 1：待处理 2：处理中 3：已完成
//                remarks

        String neirong= et_search.getText().toString();
        if (neirong==null||neirong.equals("")){
            MyToast.show(context,"处理任务内容不能为空");
            return;
        }
//        Map<String,List<String>> params = new HashMap<String,List<String>>();
//        params.put("img", list);
        JSONArray jsonArrays = new JSONArray();
        for (int i = 0;i<list.size();i++){
            jsonArrays.add(i,list.get(i));
        }
        ApiUtil.getApiService().CLRW( uid,jsonArrays.toString(),status,neirong )
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {

                    @Override
                    public void onHandleSuccess(String s) {
                        Log.e("上传成功返回","上传成功返回"+s.toString());
                        MyToast.show(context,"处理任务提交成功");
                        finish();
                    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
                    }

                    @Override
                    public void onNext(BaseEntity<String> value) {
                        super.onNext(value);
                        Log.e("获取Value",value.getMsg()+"获取Valuo"+value.getStatus());
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            Log.e("获取图片","获取图片内容"+PictureSelector.obtainMultipleResult(data).toString());
            Log.e("获取图片","获取图片内容的长度"+PictureSelector.obtainMultipleResult(data).size());
            String cover = PictureSelector.obtainMultipleResult(data).get((PictureSelector.obtainMultipleResult(data).size()-1)).getCutPath();
//              单张加载图片
//            selectList1.add(PictureSelector.obtainMultipleResult(data).get( (PictureSelector.obtainMultipleResult(data).size()-1)));
            for (int i= 0;i<PictureSelector.obtainMultipleResult(data).size();i++){
                selectList1.add(PictureSelector.obtainMultipleResult(data).get(i));
            }
            Log.e("获取图片长度","获取图片数量的长度"+selectList1.size());
            //                Glide.with(UplodeActivity.this)
//                        .load(cover)
//                        .into(image);
                shangchuan();

        }
    }




    private void shangchuan() {

        Log.e("上传图片路径","上传图片路径"+selectList1.get(selectList1.size()-1).getCutPath().toString());
        List<MultipartBody.Part> parts=new ArrayList<>();
        List<File> lfil= new ArrayList<>();
        for (int  i = 0;i<selectList1.size();i++) {
            lfil.add(new File(selectList1.get(i).getCutPath()));
            parts.add(HttpUtil.parsePart("file[]",new File(selectList1.get(i).getCutPath().toString())));
        }


        JSONArray jsonArrayss = new JSONArray();
        for (int i = 0;i<selectList1.size();i++){
            jsonArrayss.add(i,HttpUtil.parsePart("file[]",new File(selectList1.get(i).getCutPath().toString())));
        }





        Log.e("获取图片信息","获取图片信息"+parts.toString());
        ApiUtil.getApiService().upLoder( parts)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
//                        ss= JSON.parseObject(s,UploderBearn.class);
                        JSONArray qq= JSONArray.parseArray(s);
                        for (int i = 0;i<qq.size();i++){
                            list.add( qq.get(i).toString());
                            Log.e("获取照片信息","获取好骗信息"+qq.get(i).toString());
                        }
                        mAdapter = new MyRecyclerViewAdapter(list);
//设置添加或删除item时的动画，这里使用默认动画
                        recycle.setItemAnimator(new DefaultItemAnimator());
//设置适配器
                        recycle.setAdapter(mAdapter);
                        Log.e("获取信息","获取信息"+s);
                   /*    JSONArray qq= JSONArray.parseObject(s);
                        Log.e("上传成功返回","上传成功返回"+ss.getPath());
                        list.add( ss.getPath());
                        Log.e("获取打印图片","获取打印图片"+list.get((list.size()-1)).toString());
                        t_sc.setText("继续添加照片");

                        mAdapter = new MyRecyclerViewAdapter(list);
//设置添加或删除item时的动画，这里使用默认动画
                        recycle.setItemAnimator(new DefaultItemAnimator());
//设置适配器
                        recycle.setAdapter(mAdapter);*/

//                        ss.getPath();
                    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
                    }
                });


//        单张上传
//        ApiUtil.getApiService().upLoder(  HttpUtil.parsePart("file[]", new File(selectList1.get(selectList1.size()-1).getCutPath().toString())))
//                .compose(this.<String>compose())
//                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
//                    @Override
//                    public void onHandleSuccess(String s) {
//                        ss= JSON.parseObject(s,UploderBearn.class);
//                        Log.e("上传成功返回","上传成功返回"+ss.getPath());
//                        list.add( ss.getPath());
//                        Log.e("获取打印图片","获取打印图片"+list.get((list.size()-1)).toString());
//
//
//                        t_sc.setText("继续添加照片");
//
//                        mAdapter = new MyRecyclerViewAdapter(list);
////设置添加或删除item时的动画，这里使用默认动画
//                        recycle.setItemAnimator(new DefaultItemAnimator());
////设置适配器
//                        recycle.setAdapter(mAdapter);
//
////                        ss.getPath();
//                    }
//                    @Override
//                    public void onHandleError(int code, String message) {
//                        super.onHandleError(code, message);
//                    }
//                });


    }

    private void choosePic() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.choose_pic, null);
        layout.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                MediaUtil.openCamera(TaskaddActivity.this);
            }
        });
        layout.findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomDialog.dismiss();
                MediaUtil.openGallery(TaskaddActivity.this, 11, PictureConfig.MULTIPLE, selectList1, true);
            }
        });
        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.setContentView(layout);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layout.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

}

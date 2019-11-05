package com.jtjt.qtgl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.DeviceModel;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改上传设备
 */

public class ModifyActivity  extends BaseActivity {
    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.r_clz)
    RadioButton r_clz;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.e_iction)
    EditText e_iction;
    @BindView(R.id.t_xg)
    TextView t_xg;

    @BindView(R.id.r_ywc)
    RadioButton r_ywc;
    private String id,name,remarks,image;
    private  int status;


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.modify_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

        id=getBundle().get("ID").toString();
        name=getBundle().get("TNAME").toString();
        remarks=getBundle().get("REMARKS").toString();

        status=  Integer.parseInt(getBundle().get("STATUS").toString());

        try {
            image=getBundle().get("IMAGE").toString();
        }catch (Exception e){

        }


        Log.e("获取BUNH","获取Bundle信息"+name+remarks);

        Log.e("获取信息","获取信息Imag"+image);
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
                }


            }
        });


//        bundle.putString("ID",id);
//        bundle.putString("STATUS",status);
//        bundle.putString("TNAME",is.getString("name"));
//        bundle.putString("REMARKS",is.getString("remarks"));
//        bundle.putString("IMAGE",aa.toString());

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }



    @Override
    public void initData() {


        Log.e("获取打印的AA，",status+"获取打印的Status");
        t_xg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shangchuan();
            }
        });


    }

    private void shangchuan() {
        String aa=e_iction.getText().toString();
        Log.e("获取打印的AA，",status+"获取打印的Aa"+aa);

//        JSONArray  js= JSONArray.parseArray(image);

        ApiUtil.getApiService().UpDamage(""+id,name,image,status,remarks+"-"+aa)
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {
                    @Override
                    public void onHandleSuccess(String s) {
                        Log.e("获取信息","获取信息"+s);

                        MyToast.show(context,"修改成功");
                        finish();
                    }
                    @Override
                    public void onHandleError(int code, String message) {
                        super.onHandleError(code, message);
                        if (code==2){
                            MyToast.show(context,message);
                            finish();
                        }
                        Log.e("获取权限失败",code+"获取权限是吧"+message);
                    }
                });


    }

    @Override
    public void onViewClick(View v) {

    }
}

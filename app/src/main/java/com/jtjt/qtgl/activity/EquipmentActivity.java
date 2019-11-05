package com.jtjt.qtgl.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.http.ApiUtil;
import com.jtjt.qtgl.http.BaseObserver;
import com.jtjt.qtgl.ui.login.Constant;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备详情
 */


public class EquipmentActivity  extends BaseActivity {

    @BindView(R.id.t_name)
    TextView t_name;

    @BindView(R.id.t_gg)
    TextView t_gg;
    @BindView(R.id.t_pp)
    TextView t_pp;
    @BindView(R.id.t_lxr)
    TextView t_lxr;
    @BindView(R.id.t_phone)
    TextView t_phone;
    @BindView(R.id.t_sm)
    TextView t_sm;
    @BindView(R.id.t_jk)
    TextView t_jk;
    @BindView(R.id.t_ck)
    TextView t_ck;
    @BindView(R.id.left)
    ImageView left;



    @BindView(R.id.l_phone)
    LinearLayout l_phone;




    private String id;

    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.equipment_activity);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {

        try {
            id=  getIntent().getExtras().get("ID").toString();
            Log.e("获取对应ID","获取设备ID"+id);
        }catch (Exception e){
            Log.e("获取对应ID","获取设备ID-----"+id);
        }

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void initData() {





        ApiUtil.getApiService().EquiDetail( id )
                .compose(this.<String>compose())
                .subscribe(new BaseObserver<String>(context, buildProgressDialogs(true), getCompositeDisposable()) {

                    public JSONArray js=null;

                    @Override
                    public void onHandleSuccess(String s) {
                        Log.e("上传成功返回","上传成功返回"+s.toString());

//                        "name": "国资委建材局停车场",
//                                "month_money": 300,
//                                "ppnum": 81,
//                                "ppsnum": 61,
//                                "ppnsnum": 20

                        final JSONObject is= JSONObject.parseObject(s);
                        t_name.setText("设备名称： "+is.getString("name")+"  *"+is.getString("number"));
                        t_gg.setText("规格型号： "+is.getString("model"));
                        t_pp.setText("品牌： "+is.getString("brand"));
                        t_lxr.setText("厂家联系人： "+is.getString("contact"));
                        t_phone.setText("联系电话： "+is.getString("phone"));
                        t_sm.setText("备注说明： "+is.getString("remarks"));
                        t_jk.setText("进口是否有安装： "+is.getString("enterstatus")+"  *"+is.getString("enternum"));
                        t_ck.setText("进口是否有安装： "+is.getString("outstatus")+"  *"+is.getString("outnum"));
//                        l_phone.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//
//
//                                MyToast.show(context,"打电话"+is.getString("phone"));
//                            }
//                        });

                        l_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(EquipmentActivity.this, Manifest.permission.CALL_PHONE)) {


                                    String number = t_phone.getText().toString();
                                    //用intent启动拨打电话
                                    if (number.length() >7) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                } else {
                                    //提示用户开户权限   拍照和读写sd卡权限
                                    String[] perms = {Manifest.permission.CALL_PHONE};
                                    ActivityCompat.requestPermissions(EquipmentActivity.this, perms, Constant.REQUEST_PERMISSION_CALLPHONE);
                                }
                            }
                        });

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

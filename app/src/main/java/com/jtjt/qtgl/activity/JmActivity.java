package com.jtjt.qtgl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jtjt.qtgl.R;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.MyToast;
import com.jtjt.qtgl.util.UseRSAUtil;
import com.jtjt.qtgl.util.jm.AES256;
import com.jtjt.qtgl.util.jm.AesCbcWithIntegrity;
import com.jtjt.qtgl.util.jm.AesEncryptionUtil;
import com.jtjt.qtgl.util.jm.Base64Utils;


import butterknife.BindView;
import butterknife.ButterKnife;

public class JmActivity   extends BaseActivity {


    @BindView(R.id.t_a)
    TextView t_a;
    @BindView(R.id.t_sc)
    TextView t_sc;
    @BindView(R.id.t_my)
    TextView t_my;
    @BindView(R.id.t_jm)
    TextView t_jm;
    @BindView(R.id.t_jms)
    TextView t_jms;
    @BindView(R.id.tt)
    TextView tt;
    private String cc="g87y65ki6e8p93av8zjdrtfdrtgdwetd";

    private String a= "JCxOorCC40OBUo4MiCvAhlrkteLJcLUL";
    private String b= "FHKLR8gm455HhFVJ";


    //密钥
    byte[] bKey = new byte[]{0x30, 0x31, 0x31, 0x32, 0x67, 0x5A, 0x68, 0x47, 0x47, 0x47, 0x41, 0x35, 0x20, 0x20, 0x20, 0x20, 0x20,
            0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
    //密钥向量
    byte[] bV = new byte[]{0x31, 0x30, 0x30, 0x33, 0x63, 0x65, 0x20, 0x20, 0x20, 0x21, 0x21, 0x20, 0x20, 0x20, 0x20, 0x20};


    @Override
    public void afterCreate(Bundle savedInstanceState, Intent intent) {
        setContentView(R.layout.aa_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {




    }

    @Override
    public void initData() {

        t_sc.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                try {


                    try {
                        long time = System.currentTimeMillis();
                        //生成密钥串
//                        key = AesCbcWithIntegrity.generateKeyFromPassword("www.hao123.com", "aabbccddeeffgghh");




                    } catch (Exception e) {

                    }


                    Log.e("生成","生成公钥--");
                } catch (Exception e) {


                    Log.e("生成","生成公钥失败"+e);
                    e.printStackTrace();
                }
            }
        });


        t_jm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String source = "这是一行没有任何意义的文字123$#^&*()@163.c0m你看完了等于没看，不是吗？" +
                        "" +
                        "" +
                        "";
                try {

                    tt.setText(android.util.Base64.encodeToString(AES256.AES_cbc_encrypt(source.getBytes(),a.getBytes(), b.getBytes()),32));
                    String aa= android.util.Base64.encodeToString(AES256.AES_cbc_encrypt(source.getBytes(),a.getBytes(), b.getBytes()),32);

//                    String  aa =   AesEncryptionUtil.encrypt(source,a,b);
//  不分段加密                String  aa = UseRSAUtil.encryptPublicKey(source);
                    Log.e("打印加密密数据","打印加密密数据"+aa);
                    tt.setText(aa);
                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e("加密数据失败","加密数据失败"+e);
                }






                Log.e("加密","加密信息"+source);
//                try
//                {
//                    // 从字符串中得到公钥
//                    // PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
//                    // 从文件中得到公钥
//                    InputStream inPublic = getResources().getAssets().open("rsa_public_key.pem");
//                    PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
//                    // 加密
//                    byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);
//                    // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
//                    String afterencrypt = Base64Utils.encode(encryptByte);
//                    tt.setText(afterencrypt);
//                    Log.e("加密","加密后信息"+afterencrypt);
//
//
//                } catch (Exception e)
//                {
//                    e.printStackTrace();
//                }

            }
        });

        tt.setText(
//"NIhSeGxI5AWBDjn5P7tJsxXYhH56zWlmi73AOV3xrjovm/xqK+5nDBYGiGAGiR3Yo/Iy8JgGt9b+qtUJNnTFagTkj3/ac5DbUHvqBfx3lz8rjfe+axjxZDFdsKefNbn8o4/rFEdg7L2T7SvRj1jCujuIkrXnxRTjft+oCvyOym8JE8V7MrN3fQkWMX89I291DXnNdP7q39o39gpmhzJsowjQD8fZ1TCR4DHypCx/ybsacgZ2eFwsWQ+0xDowEeDfcslulw9ORzzg6RKv8a1XaHUnSndqnmShXxXqGMw8Rb0+ZLk9mAQu4wpnn7oB6tjs"
//   "9Gg6YkM9sozjr8/Qpn4Z3g78jMaRgp5wB+eoqSmoALYchDACQDRCEoqBwg5/l4em/hYwGr2EZz21\n" +
//           "    YCufF9IEfAVMc/9rCrMmctCibYcHYo9dTvTZi+xW9Dr+WBS3WAwwLySpTWlvBtx14o3MAwZWitDV\n" +
//           "    lUzoqUNOqu0TudDE6kPcOTtkLg6FGoeSZSDQmrJO"
//
//                "ZpvVM8m5MqQu7PwzaYhEmoxxLRqbr414egtYP1AYRbrHVU8VsEoJZhOP8OV1qwPTKbfbQLYMfwdS%0AEYJb584DQTp1bVbeUwDm4GVu45tqNKPTRIyixaq1XLnOGWMycHpqZZtuFQjciGi5033EY1Zs5GQb%0Aad%2BHiLOFzvwzVx3ZUZMsDoLkzdhYJr%2FhelKSKVKE%0A"
"9Gg6YkM9sozjr8/Qpn4Z3g78jMaRgp5wB+eoqSmoALYchDACQDRCEoqBwg5/l4em7yunzbLL0LbD\n" +
        "    HxHEJOXXiw=="
        );

        t_jms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encryptContent = tt.getText().toString().trim();
//            不分段解密     String aa=   UseRSAUtil.decrypt(encryptContent);
                String aa= null;
                try {

//                    String  cc =   AesEncryptionUtil.decrypt(encryptContent,a,b);
//                    String  cc =          AES256.  AES256Decrypt(encryptContent,a,b);



                    try {
                        byte [] bytesToDecrypt = android.util.Base64.decode(encryptContent.getBytes(),32);
                        cc=AES256.AES_cbc_decrypt(bytesToDecrypt, a.getBytes(), b.getBytes());
                        Log.e("打印解密数据","打印解密数据"+cc);
                        MyToast.show(context,"解密信息"+cc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    String  cc = new String(AES256.  AES_cbc_decrypt(encryptContent.getBytes(),bKey,bV)) ;

                } catch (Exception e) {
                    e.printStackTrace();
                }



//                    decrypt
//                     从文件中得到私钥
//                    InputStream inPrivate = getResources().getAssets().open("rsa_private_key.pem");
//                    PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
////                     因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
//                    byte[] decryptByte = RSAUtils.decryptData(org.bouncycastle.util.encoders.Base64.decode(encryptContent), privateKey);
//                    String decryptStr = new String(decryptByte);
//
//                    Log.e("解密信息","解密信息"+decryptStr);

            }
        });








    }


    /**
     * 加密
     */


    /**
     * 解密
     */


    @Override
    public void onViewClick(View v) {

    }





}

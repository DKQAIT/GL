package com.jtjt.qtgl.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.jtjt.qtgl.R;

/**
 * Web页面
 */

public class WebActivity   extends Activity {

    private ImageView left;
    private WebView web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        left=(ImageView) findViewById(R.id.lefts);
        web=(WebView) findViewById(R.id.webView);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        web.loadUrl("http://admin.qiantuebo.com/admin/root/login/login.html");
        WebSettings webSettings =  web.getSettings();
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        web.setWebViewClient(new WebViewClient());//限制在webview中打开网页，不用默认浏览器
        web.getSettings().setBuiltInZoomControls(true);//设置是否支持缩放
//        web.addJavascriptInterface(obj,str);//向html页面注入java对象
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);// 页面支持缩放：
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        web.requestFocusFromTouch(); //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
//        webSettings.setSupportZoom(true);  //支持缩放    webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片




    }


    @Override
    protected void onDestroy() {
//        web.getSettings()
        web.destroy();
        super.onDestroy();

    }



//    @Override
//    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//        result.confirm();// 不加这行代码，会造成Alert劫持：Alert只会弹出一次，并且WebView会卡死
//        return true;
//    }

}

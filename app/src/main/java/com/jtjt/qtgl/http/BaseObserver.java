package com.jtjt.qtgl.http;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.jtjt.qtgl.ui.login.LoginActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.ComUtil;
import com.jtjt.qtgl.util.MyToast;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    private Context mContext;
    private Dialog mDialog;
    private final int SUCCESS_CODE = 1;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;
    private boolean isCloseDialog;

    public BaseObserver(Context context, Dialog dialog, CompositeDisposable compositeDisposable) {
        this(context, dialog, compositeDisposable, true);
    }

    public BaseObserver(Context context, Dialog dialog, CompositeDisposable compositeDisposable, boolean isCloseDialog) {
        mContext = context;
        this.isCloseDialog = isCloseDialog;
        if (dialog == null) {
            mDialog = new ProgressDialog(context);
        } else {
            mDialog = dialog;
        }
        this.compositeDisposable = compositeDisposable;
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                removeThisDisposable();
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        compositeDisposable.add(disposable);
        if (!mDialog.isShowing() && isCloseDialog) {
            mDialog.show();
        }
    }

    @Override
    public void onNext(BaseEntity<T> value) {


        try {
            Log.e("OnNext","输出结果"+value.getData().toString()+"状态Status"+value.getStatus());
        }catch (Exception e){
            Log.e("获取出错","获取出错"+e);
        }

        if (value.getStatus() == SUCCESS_CODE) {
            T t = value.getData();
            try {
                onHandleSuccess(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (value.getStatus() == 0 && value.getMsg().equals("请先登录")) {
            logout(value.getMsg());
        } else {
            onHandleError(value.getStatus(), value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {

        Log.e("onError","输出结果"+e);
        Log.d("gesanri", "error:" + e.toString());
        removeThisDisposable();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (AppUtil.isNoEmpty(e.getMessage()) && ComUtil.isContainChinese(e.getMessage())) {
            MyToast.show(mContext, e.getMessage());
        } else {
            MyToast.show(mContext, "网络异常，请稍后再试");
        }
    }

    private void removeThisDisposable() {
       compositeDisposable.remove(disposable);
    }

    @Override
    public void onComplete() {
        Log.e("onComplete","输出结果onComplete");

        Log.d("gesanri", "onComplete");
        removeThisDisposable();
        if (mDialog != null && mDialog.isShowing() && isCloseDialog) {
            mDialog.dismiss();
        }
    }

    public abstract void onHandleSuccess(T t) throws Exception;

    public void onHandleError(int code, String message) {
        Log.e("onHandleError","输出结果"+message+"状态码"+code);

        MyToast.show(mContext, message);
    }

    private void logout(String msg) {
        Log.e("logout","输出结果"+msg);

        MyToast.show(mContext, msg);
//        JPushInterface.deleteAlias(MyApplication.getApp(), 1);
//        MyApplication.myShare.clear();
        Activity activity = MyApp.getApp().getTopActivity();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        MyApp.getApp().clear();
    }

}

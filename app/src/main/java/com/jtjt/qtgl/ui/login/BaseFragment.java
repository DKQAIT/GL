package com.jtjt.qtgl.ui.login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.OrderBean;
import com.jtjt.qtgl.http.BaseEntity;
import com.jtjt.qtgl.http.BaseModelImpl;
import com.jtjt.qtgl.http.MyApp;
import com.jtjt.qtgl.ui.login.by.IBaseFragment;
import com.jtjt.qtgl.ui.login.by.MyApplication;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.CameraUtil;
import com.jtjt.qtgl.util.ComUtil;
import com.jtjt.qtgl.util.MyLog;
import com.jtjt.qtgl.util.MyShare;
import com.jtjt.qtgl.util.MyToast;
import com.trello.rxlifecycle2.components.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 董凯强 on 2018/11/13.
 */

public abstract class BaseFragment extends com.trello.rxlifecycle2.components.support.RxFragment implements IBaseFragment,
        View.OnClickListener, CameraUtil.CameraDealListener {

    protected Activity context;
    private MyApp app;
    private boolean init;
    private Dialog progressDialog;
    //相册工具
    private CameraUtil camera;
    protected MyShare myShare;
    private CompositeDisposable compositeDisposable;
    protected OrderBean userBean;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        app = (MyApp) getActivity().getApplication();
        myShare = app.myShare;
        if (myShare.getString(Constant.USER_DATA) != null) {
            userBean = JSON.parseObject(myShare.getString(Constant.USER_DATA), OrderBean.class);
        }
        View view = onInitView(inflater, container, savedInstanceState);

        initView();
        initData();
        return view;
    }

    @Override
    public void onStartData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (userBean == null && myShare.getString(Constant.USER_DATA) != null) {
            userBean = JSON.parseObject(myShare.getString(Constant.USER_DATA), OrderBean.class);

            Log.e("获取登录的ID","获取登录的Fragment"+userBean.getAid());
        }
    }

    public MyApp getApp() {
        return app;
    }

    @Override
    public void onClick(View v) {
        if (AppUtil.isFastClick())
            return;
        if (v.getId() == R.id.head_vLeft) {
            onLeftClick();
        } else if (v.getId() == R.id.head_vRight) {
            onRightClick(v);
//        } else if (v.getId() == R.id.btn_retry) {
//            retry();
        } else {
            onViewClick(v);
        }
    }

    protected void initTopView(View view) {
        int statusBarHeight = AppUtil.getStatusBarHeight(context);
        view.setPadding(0, statusBarHeight, 0, 0);
        if (view.getLayoutParams() != null) {
            view.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.header_height) + statusBarHeight;
        }
    }

    protected void retry() {

    }


    /**
     * 头-左边图标点击
     */
    public void onLeftClick() {

    }

    /**
     * 头-右边图标点击
     */
    public void onRightClick(View v) {
    }

    /**
     * 获取 控件
     *
     * @param v  布局
     * @param id 行布局中某个组件的id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(View v, @IdRes int id) {
        return (T) v.findViewById(id);
    }

    /**
     * 获取 控件
     *
     * @param id 行布局中某个组件的id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int id) {
        return getView(getView(), id);
    }

    /**
     * 获取并绑定点击
     *
     * @param id id
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewAndClick(@IdRes int id) {
        T v = getView(id);
        v.setOnClickListener(this);
        return v;
    }

    /**
     * 获取并绑定点击
     *
     * @param id id
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewAndClick(View view, @IdRes int id) {
        T v = getView(view, id);
        v.setOnClickListener(this);
        return v;
    }

    /**
     * @see #startAct(Class, Bundle)
     */
    protected void startAct(Class<?> cls) {
        startAct(cls, null);
    }

    /**
     * @see #startAct(Intent, Class, Bundle)
     */
    protected void startAct(Class<?> cls, Bundle bundle) {
        startAct(null, cls, bundle);
    }

    /**
     * 启动Activity
     */
    protected void startAct(Intent intent, Class<?> cls, Bundle bundle) {
        if (intent == null)
            intent = new Intent();
        if (bundle != null)
            intent.putExtra(BaseActivity.BUNDLE, bundle);
        intent.putExtra(BaseActivity.LASBUNDLET_ACT, this.getClass().getSimpleName());
        intent.setClass(getActivity(), cls);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    /**
     * 显示加载框
     *
     * @param flag true为加载中 false为加载失败
     */
    public Dialog buildProgressDialog(boolean flag) {

        return buildProgressDialog(flag, "");
    }
    public Dialog buildProgressDialogs(boolean flag) {

        return buildProgressDialogs(flag, "");
    }

    public Dialog buildProgressDialog(boolean flag, String content) {
        if (progressDialog == null) {
            progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
        }

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (AppUtil.isNoEmpty(content)) {
            progressDialog.setContentView(R.layout.in_dialog);
            ((TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg)).setText(content);
        } else if (flag) {
            progressDialog.setContentView(R.layout.in_dialog);
            ((TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg)).setText("加载中...");
        } else {
//            progressDialog.setContentView(R.layout.in_dialogerror);
            ((TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg)).setText("网络出现问题！");
//            progressDialog.findViewById(R.id.btn_retry).setOnClickListener(this);
        }
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
//        progressDialog.show();
        return progressDialog;
    }
    public Dialog buildProgressDialogs(boolean flag, String content) {
        if (progressDialog == null) {
            progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
        }

        progressDialog.dismiss();
        return progressDialog;
    }

    /**
     * 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void getPicturesFromAlbum() {
        camera = new CameraUtil(this, this);
        camera.onDlgPhotoClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    /**
     * 线程调度
     */
    protected <T> ObservableTransformer<BaseEntity<T>, BaseEntity<T>> compose() {
        return new ObservableTransformer<BaseEntity<T>, BaseEntity<T>>() {
            @Override
            public ObservableSource<BaseEntity<T>> apply(Observable<BaseEntity<T>> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                // 可添加网络连接判断等
                                if (AppUtil.isNetworkAvailable(context)) {

                                } else {
                                    MyLog.e("网络连接异常，请检查网络");
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new BaseModelImpl.ErrorReturn<T>());
            }
        };
    }

    /**
     * 拍照回调
     *
     * @param uri
     */
    @Override
    public void onCameraTakeSuccess(String uri) {
    }

    /**
     * 相册选取回调
     *
     * @param uri
     */
    @Override
    public void onCameraPickSuccess(String uri) {
        if (uri == null || uri.equals("no jurisdiction")) {
            MyLog.d(getClass(), "uri == null || uri.equals(\"no jurisdiction\")");
        } else {
            Uri path = Uri.parse("file://" + uri);
            camera.cropImageUri(path, 1, 1, 640);
        }
    }

    /**
     * 剪切回调
     *
     * @param uri
     */
    @Override
    public void onCameraCutSuccess(String uri) {
        MyLog.d("uri = " + uri);
    }

    /**
     * 取消拍照或选取图册回调
     */
    @Override
    public void onCamerafail() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLog.d("requestCode：" + requestCode);
        if (camera != null) {
            camera.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_PERMISSION_STORAGE: {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    MyLog.d("获取文件写权限成功");
                    if (camera != null) {
                        camera.onDlgPhotoClick();

                    } else {
                        MyToast.show(context, "您没有文件存储权限，请去权限管理中心开启");
                    }
                }
                break;
            }

        }
    }

}

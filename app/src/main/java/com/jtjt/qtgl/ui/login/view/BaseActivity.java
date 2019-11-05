package com.jtjt.qtgl.ui.login.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.bean.base.OrderBean;
import com.jtjt.qtgl.http.BaseEntity;
import com.jtjt.qtgl.http.BaseModelImpl;
import com.jtjt.qtgl.http.MyApp;
import com.jtjt.qtgl.ui.login.Constant;
import com.jtjt.qtgl.ui.login.by.IBaseActivity;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.CameraUtil;
import com.jtjt.qtgl.util.MyLog;
import com.jtjt.qtgl.util.MyShare;
import com.jtjt.qtgl.util.MyToast;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.simple.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 董凯强 on 2017/2/28.
 */


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity,
        View.OnClickListener, CameraUtil.CameraDealListener {
    public static final String LASBUNDLET_ACT = "LAST_ACT";
    public static final String BUNDLE = "TAG_BUNDLE";
    private MyApp app;
    protected Toolbar toolbar;
    protected View v_divider;
    protected Context context = this;
    //loading所用的dialog
    private Dialog progressDialog;
    //相册工具
    protected CameraUtil camera;
    protected MyShare myShare;
    protected FrameLayout mContent;
    protected boolean keyboardFlag = false;
    private CompositeDisposable compositeDisposable;
    protected OrderBean userBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        EventBus.getDefault().register(this);
        try {
            app = (MyApp) getApplication();
        } catch (Exception e) {
            MyLog.e(getClass(), "MyApplication Exception");
            System.exit(0);
            return;
        }
        app.addActivity(this);
        myShare = app.myShare;
        if (myShare.getString(Constant.USER_DATA) != null) {
            userBean = JSON.parseObject(myShare.getString(Constant.USER_DATA), OrderBean.class);
            Log.e("获取登录的ID","获取登录的"+userBean.getAid());
        }
        afterCreate(savedInstanceState, getIntent());
//        init();
        initView();
        initData();
    }

//    public abstract void afterCreate(Bundle savedInstanceState, Intent intent);
    protected void init() {
        /*** 创建一个新的总容器 */
        LinearLayout content_chlid = new LinearLayout(this);
        content_chlid.setOrientation(LinearLayout.VERTICAL);

        View mTitleBar = LayoutInflater.from(this).inflate(R.layout.in_base, content_chlid, false);
        mContent = (FrameLayout) this.findViewById(Window.ID_ANDROID_CONTENT);
        /*** 把自定义titleBar放到容器内 */
        content_chlid.addView(mTitleBar);

        /*** 把遍历activity布局view放到容器内 */
        for (int i = 0; i < mContent.getChildCount(); i++) {
            View view = mContent.getChildAt(i);
            mContent.removeView(view);
            content_chlid.addView(view);
        }

        mContent.addView(content_chlid);

        toolbar = getView(mTitleBar, R.id.head_toolBar);
        v_divider = getView(mTitleBar, R.id.base_divider);
        toolbar.setTitle("");

        //根据版本号开启沉浸式状态栏
        if (isTranslucentStatus() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            initTopView(toolbar);
        } else if (isTranslucentStatus() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            initTopView(toolbar);
        }
        setSupportActionBar(toolbar);
    }

    protected void initTopView(View toolbar) {
        int statusBarHeight = AppUtil.getStatusBarHeight(context);
        toolbar.setPadding(0, statusBarHeight, 0, 0);
        if (toolbar.getLayoutParams() != null) {
            toolbar.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.header_height) + statusBarHeight;
        }
    }

    /**
     * 是否使用沉浸式状态栏
     *
     * @return
     */
    protected boolean isTranslucentStatus() {
        return true;
    }

    protected void hideHeader() {
        if (toolbar != null) {
            v_divider.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置系统状态栏图标和字体配色模式
     *
     * @param darkmode 是否深色模式
     * @return 成功执行返回true
     */
    public boolean setStatusBarMode(boolean darkmode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
            if (AppUtil.MIUISetStatusBarLightMode(this, darkmode) || AppUtil.FlymeSetStatusBarLightMode(this, darkmode)) {
                //设置状态栏为指定颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4

                }
                return true;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
                // 设置浅色状态栏时的界面显示
                View decor = getWindow().getDecorView();
                int ui = decor.getSystemUiVisibility();
                if (darkmode) {
                    ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decor.setSystemUiVisibility(ui);
            }
        }
        return false;
    }

    public MyApp getApp() {
        return app;
    }

    @Override
    public void onStartData() {

    }

    protected Bundle getBundle() {
        if (getIntent().hasExtra(BUNDLE)) {
            return getIntent().getBundleExtra(BUNDLE);
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userBean == null && myShare.getString(Constant.USER_DATA) != null) {
            userBean = JSON.parseObject(myShare.getString(Constant.USER_DATA), OrderBean.class);
        }
    }

    public Dialog buildProgressDialogs(boolean flag) {

        return buildProgressDialogs(flag, "");
    }
    public Dialog buildProgressDialogs(boolean flag, String content) {
        if (progressDialog == null) {
            progressDialog = new Dialog(context, R.style.progress_dialog);
        }

        progressDialog.dismiss();
        return progressDialog;
    }


    /**
     * 设置标题
     */
    public void setTitle(@StringRes int titleId) {
        setTitle(getString(titleId));

    }

    /**
     * 设置标题
     */
    public void setTitle(CharSequence title) {
        if (toolbar.findViewById(R.id.head_vTitle) == null) {
            View v = View.inflate(context, R.layout.in_head_title, null);
            Toolbar.LayoutParams tlp = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
            if (tlp != null) {
                tlp.gravity = Gravity.CENTER;
            }
            toolbar.addView(v, tlp);
        }
        TextView txtTitle = getView(toolbar, R.id.head_vTitle);
        txtTitle.setText(title);
    }

    protected void setHeaderLeftTxt(@StringRes int left) {
        if (left > 0) {
            if (toolbar.findViewById(R.id.head_vLeft) == null) {
                View v = View.inflate(context, R.layout.in_head_tleft, toolbar);
                TextView txt = getView(v, R.id.head_vLeft);
                txt.setOnClickListener(this);
                txt.setText(left);
            } else {
                TextView txt = getView(toolbar, R.id.head_vLeft);
                txt.setText(left);
            }
        }
    }

    protected void setHeaderLeft(@DrawableRes int left) {
        if (left > 0) {
            if (toolbar.findViewById(R.id.head_vLeft) == null) {
                View v = View.inflate(context, R.layout.in_head_left, toolbar);
                ImageView img = getView(v, R.id.head_vLeft);
                img.setOnClickListener(this);
                img.setImageResource(left);
            } else {
                ImageView img = getView(toolbar, R.id.head_vLeft);
                img.setImageResource(left);
            }
        }
    }

    protected void setHeaderRightTxt(@StringRes int right) {
        if (right > 0) {
            if (toolbar.findViewById(R.id.head_vRight) == null) {
                View v = View.inflate(context, R.layout.in_head_tright, toolbar);
                TextView txt = getView(v, R.id.head_vRight);
                txt.setOnClickListener(this);
                txt.setText(right);
            } else {
                TextView txt = getView(toolbar, R.id.head_vRight);
                txt.setText(right);
            }
        }
    }

    protected void setHeaderRight(@DrawableRes int right) {
        if (right > 0) {
            if (toolbar.findViewById(R.id.head_vRight) == null) {
                View v = View.inflate(context, R.layout.in_head_right, toolbar);
                ImageView img = getView(v, R.id.head_vRight);
                img.setOnClickListener(this);
                img.setImageResource(right);
            } else {
                ImageView img = getView(toolbar, R.id.head_vRight);
                img.setImageResource(right);
            }
        }
    }

    /**
     * 头部
     */
    protected View getHeader() {
        return toolbar;
    }

    protected View getHeaderTitle() {
        return toolbar.findViewById(R.id.head_vTitle);
    }

    protected View getHeaderRight() {
        return toolbar.findViewById(R.id.head_vRight);
    }

    protected View getHeaderLeft() {
        return toolbar.findViewById(R.id.head_vLeft);
    }

    @Override
    public void onClick(View v) {
        if (AppUtil.isFastClick())
            return;
        if (v.getId() == R.id.head_vLeft) {
            onLeftClick();
        } else if (v.getId() == R.id.head_vRight) {
            onRightClick(v);
        } else if (v.getId() == R.id.head_vLeft) {
            retry();
        } else {
            onViewClick(v);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载失败之后重新加载
     */
    protected void retry() {

    }


    /**
     * 头-左边图标点击
     */
    public void onLeftClick() {
        finish();
    }

    /**
     * 头-右边图标点击
     */
    public void onRightClick(View v) {

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
            intent.putExtra(BUNDLE, bundle);
        intent.putExtra(LASBUNDLET_ACT, this.getClass().getSimpleName());
        intent.setClass(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
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
        return (T) findViewById(id);
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

    protected void setOnClickListener(@IdRes int id) {
        findViewById(id).setOnClickListener(this);
    }

    protected void setOnClickListener(View v, @IdRes int id) {
        v.findViewById(id).setOnClickListener(this);
    }

    @Override
    public void finish() {
        if (app != null) {
            app.remove(this);
        }
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    /**
     * 显示加载框
     *
     * @param flag true为加载中 false为加载失败
     */
    public Dialog buildProgressDialog(boolean flag) {
        return buildProgressDialog(flag, "");
    }

    public Dialog buildProgressDialog(boolean flag, String content) {
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
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
//            ((TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg)).setText("您的网络出现了问题...");
//            progressDialog.findViewById(R.id.btn_retry).setOnClickListener(this);
        }
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    public <T> ObservableTransformer<BaseEntity<T>, BaseEntity<T>> compose() {
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
                                    MyToast.show(context, "网络连接异常，请检查网络");
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new BaseModelImpl.ErrorReturn<T>());
            }
        };
    }

    @Override
    public void onCameraTakeSuccess(String uri) {
    } //拍照回调

    @Override
    public void onCameraPickSuccess(String uri) {
    } //相册选取回调

    @Override
    public void onCameraCutSuccess(String uri) {
    } //剪切回调

    @Override
    public void onCamerafail() {

    }//取消拍照或选取图册回调

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (camera != null) {
            camera.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            case Constant.REQUEST_PERMISSION_LOCATION: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    MyLog.d("获取定位权限成功1");
//                    LocationUtil.isAllowed = true;
//                    LocationUtil.getInstance(context, this).startLocation(null, this);
//                } else {
//                    LocationUtil.isAllowed = false;
//                    MyToast.show(context, "您没有定位权限，请去权限管理中心开启");
//                }
                break;
            }
            case Constant.REQUEST_PERMISSION_CALLPHONE: {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    MyLog.d("获取电话权限成功");

                } else {
                    MyToast.show(context, "您没有拨打电话权限，请去权限管理中心开启");
                }
                break;
            }
        }

    }
}

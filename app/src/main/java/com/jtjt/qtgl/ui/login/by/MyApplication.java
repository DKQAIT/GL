package com.jtjt.qtgl.ui.login.by;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.jtjt.qtgl.ui.login.anr.Cockroach;
import com.jtjt.qtgl.ui.login.anr.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by wjw on 2017/2/28.
 */

public class MyApplication extends Application {

public  String    adres;

    /**
     * Activity栈
     */
    public List<Activity> acts;

    /**
     * 全局数据
     */
    private HashMap<String, Object> hashMap;

    /**
     * 是否是debug
     */
    public static boolean debug = false;


    /**
     * 全局单例
     */
    private static MyApplication app;


    public static String wx_app_id = "wxb6ecf539114f2160";
    private String registrationId;


    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //初始化Activity栈
        acts = new CopyOnWriteArrayList<>();
        //初始化全局数据储存器
        hashMap = new HashMap<>();
        //初始化是否是debug还是release
        debug = isApkInDebug(getApplicationContext());
        //初始化SharedPreferences

//        //初始化bugly异常上报和升级
//        Bugly.init(getApplicationContext(), "c360d21adb", false);



        /**
         * 闪退处理
         */


        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
        Cockroach.install(new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", throwable);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
//                        toast.setText(R.string.safe_mode_excep_tips);
//                        toast.show();
                        Toast.makeText(getApp(),"请及时反馈问题，谢谢",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别 log，该 throwable 可能是最开始的 bug 导致的，无需关心
            }

            @Override
            protected void onEnterSafeMode() {
            }

            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                Toast.makeText(getApp(),"请及时反馈问题，谢谢",Toast.LENGTH_LONG).show();
                Thread thread = Looper.getMainLooper().getThread();
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", e);
                //黑屏时建议直接杀死 app
                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));


            }

        });

        /**
         * 闪退处理
         */


    }

    public static MyApplication getApp() {
        if (app == null) {
        }
        return app;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 清理activity栈
     */
    public void remove(Activity activity) {
        if (acts != null && !acts.isEmpty()) {
            acts.remove(activity);
        }
    }

    /**
     * 退出除了自己以为的所有的Activity
     *
     * @param act
     */
    public void saveOneActivity(Activity act) {
        if (acts != null && !acts.isEmpty()) {
            for (Activity activity : acts) {
                if (act != activity) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 添加Activity到容器中
     */
    public void addActivity(Activity activity) {
        //防止重复添加
        remove(activity);
        acts.add(activity);
    }

    public Activity getTopActivity() {
        return acts.get(acts.size() - 1);
    }

    /**
     * 清理activity栈
     */
    public void clear() {
        if (acts != null && !acts.isEmpty()) {
            for (Activity activity : acts) {
                activity.finish();
            }
            acts.clear();
        }
    }

    /**
     * 存数据
     *
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(key, value);
    }

    /**
     * 取数据
     */
    public Object getValue(String key) {
        if (hashMap != null && hashMap.containsKey(key)) {
            return hashMap.get(key);
        }
        return null;
    }

    /**
     * 删除数据
     */
    public void removeValue(String key) {
        if (hashMap != null && hashMap.containsKey(key)) {
            hashMap.remove(key);
        }
    }

    /**
     * 判断当前应用是否是debug状态
     */

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 一次关闭多个界面
     */
    private List<Activity> act = new ArrayList<>();

    public List<Activity> getAct() {
        return act;
    }

    public void removeAct() {
        for (Activity activity : act)
            activity.finish();
    }

    public void removeThisAct(Activity activity) {
        if (act != null && !act.isEmpty()) {
            act.remove(activity);
        }
    }
}

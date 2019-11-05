package com.jtjt.qtgl.http;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jtjt.qtgl.bean.base.OrderBean;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomPreferences {


    private static final String PREFS_PATH = "apps-prefs";


    private static final String KEY_USER_INFO = "u_info";


    private static Context mContext;

    private static final ExecutorService pool = Executors
            .newSingleThreadExecutor();
    private static SharedPreferences prefs;

    private static Gson gson = new Gson();






    public static void init(Context context){
        mContext = context;
    }


    /**
     * 获取本地保存的用户信息
     * @return
     */
    public static List<OrderBean> getUInfo() {
        List<OrderBean> user = null;
        String json = getPrefs().getString(KEY_USER_INFO, null);
        try {
            user = gson.fromJson(json, new TypeToken<List<OrderBean>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }



//     * 设置本地保存的用户信息
//     * @return
    public static void setUInfo(List<OrderBean> user) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(KEY_USER_INFO, gson.toJson(user));
        submit(editor);
    }
    /**
     * 删除本地保存的用户信息
     * @return
     */
    public static void removeUserInfo() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.remove(KEY_USER_INFO);
        submit(editor);
    }

    public static void clear() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.clear();
        submit(editor);
    }

    private static SharedPreferences getPrefs() {
        if (prefs == null) {
            prefs = mContext.getSharedPreferences(PREFS_PATH, Context.MODE_PRIVATE);
        }
        return prefs;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static void submit(final SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT > 9) {
            editor.apply();
        } else {
            pool.execute(new Runnable() {

                @Override
                public void run() {
                    editor.commit();
                }
            });
        }
    }
}

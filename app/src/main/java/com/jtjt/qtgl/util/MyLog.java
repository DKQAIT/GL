package com.jtjt.qtgl.util;

import android.util.Log;

import com.jtjt.qtgl.ui.login.by.MyApplication;


/**
 * Created by 董凯强 on 2018/11/13.
 */
public class MyLog {
    private static boolean LOG = MyApplication.debug;

    public static void v(String mess) {
        if (LOG) {
            Log.v(getTag(), mess);
        }
    }

    public static void d(String mess) {
        if (LOG) {
            Log.d(getTag(), mess);
        }
    }

    public static void d(Class<?> cls, String mess) {
        if (LOG) {
            Log.d(getTag(), cls.getSimpleName() + ": " + mess);
        }
    }

    public static void d(String cls, String mess) {
        if (LOG) {
            Log.d(cls, ": " + mess);
        }
    }

    public static void i(String mess) {
        if (LOG) {
            Log.i(getTag(), mess);
        }
    }

    public static void w(String mess) {
        if (LOG) {
            Log.w(getTag(), mess);
        }
    }

    public static void e(String mess) {
        if (LOG) {
            Log.e(getTag(), mess);
        }
    }

    public static void e(Class<?> cls, String mess) {
        if (LOG) {
            Log.e(getTag(), cls.getSimpleName() + ": " + mess);
        }
    }

    private static String getTag() {
        return "wjwLog";
    }
}

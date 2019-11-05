package com.jtjt.qtgl.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 董凯强 on 2018/11/13.
 */

public class MyToast {
    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = ToastCus.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
            toast = ToastCus.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

}

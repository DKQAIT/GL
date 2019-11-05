package com.jtjt.qtgl.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jtjt.qtgl.R;


/**
 * Created by 董凯强 on 2018/11/13.
 */

public class ToastCus extends Toast {

    public ToastCus(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.setView(View.inflate(context, R.layout.custom_toast, null));
        TextView tv = (TextView)toast.getView().findViewById(R.id.custom_toast_message);
        tv.setText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    public static Toast makeText(Context context, int resId, int duration) throws Resources.NotFoundException {
        Toast toast = Toast.makeText(context, resId, duration);
        toast.setView(View.inflate(context, R.layout.custom_toast, null));
        TextView tv = (TextView)toast.getView().findViewById(R.id.custom_toast_message);
        tv.setText(context.getResources().getText(resId));
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    public static class Builder {
        private long time;
        private Context context;
        private String text;
        private int textRes, duration, gravity, xOffset, yOffset;
        private float horizontalMargin, verticalMargin;
        private View view;

        public Builder(Context context) {
            time = System.currentTimeMillis();
            this.context = context;

            this.gravity = -1;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextRes(@StringRes int res) {
            this.textRes = res;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setGravity(int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        public Builder setMargin(float horizontalMargin, float verticalMargin) {
            this.horizontalMargin = horizontalMargin;
            this.verticalMargin = verticalMargin;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public boolean isValid() {
            int valid;
            if (duration == ToastCus.LENGTH_SHORT ) {
                valid = 2000;
            } else if (duration == Toast.LENGTH_LONG) {
                valid = 3500;
            } else {
                valid = 2000;
            }
            return System.currentTimeMillis() - time < valid;
        }

        public Toast build() {
            Toast toast = ToastCus.makeText(context, text, ToastCus.LENGTH_SHORT);
            toast.setText(text);
            if (textRes != 0) toast.setText(textRes);
            toast.setDuration(duration == 0 ? ToastCus.LENGTH_SHORT : duration);
            if (gravity != -1) toast.setGravity(gravity, xOffset, yOffset);
            toast.setMargin(horizontalMargin, verticalMargin);
            if (view != null) toast.setView(view);
            return toast;
        }
    }
}

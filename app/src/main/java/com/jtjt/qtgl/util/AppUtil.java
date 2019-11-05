package com.jtjt.qtgl.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具
 *
 * Created by 董凯强 on 2018/11/13.
 */
public class AppUtil {

    /**
     * 防止用户误点操作
     */

    private static long mLastClickTime;// 用户判断多次点击的时间
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - mLastClickTime) < 600) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        if (str == null)
            return false;
        return Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$").matcher(str).matches(); // 验证手机号
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isNumber(String str) {
        if (str == null)
            return false;
        return Pattern.compile("[0-9]+").matcher(str).matches();
    }

    /**
     * 是否是英文
     */
    public static boolean isEnglish(String str) {
        if (str == null)
            return false;
        return Pattern.compile("[a-zA-Z]+").matcher(str).matches();
    }

    /**
     * 是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (str == null)
            return false;
        return Pattern.compile("^[\u4e00-\u9fa5]+$").matcher(str).matches();
    }

    /**
     * 是否是IP地址
     *
     * @param str
     * @return
     */
    public static boolean isIpAddress(String str) {
        if (str == null)
            return false;
        return Pattern.compile("(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}").matcher(str).matches();
    }


    /**
     * 车牌认证
     */

    /**
     * 验证str是否为正确的车牌号
     *
     * @param
     * @return
     */
    public static boolean isPlateNo(EditText view) {
        String no = view.getText().toString().trim();
        if (no == null || no.equals("")) {
            return false;
        }
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String[] str1 = { "京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏",
                "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝",
                "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "农", "台", "中",
                "武", "WJ", "亥", "戌", "酉", "申", "未", "午", "巳", "辰", "卯", "寅",
                "丑", "子", "葵", "壬", "辛", "庚", "己", "戊", "丁", "丙", "乙", "甲",
                "河北", "山西", "北京", "北", "南", "兰", "沈", "济", "成", "广", "海", "空",
                "军", "京V", "使" };
        if (no.equals("新车")) {
            return true;
        }
        if (no.length() == 7) {
            int h = 0;
            for (int r = 0; r < no.length(); r++) {
                if (str.indexOf(no.charAt(r)) != -1) {
                    h++;
                }
            }
            if (h == 7) {
                return true;
            }
        }
        if (no.length() > 1) {
            String jq1 = no.substring(0, 1);
            String jq2 = no.substring(0, 2);
            for (int k = 0; k < str1.length; k++) {
                if (str1[k].equals(jq1)) {
                    if (no.length() <= 10) {
                        return true;
                    }
                }
                if (str1[k].equals(jq2)) {
                    if (no.length() <= 10) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    /**
     * 是否是身份证
     *
     * @param str
     * @return
     */
    public static boolean isIdentity(String str) {
        if (isEmpty(str))
            return false;
        if (str.length() == 15)
            return Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$").matcher(str).matches();
        if (str.contains("x"))
            str = str.replaceAll("x", "X");
        if (str.length() == 18)
            return Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$").matcher(str).matches();
        return false;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        if (str == null)
            return false;
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        str = str.replaceAll("-", "");
        // p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        if (str.length() == 11) {
            p1 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$"); // 验证带区号的
            m = p1.matcher(str);
            b = m.matches();
        } else if (str.length() <= 9) {
            p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
            m = p2.matcher(str);
            b = m.matches();
        }
        if (!b)
            return isMobile(str);
        return b;
    }

    /**
     * 邮箱验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isEmail(String str) {
        if (str == null)
            return false;
        return Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
                .matcher(str).matches();
    }

    /**
     * 是否是银行卡号
     *
     * @param str
     * @return
     */
    public static boolean isBankCard(String str) {
        if (isEmpty(str))
            return false;
        Pattern pattern = Pattern.compile("^(\\d{16}|\\d{19})$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNoEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
//        if (null == str)
//            return true;
//        if (str.length() == 0)
//            return true;
//        if (str.trim().length() == 0)
//            return true;
//        if (str.indexOf("null") == 0) //包含null
//            return true;
//        return false;
        return (null == str) || (str.length() == 0) || (str.trim().length() == 0) || (str.indexOf("null") == 0);

    }

    public static boolean isEmptyNoNull(String str) {
        if (null == str)
            return true;
        if (str.length() == 0)
            return true;
        if (str.trim().length() == 0)
            return true;
//        if (str.indexOf("null") == 0) 包含null
//            return true;
        return false;
    }

    /**
     * 如果返回 "true" 字符串就是当前页面申明了函数
     *
     * @param str
     * @return true 网页没有申明返回
     * false 网页申明了返回
     */
    public static boolean isWebBack(String str) {
        if ("true".equals(str))
            return true;
        return false;
    }

    public static boolean isNoEmpty(List<?> datas) {
        return !isEmpty(datas);
    }

    public static boolean isEmpty(List<?> datas) {
        if (datas == null)
            return true;
        if (datas.size() == 0)
            return true;
        return false;
    }

    /**
     * 去掉多余的0
     *
     * @param str
     * @return
     */
    public static String removeNumberZero(String str) {
        if (isEmpty(str)) {
            return "0";
        }
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");// 去掉多余的0
            str = str.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return str;
    }

    /**
     * 转换成Money格式
     *
     * @param obj
     * @return
     */
    public static String formatToMoney(Object obj) {
        return new DecimalFormat("0.00").format(obj);
    }

    /**
     * 把字体结果dimen转化成原sp值
     *
     * @return
     */
    public static float floatToSpDimension(float value, Context context) {
        return value / context.getResources().getDisplayMetrics().scaledDensity;
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getView(View v, int resId) {
        return (T) v.findViewById(resId);
    }

    /**
     * 获取当前时间Date
     *
     * @return 现在时间(Now)
     */
    public static String getNowTime() {
        Date d = new Date(System.currentTimeMillis());
        // String type = "yyyy-MM-dd HH:mm:ss";
        String type = "HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(type, Locale.CHINA);
        return formatter.format(d);
    }

    /**
     * 获取当前时间Date
     */
    public static String getDateTime(long ltime) {
        return getDateTime(ltime, null);
    }

    /**
     * 获取当前时间Date
     */
    public static String getDateTime(long ltime, String type) {
        if ((ltime + "").length() == 10) ltime = ltime * 1000L;
        if (type == null) type = "yyyy-MM-dd HH:mm:ss";
        Date d = new Date(ltime);
        SimpleDateFormat formatter = new SimpleDateFormat(type, Locale.CHINA);
        return formatter.format(d);
    }


    public static Date stringToDateTime(String strDate) {
        return stringToDateTime(strDate, null);
    }


//    转化为时间戳
    public static Date stringToDateTime(String strDate, String type) {
        if (strDate != null) {
            if (type == null) type = "yyyy-MM-dd HH:mm:ss";
            try {

                Log.e("获取转化type","获取转化type"+type);
                SimpleDateFormat sdf = new SimpleDateFormat(type, Locale.CHINA);
                return sdf.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//指定年份
        calendar.set(Calendar.MONTH, month - 1);//指定月份 Java月份从0开始算
        int daysCountOfMonth = calendar.getActualMaximum(Calendar.DATE);//获取指定年份中指定月份有几天
//获取指定年份月份中指定某天是星期几
        calendar.set(Calendar.DAY_OF_MONTH, day);  //指定日
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

    /**
     * 指定的时间距离当前的时间月数
     */
    public static long CalculateTime(Date time) {
        long nowTime = System.currentTimeMillis();  //获取当前时间的毫秒数
        long msg = 0;

        long reset = time.getTime();   //获取指定时间的毫秒数
        long dateDiff = reset - nowTime;
        long dateTemp1 = dateDiff / 1000; //秒
        long dateTemp2 = dateTemp1 / 60; //分钟
        long dateTemp3 = dateTemp2 / 60; //小时
        long dateTemp4 = dateTemp3 / 24; //天数
        long dateTemp5 = dateTemp4 / 30; //月数
        if (dateTemp5 > 0) {
            msg = dateTemp5;

        }
        return msg;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        // int height = view.getMeasuredHeight();
        // if(0 < height){
        // return height;
        // }
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
        // int width = view.getMeasuredWidth();
        // if(0 < width){
        // return width;
        // }
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 测量控件的尺寸
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {
        // int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        // int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        // view.measure(width,height);

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }

    /**
     * 返回当前程序版本信息
     */
    public static PackageInfo getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            MyLog.e("VersionInfo|Exception:" + e);
        }
        return null;
    }

    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkPackage(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();
    }

    private static long lastTime = 0;

    /**
     * 是否是快速点击
     *
     * @return
     */
    public static boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime < 500)
            return true;
        lastTime = curTime;
        return false;
    }

    /**
     * 是否是车牌号
     *
     * @param str
     * @return
     */
    public static boolean isCarNumber(String str) {
        if (isNoEmpty(str))
            return Pattern.compile("^[\u4e00-\u9fa5|A-Z]{1}[A-Z]{1}[A-Z_0-9]{5}$").matcher(str).matches();
        return false;
    }

    public static final String[] weeks = {};

    /**
     * 获取周几
     *
     * @param week
     * @return
     */
    public static String getWeekName(int week) {
        switch (week) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
        }
        return null;
    }

    /**
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    private static String getCachePath(Context context, String dir) {
        File f = context.getExternalFilesDir(dir);
        if (f == null) {
            f = context.getCacheDir();
            if (f == null) return null;
            String path = f.getAbsolutePath() + File.separator + dir;
            File file = new File(path);
            // 判断文件夹存在与否，否则创建
            if (!file.exists() && file.mkdirs()) {
                return file.getAbsolutePath();
            }
            return f.getAbsolutePath();
        } else {
            return f.getAbsolutePath();
        }
    }

    /**
     * 获取相册缓存路径
     */
    public static String getCachePathWeb(Context context) {
        return getCachePath(context, "Web");
    }

    /**
     * 获取下载缓存路径
     */
    public static String getCachePathDownload(Context context) {
        return getCachePath(context, "Download");
    }

    /**
     * 获取相册缓存路径
     */
    public static String getCachePathAlbum(Context context) {
//        return getCachePath(context, "Album");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "PlusIm");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取剪切缓存路径
     */
    public static String getCachePathCrop(Context context) {
        return getCachePath(context, "CropFile");
    }

    /**
     * 获取音频缓存路径
     */
    public static String getCachePathAudio(Context context) {
        return getCachePath(context, "AudioFile");
    }

    /**
     * 获取音频缓存路径
     */
    public static String getCachePathAudioC(Context context) {
        return getCachePath(context, "AudioFileC");
    }

    /**
     * 获取图片缓存路径
     */
    public static String getCachePathImage(Context context) {
        return getCachePath(context, "Image");
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    public static String getCacheSize(Context context) {
        // 取得sdcard文件路径
        String path = getCachePath(context, null); // "mnt/sdcard"
        if (path == null) {
            return "0M";
        }
        return FileUtil.getAutoFileOrFilesSize(path);
    }

    public static void clearCache(Context context) {
        FileUtil.delAllFile(getCachePath(context, null));
    }

    //百度转高德（百度坐标bd09ll–>火星坐标gcj02ll）
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    //高德转百度（火星坐标gcj02ll–>百度坐标bd09ll）
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

package com.jtjt.qtgl.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jtjt.qtgl.R;
import com.jtjt.qtgl.http.ApiService;
import com.jtjt.qtgl.ui.login.BaseFragment;
import com.jtjt.qtgl.ui.login.Constant;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.view.GlideCircleTransform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类
 * author wjw
 * time 2015/12/4 11:42
 */
public class ComUtil {

    public static String getString(Context context, int resId) {
        return context.getString(resId);
    }


    /**
     * 获取缓存路径
     */
//    public static String getCachePath() {
//        // 判断sd卡
//        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            return null;
//        }
//        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + FILE_CACHE;
//        File file = new File(path);
//        // 判断文件夹存在与否，否则创建
//        if (!file.exists())
//            file.mkdirs();
//        return path;
//    }


    /**
     * 检测手机号
     *
     * @param phone
     * @return
     */
//    public static boolean checkPhone(Context context, String phone) {
//        if (TextUtils.isEmpty(phone))
//            MyToast.show(context, getString(context, R.string.phone_hint));
//        else if (HyUtil.isMobile(phone))
//            return true;
//        else
//            MyToast.show(context, getString(context, R.string.phone_format_hint));
//        return false;
//    }

    /**
     * 检测用户账号
     *
     * @return
     */
//    public static boolean checkUserName(Context context, String phone) {
//        if (TextUtils.isEmpty(phone))
//            MyToast.show(context, "账号不能为空");
//        else if (HyUtil.isMobile(phone) || HyUtil.isEmail(phone))
//            return true;
//        else
//            MyToast.show(context, "请输入正确的手机号码或邮箱");
//        return false;
//    }

//    /**
//     * 检测用户密码
//     *
//     * @param pwd
//     * @return
//     */
//    public static boolean checkUserPwd(Context context, String pwd) {
//        if (TextUtils.isEmpty(pwd))
//            MyToast.show(context, "密码不能为空");
//        else if (pwd.length() >= 6 && pwd.length() <= 16)
//            return true;
//        else
//            MyToast.show(context, "密码为6-16位字母或数字");
//        return false;
//    }

//    /**
//     * float转money格式，向下取整
//     *
//     * @param fl
//     * @return
//     */
//    public static String floatToMoney(double fl) {
//        DecimalFormat format = new DecimalFormat();
//        format.setMinimumFractionDigits(2);
//        format.setMaximumFractionDigits(2);
//        format.setRoundingMode(RoundingMode.DOWN);
//        return format.format(fl);
//    }

    public static void displayImage(ImageView imageView, String url) {
        display(imageView, url, R.mipmap.default_1);
    }

    public static void display(ImageView imageView, String url, final int fail) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(fail);
            return;
        }
        if (!url.startsWith("http")) {
            url = ApiService.BASE_URL_IMG + url;
        }
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .placeholder(fail);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void displayHead(ImageView imageView, String url,Context context) {
        displayHead(imageView, url, R.mipmap.default_head,context);
    }

    public static void displayHead(ImageView imageView, String url, int fail,Context context) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .placeholder(fail)
                .transform(new GlideCircleTransform(context));
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);


    }


    public static boolean requestCameraPermission(Activity act) {
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return true;
        ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constant.REQUEST_PERMISSION_CAMERA);
        return false;
    }

    public static boolean requesStoragetPermission(Activity act) {
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return true;
        ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constant.REQUEST_PERMISSION_STORAGE);
        return false;
    }

    public static boolean requesLocatioPermission(Activity act) {
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;
        ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constant.REQUEST_PERMISSION_LOCATION);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean requesLocatioPermission(BaseFragment frg) {
        if (ContextCompat.checkSelfPermission(frg.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;
        frg.requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constant.REQUEST_PERMISSION_LOCATION);
        return false;
    }

    public static boolean requesCallPhonePermission(Activity act) {
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            return true;
        ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.CALL_PHONE},
                Constant.REQUEST_PERMISSION_CALLPHONE);
        return false;
    }

//    public static String getLocationError(int errorCode) {
//        switch (errorCode) {
//            case AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION:
//                return "您没有定位权限，请去权限管理中心开启";
//            case AMapLocation.LOCATION_SUCCESS:
//                return "定位成功";
//        }
//        return "定位失败";
//    }


    /**
     * 隐藏系统键盘
     *
     * @param act
     * @param ed
     */
    public static void hideSoftInputMethod(BaseActivity act, EditText ed) {
        act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDateTime() {
        String type = "MM-dd HH:mm:ss";
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(type, Locale.CHINA);
        return formatter.format(d);
    }


    /**
     * 压缩图片
     *
     * @param uri
     */
    public static void startCompress(String uri, int quality) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(uri);
            //创建FileOutputStream对象
            FileOutputStream fos = new FileOutputStream(uri);
            //开始压缩图片
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)) {
                fos.flush();
                //关闭流对象
                fos.close();
                MyLog.e("压缩成功: " + uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩
     *
     * @param uri
     * @param maxkb
     * @return
     */
    public static Bitmap compressBitmap(String uri, int maxkb) {
        Bitmap image = BitmapFactory.decodeFile(uri);
        //L.showlog(压缩图片);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于(maxkb)50kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > maxkb) {
            // 重置baos即清空baos
            baos.reset();
            if (options - 10 > 0) {
                // 每次都减少10
                options -= 10;
            }
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 插入Android 系统图库
     *
     * @param context
     * @param path
     */

    public static void insertAlbum(Context context, String path) {
        // 其次把文件插入到系统图库
        try {
            File file = new File(path);
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, file.getName(), "Images from the " + context.getString(R.string.app_name));
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            MyToast.show(context, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e("FileNotFoundException");
        }
    }

    /**
     * 生成二维码Bitmap
     *
     * @param str    内容
     * @param width  图片宽度
     * @param height 图片高度
     * @return bitmap图片
     */
   /* public static Bitmap createImage(String str, int width, int height) {
        if (AppUtil.isEmpty(str))
            return null;
        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, width, height, hints);
            return toBitmap(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
*/

    /**
     * 根据bitMatrix转换为bitmap
     *
     * @param bitMatrix
     * @return
     */
    /*public static Bitmap toBitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
*/
    /**
     * 根据bitmap把图片转换为base64格式
     *
     * @param bitmap
     * @return 返回的图片为png格式的base64
     * @throws IOException
     */
    public static String convert2Byte(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream input = new ByteArrayInputStream(baos.toByteArray());
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = input.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        swapStream.flush();
        swapStream.close();
        input.close();
//        return "data:image/png;base64," + new String(Base64.encode(in2b, 1));
        return new String(Base64.encode(in2b, 1));
    }
    /**
     * 判断是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
package com.jtjt.qtgl.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.jtjt.qtgl.ui.login.BaseFragment;
import com.jtjt.qtgl.ui.login.Constant;
import com.jtjt.qtgl.ui.login.camera.CropActivity;
import com.jtjt.qtgl.ui.login.view.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 拍照工具类
 Created by 董凯强 on 2018/11/13.
 */
public class CameraUtil {
    /**
     * 拍照
     */
    public static final int FLAG_UPLOAD_TAKE_PICTURE = 10;
    /**
     * 选择图片
     */
    public static final int FLAG_UPLOAD_CHOOICE_IMAGE = 12;
    /**
     * 剪切
     */
    public static final int FLAG_UPLOAD_IMAGE_CUT = 13;
    private BaseActivity act;
    private BaseFragment fragment;
    private CameraDealListener listener;

    public CameraUtil(BaseActivity act, CameraDealListener listener) {
        this.act = act;
        this.listener = listener;
    }

    public CameraUtil(BaseFragment fragment, CameraDealListener listener) {
        this.fragment = fragment;
        this.listener = listener;
    }

    //private Uri imageUri, cacheUri;
    private static final String URI_IMAGE = "CAMERA_URI_IMAGE", URI_CACHE = "CAMERA_URI_CACHE";// URI_CONTENT = "CAMERA_URI_CONTENT";


    @SuppressLint("NewApi")
    private Context getContext() {
        if (act == null) return fragment.getContext();
        return act;
    }

    private boolean initPhotoData() {
        String path = AppUtil.getCachePathCrop(getContext());
        // 判断sd卡
        if (path == null) {
            MyToast.show(getContext(), "没有SD卡，不能拍照");
            return false;
        }

        // FileUtil.delAllFile(path);
        long time = System.currentTimeMillis();
        String imagePath = path + File.separator + "pic" + time + ".jpg";
        //String cachePath = path + File.separator + "cache" + time + ".jpg";
        //MyShare.get(getContext()).putString(URI_CACHE, "file://" + cachePath);
//        MyShare.get(getContext()).putString(URI_IMAGE, "file://" + imagePath);
        return true;
    }
    private boolean initPhotoData2() {
        String path = AppUtil.getCachePathCrop(getContext());
        // 判断sd卡
        if (path == null) {
            MyLog.d(getClass(), "没有SD卡，不能拍照");
            return false;
        }
        if (!requesStoragetPermission()) {
            listener.onCameraTakeSuccess("no jurisdiction");
            return false;
        }
        // FileUtil.delAllFile(path);
        long time = System.currentTimeMillis();
        String imagePath = path + File.separator + "pic" + time + ".jpg";
        //String cachePath = path + File.separator + "cache" + time + ".jpg";
        //MyShare.get(getContext()).putString(URI_CACHE, "file://" + cachePath);
//        MyShare.get(getContext()).putString(URI_IMAGE, "file://" + imagePath);
        return true;
    }

    public void onDlgCameraClick() {
        if (initPhotoData()) {
            if (!requestCameraPermission()) {
                listener.onCameraPickSuccess("no jurisdiction");
                return;
            }
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues values = new ContentValues();
                Uri contentUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (contentUri == null) return;
//                MyShare.get(getContext()).putString(URI_CACHE, contentUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                startActivityForResult(intent, FLAG_UPLOAD_TAKE_PICTURE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDlgPhotoClick() {
        if (initPhotoData2()) {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, FLAG_UPLOAD_CHOOICE_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cropImageUri(int aspectX, int aspectY, int unit) {
        cropImageUri(getCacheUri(), aspectX, aspectY, unit);
    }

    //地址 比例
    public void cropImageUri(Uri uri, int aspectX, int aspectY, int unit) {
        Uri imageUri = getImageUri();
        if (uri == null || imageUri == null) {
            MyLog.e(getClass(), "地址未初始化");
            return;
        }
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("scaleUpIfNeeded", true);//黑边
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", aspectX * unit);
//        intent.putExtra("outputY", aspectX * unit);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
//        startActivityForResult(intent, Constant.FLAG_UPLOAD_IMAGE_CUT);

//        UCrop.Options options = new UCrop.Options();
//        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//        options.setCompressionQuality(70);//质量
//        options.setStatusBarColor(Color.parseColor("#D81B60"));
//        options.setToolbarColor(Color.parseColor("#E91E63"));
//        UCrop uCrop = UCrop.of(uri, imageUri);
//        if (aspectX > 0 && aspectY > 0) {
//            //uCrop = uCrop.withAspectRatio(aspectX, aspectY);
//            //uCrop = uCrop.withAspectRatio(aspectX, aspectY);
//        } else {
//            uCrop = uCrop.useSourceImageAspectRatio();
//        }
//        uCrop.withOptions(options);
//        uCrop.start(act == null ? fragment.getActivity() : act);

        Intent intent = new Intent(getContext(), CropActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(CropActivity.EXTRA_ASPECT_X, aspectX);
        bundle.putInt(CropActivity.EXTRA_ASPECT_Y, aspectY);
        bundle.putInt(CropActivity.EXTRA_ASPECT_UNIT, unit);
        bundle.putParcelable(CropActivity.EXTRA_INPUT_URI, uri);
        bundle.putParcelable(CropActivity.EXTRA_OUTPUT_URI, imageUri);
        intent.putExtra(BaseActivity.BUNDLE, bundle);
        try {
            startActivityForResult(intent, FLAG_UPLOAD_IMAGE_CUT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startActivityForResult(Intent intent, int requestCode) {
        if (act == null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            act.startActivityForResult(intent, requestCode);
        }
    }

    public Uri getCacheUri() {
//        String path = MyShare.get(getContext()).getString(URI_CACHE);
//        return path == null ? null : Uri.parse(path);
        return  null;
    }

    public Uri getImageUri() {
//        String path = MyShare.get(getContext()).getString(URI_IMAGE);
//        return path == null ? null : Uri.parse(path);
        return  null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            File f;
            Uri cacheUri;
            String path;
            try {
                switch (requestCode) {
                    case FLAG_UPLOAD_TAKE_PICTURE:
                        if (data != null && data.getData() != null) {
                            path = CameraDocument.getPath(getContext(), data.getData());
                        } else {
                            path = CameraDocument.getPath(getContext(), getCacheUri());
                        }
                        f = new File(path);
                        if (f.exists() && f.length() > 0) {
                            if (listener != null)
                                listener.onCameraTakeSuccess(path);
                        } else {
                            if (listener != null)
                                listener.onCameraTakeSuccess(null);
                            MyLog.e(getClass(), "拍照存储异常");
                        }
                        break;
                    case FLAG_UPLOAD_CHOOICE_IMAGE:
                        if (data != null && data.getData() != null) {
                            path = CameraDocument.getPath(getContext(), data.getData());
                            f = new File(path);
                            if (f.exists() && f.length() > 0) {
                                if (listener != null)
                                    listener.onCameraPickSuccess(path);
                            } else {
                                MyLog.e(getClass(), "选择的图片不存在");
                                if (listener != null)
                                    listener.onCameraPickSuccess(null);
                            }
                        }
                        break;
                    case FLAG_UPLOAD_IMAGE_CUT:
                        Uri imageUri = getImageUri();
                        f = new File(imageUri.getPath());
                        if (f.exists() && f.length() > 0) {
                            if (listener != null)
                                listener.onCameraCutSuccess(imageUri.getPath());
                        } else if (data != null && data.getData() != null) {
                            MyLog.e(getClass(), "剪切其他情况");
                            path = CameraDocument.getPath(getContext(), data.getData());
                            if (listener != null)
                                listener.onCameraCutSuccess(path);
                        } else {
                            MyLog.e(getClass(), "剪切未知情况");
                            if (listener != null)
                                listener.onCameraCutSuccess(null);
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            listener.onCamerafail();
        }
    }

    private boolean requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return true;
        Activity act = null;
        if (this.act != null)
            act = this.act;
        if (this.fragment != null)
            act = this.fragment.getActivity();
        ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constant.REQUEST_PERMISSION_CAMERA);
        return false;
    }

    private boolean requesStoragetPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return true;
        Activity act = null;
        if (this.act != null)
            act = this.act;
        if (this.fragment != null)
            act = this.fragment.getActivity();
        ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constant.REQUEST_PERMISSION_STORAGE);
        return false;
    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case Constant.REQUEST_PERMISSION_CAMERA: {
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    MyToast.show(getContext(), "获取权限成功，请重新拍照或选择图片");
//                    onDlgCameraClick();
//                } else {
//                    MyToast.show(getContext(), "您没有摄像头权限，请去权限管理中心开启");
//                }
//            }
//            break;
//            case Constant.REQUEST_PERMISSION_STORAGE: {
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    MyToast.show(getContext(), "获取权限成功，请重新拍照或选择图片");
//                    onDlgPhotoClick();
//                } else {
//                    MyToast.show(getContext(), "您没有文件存储权限，请去权限管理中心开启");
//                }
//            }
//            break;
//        }
//    }

    private boolean saveFile(Uri uri, File f) {
        ContentResolver resolver = getContext().getContentResolver();
        try {
            if (!f.exists())
                f.createNewFile();
            Bitmap bmp;
            String path = CameraDocument.getPath(getContext(), uri);
            bmp = BitmapFactory.decodeFile(path);
            if (bmp == null || bmp.getWidth() < 1) {
                return false;
            }
            FileOutputStream fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (Exception e) {
            MyLog.e(getClass(), "saveFile Error");
        }
        return false;
    }

    public interface CameraDealListener {
        void onCameraTakeSuccess(String uri);

        void onCameraPickSuccess(String uri);

        void onCameraCutSuccess(String uri);

        void onCamerafail();
    }
}
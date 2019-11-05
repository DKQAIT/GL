package com.jtjt.qtgl.ui.login.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.jtjt.qtgl.R;
import com.jtjt.qtgl.ui.login.by.MyApplication;
import com.jtjt.qtgl.ui.login.camera.utils.BitmapLoadUtils;
import com.jtjt.qtgl.ui.login.camera.view.CropImageView;
import com.jtjt.qtgl.ui.login.camera.view.GestureCropImageView;
import com.jtjt.qtgl.ui.login.camera.view.OverlayView;
import com.jtjt.qtgl.ui.login.camera.view.TransformImageView;
import com.jtjt.qtgl.ui.login.camera.view.UCropView;
import com.jtjt.qtgl.ui.login.view.BaseActivity;
import com.jtjt.qtgl.ui.login.view.TintImageView;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyLog;

import java.io.OutputStream;

/**
 * com.hy.frame.ui
 * Created by 董凯强 on 2018/11/13.
 */
public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int DEFAULT_COMPRESS_QUALITY = 90;
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    public static final String EXTRA_INPUT_URI = "INPUT_URI";
    public static final String EXTRA_OUTPUT_URI = "OUTPUT_URI";
    public static final String EXTRA_ASPECT_X = "ASPECT_X";
    public static final String EXTRA_ASPECT_Y = "ASPECT_Y";
    public static final String EXTRA_ASPECT_UNIT = "ASPECT_UNIT";
    public static final String EXTRA_QUALITY = "QUALITY";
    private GestureCropImageView imgCrop;
    private OverlayView vOverlay;
    private Uri outputUri;
    private int quality;

    private Toolbar toolbar;
    private FrameLayout flyMain;
    private MyApplication app;
//    protected MyShare myShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initData();
    }

    public void init() {
        int layout = initLayoutId();
        if (layout == 0) {
            MyLog.e(getClass(), "initLayoutId not call");
            return;
        }
        setContentView(layout);

        try {
            app = (MyApplication) getApplication();
        } catch (Exception e) {
            MyLog.e(getClass(), "MyApplication Exception");
            System.exit(0);
            return;
        }
        app.addActivity(this);
//        myShare = app.myShare;

        toolbar = (Toolbar) findViewById(R.id.head_toolBar);
        //没有标题，使用默认Layout
        if (toolbar == null) {
            setContentView(R.layout.act_base);
            toolbar = (Toolbar) findViewById(R.id.head_toolBar);
        }
        toolbar.setTitle("");
        //根据版本号开启沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            if ( statusBarHeight > 0) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                toolbar.setPadding(0, statusBarHeight, 0, 0);
                if (toolbar.getLayoutParams() != null) {
                    toolbar.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.header_height) + statusBarHeight;
                }
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        setSupportActionBar(toolbar);
        flyMain = (FrameLayout) findViewById(R.id.base_flyMain);
        //如果使用的默认Layout，则把当前Layout inflate到默认Layout中
        if (flyMain != null) {
            View.inflate(this, layout, flyMain);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int initLayoutId() {
        return R.layout.act_crop;
    }


    public void initView() {
        UCropView vUCrop = (UCropView) findViewById(R.id.crop_vUCrop);
        imgCrop = vUCrop.getCropImageView();
        vOverlay = vUCrop.getOverlayView();
    }

    public void initData() {
        setHeaderLeft(R.mipmap.ic_back);
        setTitle(R.string.crop);
        setHeaderRight(R.mipmap.ico_confirm);
        TextView txtTitle = (TextView) getHeaderTitle();
        int color = txtTitle.getCurrentTextColor();
        if (getHeaderLeft() instanceof TintImageView) {
            TintImageView imgLeft = (TintImageView) getHeaderLeft();
            imgLeft.setColorFilter(color);
        }
        if (getHeaderRight() instanceof TintImageView) {
            TintImageView imgRight = (TintImageView) getHeaderRight();
            imgRight.setColorFilter(color);
        }
        imgCrop.setTransformImageListener(new TransformImageView.TransformImageListener() {

            @Override
            public void onRotate(float currentAngle) {
                setAngleText(currentAngle);
            }

            @Override
            public void onScale(float currentScale) {
                setScaleText(currentScale);
            }
        });
        initImageData();
    }

    protected View getHeaderTitle() {
        return toolbar.findViewById(R.id.head_vTitle);
    }
    protected View getHeaderLeft() {
        return toolbar.findViewById(R.id.head_vLeft);
    }
    protected View getHeaderRight() {
        return toolbar.findViewById(R.id.head_vRight);
    }

    @SuppressLint("ResourceType")
    protected void setHeaderLeft(@DrawableRes int left) {
        if (left > 0) {
            if (toolbar.findViewById(R.id.head_vLeft) == null) {
                View v = View.inflate(this, R.layout.in_head_left, toolbar);
                ImageView img = (ImageView) v.findViewById(R.id.head_vLeft);
                img.setOnClickListener(this);
                img.setImageResource(left);
            } else {
                ImageView img = (ImageView) toolbar.findViewById(R.id.head_vLeft);
                img.setImageResource(left);
            }
        }
    }

    @SuppressLint("ResourceType")
    protected void setHeaderRight(@DrawableRes int right) {
        if (right > 0) {
            if (toolbar.findViewById(R.id.head_vRight) == null) {
                View v = View.inflate(this, R.layout.in_head_right, toolbar);
                ImageView img = (ImageView) v.findViewById(R.id.head_vRight);
                img.setOnClickListener(this);
                img.setImageResource(right);
            } else {
                ImageView img = (ImageView) toolbar.findViewById(R.id.head_vRight);
                img.setImageResource(right);
            }
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(CharSequence title) {
        if (toolbar.findViewById(R.id.head_vTitle) == null) {
            View v = View.inflate(this, R.layout.in_head_title, null);
            Toolbar.LayoutParams tlp = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
            if (tlp != null) {
                tlp.gravity = Gravity.CENTER;
            }
            toolbar.addView(v, tlp);
        }
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.head_vTitle);
        txtTitle.setText(title);
    }

    protected Bundle getBundle() {
        if (getIntent().hasExtra(BaseActivity.BUNDLE)) {
            return getIntent().getBundleExtra(BaseActivity.BUNDLE);
        }
        return null;
    }

    private void initImageData() {
        Bundle bundle = getBundle();
        Uri inputUri = bundle.getParcelable(EXTRA_INPUT_URI);
        outputUri = bundle.getParcelable(EXTRA_OUTPUT_URI);
        if (inputUri == null || outputUri == null) {
            MyLog.e(getClass(), "地址未指定");
            finish();
            return;
        }
        try {
            imgCrop.setImageUri(inputUri);
        } catch (Exception e) {
            MyLog.e(getClass(), "图片地址错误");
            finish();
            return;
        }

        int aspectX = bundle.getInt(EXTRA_ASPECT_X, 0);
        int aspectY = bundle.getInt(EXTRA_ASPECT_Y, 0);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        //以竖直屏幕下的宽度为基准，保持占满屏幕的宽度
        if (aspectX > 0 && aspectX > 0) {
            if ((width / (float) height) > (aspectX / (float) aspectY)) {
                imgCrop.setTargetAspectRatio((width / (float) height));
            } else {
                imgCrop.setTargetAspectRatio(aspectX / (float) aspectY);
            }
        } else {
            imgCrop.setTargetAspectRatio(CropImageView.SOURCE_IMAGE_ASPECT_RATIO);
        }

        int unit = bundle.getInt(EXTRA_ASPECT_UNIT, 0);

        imgCrop.setMaxResultImageSizeX(aspectX * unit);
        imgCrop.setMaxResultImageSizeY(aspectY * unit);
        imgCrop.setScaleEnabled(true);
        imgCrop.setRotateEnabled(false);
        quality = bundle.getInt(EXTRA_QUALITY, DEFAULT_COMPRESS_QUALITY);
        processOptions(bundle);
    }

    private void processOptions(Bundle optionsBundle) {
        if (optionsBundle != null) {

            //Crop image view options
            imgCrop.setMaxBitmapSize(CropImageView.DEFAULT_MAX_BITMAP_SIZE);
            imgCrop.setMaxScaleMultiplier(CropImageView.DEFAULT_MAX_SCALE_MULTIPLIER);
            imgCrop.setImageToWrapCropBoundsAnimDuration(CropImageView.DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION);

            // Overlay view options
            vOverlay.setDimmedColor(getResources().getColor(R.color.ucrop_color_default_dimmed));
            vOverlay.setOvalDimmedLayer(OverlayView.DEFAULT_OVAL_DIMMED_LAYER);
            //vOverlay.setOvalDimmedLayer( OverlayView.DEFAULT_OVAL_DIMMED_LAYER);

            vOverlay.setShowCropFrame(OverlayView.DEFAULT_SHOW_CROP_FRAME);
            vOverlay.setCropFrameColor(getResources().getColor(R.color.red_trans));
            vOverlay.setCropFrameStrokeWidth(getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_frame_stoke_width));

            vOverlay.setShowCropGrid(OverlayView.DEFAULT_SHOW_CROP_GRID);
            vOverlay.setCropGridRowCount(OverlayView.DEFAULT_CROP_GRID_ROW_COUNT);
            vOverlay.setCropGridColumnCount(OverlayView.DEFAULT_CROP_GRID_COLUMN_COUNT);
            vOverlay.setCropGridColor(getResources().getColor(R.color.ucrop_color_default_crop_grid));
            vOverlay.setCropGridStrokeWidth(getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_grid_stoke_width));
        }
    }

    private void setAngleText(float angle) {
//        if (mTextViewRotateAngle != null) {
//            mTextViewRotateAngle.setText(String.format("%.1f°", angle));
//        }
    }

    private void setScaleText(float scale) {
//        if (mTextViewScalePercent != null) {
//            mTextViewScalePercent.setText(String.format("%d%%", (int) (scale * 100)));
//        }
    }

    public void onRightClick(View view) {
        OutputStream outputStream = null;
        try {
            final Bitmap croppedBitmap = imgCrop.cropImage();
            if (croppedBitmap != null) {
                outputStream = getContentResolver().openOutputStream(outputUri);
                croppedBitmap.compress(DEFAULT_COMPRESS_FORMAT, quality, outputStream);
                croppedBitmap.recycle();
                setResult(RESULT_OK, new Intent().setData(outputUri));
                finish();
            } else {
                MyLog.e(getClass(), "剪切失败");
                finish();
            }
        } catch (Exception e) {
            finish();
        } finally {
            BitmapLoadUtils.close(outputStream);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (imgCrop != null) {
            imgCrop.cancelAllAnimations();
        }
    }

    @Override
    public void onClick(View v) {
        if (AppUtil.isFastClick())
            return;
        if (v.getId() == R.id.head_vLeft) {
            finish();
        } else if (v.getId() == R.id.head_vRight) {
            onRightClick(v);
        }
    }
}

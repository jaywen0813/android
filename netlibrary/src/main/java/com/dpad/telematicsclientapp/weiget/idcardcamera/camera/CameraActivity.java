package com.dpad.telematicsclientapp.weiget.idcardcamera.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.util.utils.StatusBarCompat;
import com.dpad.telematicsclientapp.util.utils.StatusBarUtil;
import com.dpad.telematicsclientapp.weiget.idcardcamera.cropper.CropImageView;
import com.dpad.telematicsclientapp.weiget.idcardcamera.cropper.CropListener;
import com.dpad.telematicsclientapp.weiget.idcardcamera.global.Constant;
import com.dpad.telematicsclientapp.weiget.idcardcamera.utils.FileUtils;
import com.dpad.telematicsclientapp.weiget.idcardcamera.utils.ImageUtils;
import com.dpad.telematicsclientapp.weiget.idcardcamera.utils.PermissionUtils;
import com.dpad.telematicsclientapp.weiget.idcardcamera.utils.ScreenUtils;


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/24
 * Desc	        ${拍照界面}
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    public final static int TYPE_IDCARD_FRONT = 1;//身份证正面
    public final static int TYPE_IDCARD_BACK = 2;//身份证反面
    public final static int TYPE_IDCARD_PERSON = 3;//手持身份证
    public final static int REQUEST_CODE = 0X11;//请求码
    public final static int RESULT_CODE = 0X12;//结果码
    private final int PERMISSION_CODE_FIRST = 0x13;//权限请求码
    private final static String TAKE_TYPE = "take_type";//拍摄类型标记
    public final static String IMAGE_PATH = "image_path";//图片路径标记
    private int mType;//拍摄类型
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次

    private CropImageView mCropImageView;
    private Bitmap mCropBitmap;
    private CameraPreview mCameraPreview;
    private View mLlCameraCropContainer;
    private LinearLayout cameraParentLl;
    private ImageView mIvCameraCrop;
    private ImageView mIvCameraFlash;
    private View mLlCameraOption;
    private View mLlCameraResult;
    private TextView mViewCameraCropBottom;
    private FrameLayout mFlCameraOption;
    private View mViewCameraCropLeft;
    private View mviewCameraOutTop;

    private View ivCameraResultCancel;
    private View ivCameraResultOk;

    private String des;

    private ImageView takePhoto;

    private TextView tv_qiehuan;//切换前置摄像头和后置摄像头

    private  boolean qianorhou=true;//默认为true   true为后置摄像头   false为前置摄像头



    /**
     * 跳转到拍照界面
     *
     * @param activity
     * @param type     拍摄类型
     */
    public static void toCameraActivity(Activity activity, int type) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(TAKE_TYPE, type);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * 获取图片路径
     *
     * @param data
     * @return
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(IMAGE_PATH);
        }
        return "";
    }

    /**
     * 根据type 判断相机下面显示的文字
     *
     * @param mType
     * @return
     */
    private String getDes(int mType) {
        String s = getString(R.string.touch_to_focus);
        switch (mType) {
            case TYPE_IDCARD_FRONT:
                s = "请将身份证正面放入扫描框内";
                break;
            case TYPE_IDCARD_BACK:
                s = "请将身份证背面放入扫描框内";
                break;
            case TYPE_IDCARD_PERSON:
                s = "";
                break;
        }
        return s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*动态请求需要的权限*/
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    private void init() {
        setContentView(R.layout.activity_camera);
        mType = getIntent().getIntExtra(TAKE_TYPE, 0);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        des = getDes(mType);

        StatusBarCompat.compat(this, R.color.color_000000);
        StatusBarUtil.clearStatusBarLightMode(this);
        StatusBarUtil.setStatusBarColor(this, R.color.color_000000);
        initView();
        initListener();
    }

    private void initView() {
        takePhoto=findViewById(R.id.iv_camera_take);
        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        mLlCameraCropContainer = findViewById(R.id.ll_camera_crop_container);
        cameraParentLl = findViewById(R.id.camera_parent_ll);
        mIvCameraCrop = findViewById(R.id.iv_camera_crop);
        mIvCameraFlash = (ImageView) findViewById(R.id.iv_camera_flash);
        mLlCameraOption = findViewById(R.id.ll_camera_option);
        mLlCameraResult = findViewById(R.id.ll_camera_result);
        mCropImageView = findViewById(R.id.crop_image_view);
        mViewCameraCropBottom = (TextView) findViewById(R.id.view_camera_crop_bottom);
        mFlCameraOption = (FrameLayout) findViewById(R.id.fl_camera_option);
        mViewCameraCropLeft = findViewById(R.id.view_camera_crop_left);
        mviewCameraOutTop = findViewById(R.id.view_camera_out_top);
        tv_qiehuan=findViewById(R.id.tv_qiehuan);//切换前置摄像头和后置摄像头
        mViewCameraCropBottom.setText(des);
//        float screenMinSize = Math.min(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
//        float screenMaxSize = Math.max(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
//        float height = (int) (screenMinSize * 0.75);
//        float width = (int) (height * 75.0f / 47.0f);
//        //获取底部"操作区域"的宽度
//        float flCameraOptionWidth = (screenMaxSize - width) / 2;
//        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
//        LinearLayout.LayoutParams cameraOptionParams = new LinearLayout.LayoutParams((int) flCameraOptionWidth, ViewGroup.LayoutParams.MATCH_PARENT);
//        mLlCameraCropContainer.setLayoutParams(containerParams);
//        mIvCameraCrop.setLayoutParams(cropParams);
//        //获取"相机裁剪区域"的宽度来动态设置底部"操作区域"的宽度，使"相机裁剪区域"居中
//        mFlCameraOption.setLayoutParams(cameraOptionParams);


        float screenMinSize = Math.min(ScreenUtils.getScreenWidth(this), ScreenUtils.getFullScreenHeight(this));
        float screenMaxSize = Math.max(ScreenUtils.getScreenWidth(this), ScreenUtils.getFullScreenHeight(this));
        float width = (int) (screenMinSize * 0.75);
        float height = (int) (width * 1714 / 1110 + 0.5);
        //获取底部"操作区域"的宽度
//        float flCameraOptionWidth = (screenMaxSize - height - StatusBarUtil.getStatusHeight(this) - UIUtils.dip2px(40)) / 5;//本来需要减40,由于计算尺寸有误差,需要消除,否则有横线
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, (int) height);
        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        cameraParentLl.setLayoutParams(llParams);
//        LinearLayout.LayoutParams cameraOptionParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (3 * flCameraOptionWidth));
//        LinearLayout.LayoutParams cameraTomViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (2 * flCameraOptionWidth));
        mLlCameraCropContainer.setLayoutParams(containerParams);
        mIvCameraCrop.setLayoutParams(cropParams);
        //获取"相机裁剪区域"的宽度来动态设置底部"操作区域"的宽度，使"相机裁剪区域"居中
//        mFlCameraOption.setLayoutParams(cameraOptionParams);
//        mviewCameraOutTop.setLayoutParams(cameraTomViewParams);

        switch (mType) {
            case TYPE_IDCARD_FRONT://正面
                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_front);
                tv_qiehuan.setVisibility(View.INVISIBLE);
                break;
            case TYPE_IDCARD_BACK://反面
                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_front);
                tv_qiehuan.setVisibility(View.INVISIBLE);
                break;
            case TYPE_IDCARD_PERSON://手持
                mIvCameraCrop.setImageResource(R.mipmap.camera_idcard_person);
                tv_qiehuan.setVisibility(View.VISIBLE);
                break;
        }

        /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 500);
    }

    private void initListener() {
        mCameraPreview.setOnClickListener(this);
        mIvCameraFlash.setOnClickListener(this);
        findViewById(R.id.iv_camera_close).setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        tv_qiehuan.setOnClickListener(this);
        ivCameraResultCancel = findViewById(R.id.iv_camera_result_cancel);
        ivCameraResultOk = findViewById(R.id.iv_camera_result_ok);
        ivCameraResultOk.setOnClickListener(this);
        ivCameraResultCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.iv_camera_close) {
            finish();
        } else if (id == R.id.iv_camera_take) {
            takePhoto();
        } else if (id == R.id.iv_camera_flash) {
            boolean isFlashOn = mCameraPreview.switchFlashLight();
            mIvCameraFlash.setImageResource(isFlashOn ? R.mipmap.camera_flash_on : R.mipmap.camera_flash_off);
        } else if (id == R.id.iv_camera_result_ok) {
            confirm();
        } else if (id == R.id.iv_camera_result_cancel) {
            takePhoto.setEnabled(true);
            mCameraPreview.setEnabled(true);
//            mCameraPreview.startPreview();
            mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
            setTakePhotoLayout();
            if (mCameraPreview != null) {
                mCameraPreview.onStart();
            }
            if (qianorhou){
                mCameraPreview.switchCamera(0);
            }else {
                mCameraPreview.switchCamera(1);
            }
        }else if (id==R.id.tv_qiehuan){//切换前置后置摄像头
                if (qianorhou){//默认摄像头为后置，所以第一次点击 切换到前置
                    mCameraPreview.switchCamera(1);
                    qianorhou=false;
                }else {
                    mCameraPreview.switchCamera(0);
                    qianorhou=true;
                }
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        takePhoto.setEnabled(true);
        ivCameraResultCancel.setVisibility(View.VISIBLE);
        ivCameraResultOk.setVisibility(View.VISIBLE);

        int cameraCounts = 0;
        final Camera.CameraInfo info = new Camera.CameraInfo();
        cameraCounts = Camera.getNumberOfCameras();//得到摄像头的个数



        mCameraPreview.getCamera().setOneShotPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(final byte[] bytes, Camera camera) {
                final Camera.Size size = camera.getParameters().getPreviewSize(); //获取预览大小
                camera.stopPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int w = size.width;
                        final int h = size.height;
                        Bitmap bitmap = ImageUtils.getBitmapFromByte(bytes, w, h);

                        if (qianorhou){//证明是后置相机，此时需要翻转照片

                            if (info.facing== Camera.CameraInfo.CAMERA_FACING_BACK){
                                bitmap = rotateBitmap(bitmap, 90f);
                                Log.d("测试翻转","1111");
                            }else{
                                bitmap = rotateBitmap(bitmap, -90f);
                                Log.d("测试翻转","2222");
                            }

                        }else {//此时是前置相机，进行逆时针翻转

                            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                                bitmap = rotateBitmap(bitmap, -90f);
                                Log.d("测试翻转","333");

                            } else{
                                bitmap = rotateBitmap(bitmap, -90f);
                                Log.d("测试翻转","4444");
                            }

                        }
                        cropImage(bitmap);
                    }
                }).start();
            }
        });
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 裁剪图片
     */
    private void cropImage(Bitmap bitmap) {
        /*计算扫描框的坐标点*/
        float left = mViewCameraCropLeft.getWidth();
        float top1 = mviewCameraOutTop.getHeight();
        float top = mIvCameraCrop.getTop() + top1;
        float right = mIvCameraCrop.getRight() + left;
        float bottom = mIvCameraCrop.getBottom() + top1;

        /*计算扫描框坐标点占原图坐标点的比例*/
        float leftProportion = left / mCameraPreview.getWidth();
        float topProportion = top / mCameraPreview.getHeight();
        float rightProportion = right / mCameraPreview.getWidth();
        float bottomProportion = bottom / mCameraPreview.getBottom();

        /*自动裁剪*/
        mCropBitmap = Bitmap.createBitmap(bitmap,
                (int) (leftProportion * (float) bitmap.getWidth()),
                (int) (topProportion * (float) bitmap.getHeight()),
                (int) ((rightProportion - leftProportion) * (float) bitmap.getWidth()),
                (int) ((bottomProportion - topProportion) * (float) bitmap.getHeight()));

        /*设置成手动裁剪模式*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //将手动裁剪区域设置成与扫描框一样大
                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mIvCameraCrop.getWidth(), mIvCameraCrop.getHeight()));
                setCropLayout();
                mCropImageView.setImageBitmap(mCropBitmap);
            }
        });
    }

    /**
     * 设置裁剪布局
     */
    private void setCropLayout() {
        mIvCameraCrop.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.GONE);
        takePhoto.setEnabled(false);
//        mLlCameraOption.setVisibility(View.GONE);
        mCropImageView.setVisibility(View.VISIBLE);
        mLlCameraOption.setVisibility(View.INVISIBLE);
        mLlCameraResult.setVisibility(View.VISIBLE);
        mViewCameraCropBottom.setText("");
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
        mIvCameraCrop.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mLlCameraOption.setVisibility(View.VISIBLE);
        mCropImageView.setVisibility(View.GONE);
        mLlCameraResult.setVisibility(View.GONE);

        ivCameraResultCancel.setVisibility(View.GONE);
        ivCameraResultOk.setVisibility(View.GONE);
        mViewCameraCropBottom.setText(des);

        mCameraPreview.focus();
    }

    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        /*手动裁剪图片*/
        mCropImageView.crop(new CropListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                if (bitmap == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.crop_fail), Toast.LENGTH_SHORT).show();
                    finish();
                }

                /*保存图片到sdcard并返回图片路径*/
                if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                    StringBuffer buffer = new StringBuffer();
                    String imagePath = "";
                    if (mType == TYPE_IDCARD_FRONT) {
                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardFrontCrop.jpg").toString();
                    } else if (mType == TYPE_IDCARD_BACK) {
                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardBackCrop.jpg").toString();
                    } else if (mType == TYPE_IDCARD_PERSON) {
                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardPersonCrop.jpg").toString();
                    }

                    if (ImageUtils.saveThird(bitmap, imagePath, Bitmap.CompressFormat.JPEG, mType == TYPE_IDCARD_PERSON)) {
                        Intent intent = new Intent();
                        intent.putExtra(CameraActivity.IMAGE_PATH, imagePath);
                        setResult(RESULT_CODE, intent);
                        finish();
                    }
                }
            }
        }, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }
}
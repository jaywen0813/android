package com.dpad.telematicsclientapp.weiget.idcardcamera.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



import com.dpad.telematicsclientapp.weiget.idcardcamera.utils.ScreenUtils;

import java.io.IOException;
import java.util.List;



/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/24
 * Desc	        ${相机预览}
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static String TAG = CameraPreview.class.getName();
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private AutoFocusManager mAutoFocusManager;
    private SensorControler mSensorControler;
    private Context mContext;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头
    public CameraPreview(Context context) {
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSensorControler = SensorControler.getInstance(context);
        mSensorControler.setCameraFocusListener(new SensorControler.CameraFocusListener() {
            @Override
            public void onFocus() {
                focus();
            }
        });
    }

    public void surfaceCreated(SurfaceHolder holder) {
        camera = CameraUtils.openCamera(0);
        if (camera != null) {
            try {
                camera.setPreviewDisplay(holder);

                Camera.Parameters parameters = camera.getParameters();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                    camera.setDisplayOrientation(90);
                    parameters.setRotation(90);
                } else {
                    camera.setDisplayOrientation(0);
                    parameters.setRotation(0);
                }
                List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的预览大小
                Camera.Size bestSize = getOptimalPreviewSize(sizeList, ScreenUtils.getScreenWidth(mContext), ScreenUtils.getFullScreenHeight(mContext));
//                if (Kits.Empty.check(bestSize)) {

                    float width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.75);
                float height = (int) (width * 1714 / 1110 + 0.5);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    parameters.setPreviewSize((int) width, (int) height);//设置预览大小
                }else {//
                    parameters.setPreviewSize((int) height, (int) width);//设置预览大小
                }
//                } else {
//                    parameters.setPreviewSize(bestSize.width, bestSize.height);//设置预览大小
//                }
//                parameters.setPreviewSize(bestSize.width, bestSize.height);//设置预览大小
//                float width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.75);
//                float height = (int) (width *  1714/1110 );
//                parameters.setPreviewSize((int) width,(int)height);//设置预览大小
                camera.setParameters(parameters);
                camera.startPreview();
                focus();//首次对焦
                //mAutoFocusManager = new AutoFocusManager(camera);//定时对焦
            } catch (Exception e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
                try {
                    Camera.Parameters parameters = camera.getParameters();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                        camera.setDisplayOrientation(90);
                        parameters.setRotation(90);
                    } else {
                        camera.setDisplayOrientation(0);
                        parameters.setRotation(0);
                    }
                    camera.setParameters(parameters);
                    camera.startPreview();
                    focus();//首次对焦
                    //mAutoFocusManager = new AutoFocusManager(camera);//定时对焦
                } catch (Exception e1) {
                    e.printStackTrace();
                    camera = null;
                }
            }
        }
    }

    /**
     * 获取最佳预览大小
     *
     * @param sizes 所有支持的预览大小
     * @param w     SurfaceView宽
     * @param h     SurfaceView高
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

//         Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        //因为设置了固定屏幕方向，所以在实际使用中不会触发这个方法
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        //回收释放资源
        release();
    }
    public  Camera getCamera() {
        if(camera==null){
            camera = Camera.open(0); // attempt to get a Camera instance
            return camera;
        }else{
            return camera;
        }

    }
    /**
     * 释放资源
     */
    private void release() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;

            if (mAutoFocusManager != null) {
                mAutoFocusManager.stop();
                mAutoFocusManager = null;
            }
        }
    }

    /**
     * 对焦，在CameraActivity中触摸对焦或者自动对焦
     */
    public void focus() {
        if (camera != null) {
            try {
                camera.autoFocus(null);
            } catch (Exception e) {
                Log.d(TAG, "takePhoto " + e);
            }
        }
    }
    /**
     * 镜头切换
     */
    public void switchCamera(int index) {
        this.cameraPosition = index;
        if (camera != null) {
            //切换前后摄像头
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

            for(int i = 0; i < cameraCount;i++ ) {
                Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                if(cameraPosition == 1) {
                    //如果现在是前置，变更为后置
                    if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                        camera.stopPreview();//停掉原来摄像头的预览
                        camera.release();//释放资源
                        camera = null;//取消原来摄像头
                        camera = CameraUtils.openCamera(i);//打开当前选中的摄像头
                        try {
                            camera.setPreviewDisplay(surfaceHolder);//通过surfaceview显示取景画面
                            Camera.Parameters parameters = camera.getParameters();
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                                camera.setDisplayOrientation(90);
                                parameters.setRotation(90);
                            } else {
                                camera.setDisplayOrientation(0);
                                parameters.setRotation(0);
                            }
                            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的预览大小
                            Camera.Size bestSize = getOptimalPreviewSize(sizeList, ScreenUtils.getScreenWidth(mContext), ScreenUtils.getFullScreenHeight(mContext));
                            float width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.75);
                            float height = (int) (width * 1714 / 1110 + 0.5);
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                                parameters.setPreviewSize((int) width, (int) height);//设置预览大小
                            }else {//
                                parameters.setPreviewSize((int) height, (int) width);//设置预览大小
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        camera.startPreview();//开始预览
                        break;
                    }
                } else {
                    //如果现在是后置， 则变更为前置
                    if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                        camera.stopPreview();//停掉原来摄像头的预览
                        camera.release();//释放资源
                        camera = null;//取消原来摄像头
                        camera = CameraUtils.openCamera(i);//打开当前选中的摄像头
                        try {
                            camera.setPreviewDisplay(surfaceHolder);//通过surfaceview显示取景画面
                            Camera.Parameters parameters = camera.getParameters();
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                                camera.setDisplayOrientation(90);
                                parameters.setRotation(90);
                            } else {
                                camera.setDisplayOrientation(0);
                                parameters.setRotation(0);
                            }
                            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的预览大小
                            Camera.Size bestSize = getOptimalPreviewSize(sizeList, ScreenUtils.getScreenWidth(mContext), ScreenUtils.getFullScreenHeight(mContext));
                            float width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.75);
                            float height = (int) (width * 1714 / 1110 + 0.5);
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                                parameters.setPreviewSize((int) width, (int) height);//设置预览大小
                            }else {//
                                parameters.setPreviewSize((int) height, (int) width);//设置预览大小
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        camera.startPreview();//开始预览
                        break;
                    }
                }

            }
        }
    }
    /**
     * 开关闪光灯
     *
     * @return 闪光灯是否开启
     */
    public boolean switchFlashLight() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                return true;
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                return false;
            }
        }
        return false;
    }

    /**
     * 拍摄照片
     *
     * @param pictureCallback 在pictureCallback处理拍照回调
     */
    public void takePhoto(Camera.PictureCallback pictureCallback) {
        if (camera != null) {
            try {
                camera.takePicture(null, null, pictureCallback);
            } catch (Exception e) {
                Log.d(TAG, "takePhoto " + e);
            }
        }
    }

    public void startPreview() {
        if (camera != null) {
            camera.startPreview();
        }
    }

    public void onStart() {
        if (mSensorControler != null) {
            mSensorControler.onStart();
        }
    }

    public void onStop() {
        if (mSensorControler != null) {
            mSensorControler.onStop();
        }
    }

}

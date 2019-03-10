package com.caihan.scframe.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.UriUtils;
import com.caihan.scframe.utils.file.ScFileOptionsUtils;
import com.caihan.scframe.utils.toast.ScToast;

import java.io.File;
import java.io.IOException;


/**
 * 原生相机,相册管理工具
 * <p>
 * 目前拍摄的照片放在相册中
 * 裁剪的照片放在私有目录中
 * 这样确保拍摄的照片能在系统相册中被找到
 * 取消的时候自动删除相关文件
 *
 * @author caihan
 * @date 2019/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class PhotoUtils {

    private static final int REQUEST_CODE_GALLERY = 9998;//原生相册
    private static final int REQUEST_CODE_CAMERA = 9999;//原生相机
    private static final int REQUEST_CODE_CROP = 9997;//原生裁剪

    private Activity mActivity;
    private File mCameraFileDir;//相机拍照后图片存放的文件夹
    private String mCameraFilePath;//相机拍照后图片的存放路径
    private String mCropFilePath;//裁剪后图片的存放路径
    private boolean mEnableCrop = false;//是否启动裁剪功能
    private boolean mDelCameraFile = false;//是否删除拍摄的相片
    private boolean mDelCropFile = false;//是否删除裁剪的相片
    private int mAspectX = 1;
    private int mAspectY = 1;
    private int mOutputX = 480;
    private int mOutputY = 480;
    private onResultListener mOnResultListener;//回调

    private ScFileOptionsUtils mFileOptionsUtils;

    public interface onResultListener {

        /**
         * 取消
         */
        void resultCanceled();

        /**
         * 相机
         *
         * @param imagePath
         * @param isDelCameraFile
         */
        void resultTakeCamera(String imagePath, boolean isDelCameraFile);

        /**
         * 相册
         *
         * @param imagePath
         */
        void resultTakeGallery(String imagePath);

        /**
         * 裁剪
         *
         * @param imagePath
         * @param isDelCameraFile
         * @param isDelCropFile
         */
        void resultTakeCrop(String imagePath, boolean isDelCameraFile, boolean isDelCropFile);
    }

    private PhotoUtils(Builder builder) {
        mActivity = builder.mActivity;
        mFileOptionsUtils = builder.mFileOptionsUtils;
        mEnableCrop = builder.mEnableCrop;
        mDelCameraFile = builder.mDelCameraFile;
        mDelCropFile = builder.mDelCropFile;
        mAspectX = builder.mAspectX;
        mAspectY = builder.mAspectY;
        mOutputX = builder.mOutputX;
        mOutputY = builder.mOutputY;
        mOnResultListener = builder.mOnResultListener;
        mCameraFileDir = mFileOptionsUtils.getRootFile();
    }

    public void onDestroy() {
        mActivity = null;
        mCameraFileDir = null;
        mCameraFilePath = null;
        mCropFilePath = null;
        mFileOptionsUtils = null;
    }

    /**
     * 跳转原生相册
     */
    public void toSystemGallery() {
        mActivity.startActivityForResult(getSystemGalleryIntent(), REQUEST_CODE_GALLERY);
    }

    /**
     * 跳转原生相机
     */
    public void toSystemCamera() {
        try {
            mActivity.startActivityForResult(getSystemCameraIntent(), REQUEST_CODE_CAMERA);
        } catch (Exception e) {
            ScToast.showToast("当前设备不支持拍照");
        }
    }

    public boolean isPhotoRequest(int requestCode) {
        return requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_CODE_CAMERA
                || requestCode == REQUEST_CODE_CROP;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    //原生相册
                    File file2Data = UriUtils.uri2File(data.getData());
                    sendBroadcast(file2Data);
                    if (!FileUtils.isFile(file2Data)) {
                        ScToast.showToast("找不到图片资源");
                    } else {
                        if (mEnableCrop) {
                            try {
                                mActivity.startActivityForResult(getCropIntent(file2Data), REQUEST_CODE_CROP);
                            } catch (Exception e) {
                                deleteCropFile();
                                ScToast.showToast("当前设备不支持裁剪图片");
                                e.printStackTrace();
                            }
                        } else {
                            if (mOnResultListener != null) {
                                mOnResultListener.resultTakeGallery(file2Data.getAbsolutePath());
                            }
                        }
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    //原生相机
                    File file2Camera = new File(mCameraFilePath);
                    sendBroadcast(file2Camera);
                    if (!FileUtils.isFile(file2Camera)) {
                        ScToast.showToast("找不到图片资源");
                    } else {
                        if (mEnableCrop) {
                            try {
                                mActivity.startActivityForResult(getCropIntent(file2Camera), REQUEST_CODE_CROP);
                            } catch (Exception e) {
                                deleteCameraFile();
                                deleteCropFile();
                                ScToast.showToast("当前设备不支持裁剪图片");
                                e.printStackTrace();
                            }
                        } else {
                            if (mOnResultListener != null) {
                                mOnResultListener.resultTakeCamera(file2Camera.getAbsolutePath(), mDelCameraFile);

                            }
                            if (mDelCameraFile) {
                                deleteCameraFile();
                            }
                        }
                    }
                    break;
                case REQUEST_CODE_CROP:
                    //原生裁剪
                    File file2Crop = new File(mCropFilePath);
                    if (!FileUtils.isFile(file2Crop)) {
                        ScToast.showToast("找不到图片资源");
                    } else {
                        if (mOnResultListener != null) {
                            mOnResultListener.resultTakeCrop(file2Crop.getAbsolutePath(), mDelCameraFile, mDelCropFile);
                        }
                        if (mDelCameraFile) {
                            deleteCameraFile();
                        }
                        if (mDelCropFile) {
                            deleteCropFile();
                        }
                    }
                    break;
                default:
                    break;
            }
        } else {
            if (mOnResultListener != null) {
                mOnResultListener.resultCanceled();
            }
            deleteCameraFile();
            deleteCropFile();
        }
    }

    public Bitmap getPhotoBitmap(String imagePath) {
        return ImageUtils.getBitmap(imagePath);
    }

    /**
     * 原生相机
     *
     * @return
     */
    private Intent getSystemCameraIntent() throws IOException {
        return IntentUtils.getCaptureIntent(createFileUri(createCameraFile()));
    }

    /**
     * 创建用于保存拍照生成的图片文件
     *
     * @return
     * @throws IOException
     */
    private File createCameraFile() throws IOException {
        File captureFile = File.createTempFile(
                "Capture_" + TimeUtils.getNowString(),
                ".jpg",
                mCameraFileDir);
        mCameraFilePath = captureFile.getAbsolutePath();
        return captureFile;
    }

    /**
     * 获取从系统相册选图片意图
     *
     * @return
     */
    private Intent getSystemGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return intent;
    }

    /**
     * 获取裁剪图片的 intent
     *
     * @return
     */
    private Intent getCropIntent(File inputFilePath) throws IOException {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(createFileUri(inputFilePath), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", mAspectX);
        intent.putExtra("aspectY", mAspectY);
        intent.putExtra("outputX", mOutputX);
        intent.putExtra("outputY", mOutputY);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createCropFile()));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * 创建用于保存裁剪生成的图片文件
     *
     * @return
     * @throws IOException
     */
    private File createCropFile() throws IOException {
        File cropFile = File.createTempFile(
                "Crop_" + TimeUtils.getNowString(),
                ".jpg",
                mActivity.getExternalCacheDir());
        mCropFilePath = cropFile.getAbsolutePath();
        return cropFile;
    }

    /**
     * 刷新图库
     */
    private void refreshGallery() {
        if (!TextUtils.isEmpty(mCameraFilePath)) {
            sendBroadcast(mCameraFilePath);
            mCameraFilePath = null;
        }
        if (!TextUtils.isEmpty(mCropFilePath)) {
            sendBroadcast(mCropFilePath);
            mCropFilePath = null;
        }
    }

    /**
     * 删除拍摄的照片
     */
    private void deleteCameraFile() {
        if (!StringUtils.isEmpty(mCameraFilePath)) {
            boolean isDel = FileUtils.deleteFile(mCameraFilePath);
            if (isDel) {
                sendBroadcast(mCameraFilePath);
                mCameraFilePath = null;
            }
        }
    }

    /**
     * 删除裁剪的照片
     */
    private void deleteCropFile() {
        if (!StringUtils.isEmpty(mCropFilePath)) {
            boolean isDel = FileUtils.deleteFile(mCropFilePath);
            if (isDel) {
                sendBroadcast(mCropFilePath);
                mCropFilePath = null;
            }
        }
    }

    /**
     * 发送更新图库的广播
     *
     * @param srcFile
     */
    private void sendBroadcast(String srcFile) {
        sendBroadcast(new File(srcFile));
    }

    /**
     * 发送更新图库的广播
     *
     * @param srcFile
     */
    private void sendBroadcast(File srcFile) {
        if (!ImageUtils.isImage(srcFile)) {
            return;
        }
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = createFileUri(srcFile);
        mediaScanIntent.setData(uri);
        mActivity.sendBroadcast(mediaScanIntent);//这个广播的目的就是更新图库
    }

    /**
     * 根据文件创建 Uri
     *
     * @param file
     * @return
     */
    private Uri createFileUri(File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            String authority = mActivity.getApplicationInfo().packageName + ".scframe.file_provider";
            return ScFileProvider.getUriForFile(mActivity, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }


    public static class Builder {

        private Activity mActivity;
        private boolean mEnableCrop = false;//是否启动裁剪功能
        private boolean mDelCameraFile = false;//是否删除拍摄的相片
        private boolean mDelCropFile = false;//是否删除裁剪的相片
        private int mAspectX = 1;
        private int mAspectY = 1;
        private int mOutputX = 480;
        private int mOutputY = 480;
        private onResultListener mOnResultListener;//回调

        private ScFileOptionsUtils mFileOptionsUtils;

        public Builder(Activity activity) {
            mActivity = activity;
        }

        public Builder setEnableCrop(boolean enableCrop) {
            mEnableCrop = enableCrop;
            return this;
        }

        public Builder setDelCameraFile(boolean delCameraFile) {
            mDelCameraFile = delCameraFile;
            return this;
        }

        public Builder setDelCropFile(boolean delCropFile) {
            mDelCropFile = delCropFile;
            return this;
        }

        public Builder setAspectX(int aspectX) {
            mAspectX = aspectX;
            return this;
        }

        public Builder setAspectY(int aspectY) {
            mAspectY = aspectY;
            return this;
        }

        public Builder setOutputX(int outputX) {
            mOutputX = outputX;
            return this;
        }

        public Builder setOutputY(int outputY) {
            mOutputY = outputY;
            return this;
        }

        public Builder setFileOptionsUtils(ScFileOptionsUtils fileOptionsUtils) {
            mFileOptionsUtils = fileOptionsUtils;
            return this;
        }

        public Builder setOnResultListener(onResultListener onResultListener) {
            mOnResultListener = onResultListener;
            return this;
        }

        public PhotoUtils build() {
            if (mFileOptionsUtils == null) {
                mFileOptionsUtils = new ScFileOptionsUtils(mActivity, ScFileOptionsUtils.ScFileExternalDir.DIRECTORY_CAMERA) {
                    @Override
                    public File getRootFile() {
                        return super.getRootFile();
                    }

                    @Override
                    public String getFilePathS(String fileName, Suffix suffix) {
                        return super.getFilePathS(fileName, suffix);
                    }
                };
            }
            return new PhotoUtils(this);
        }
    }

}

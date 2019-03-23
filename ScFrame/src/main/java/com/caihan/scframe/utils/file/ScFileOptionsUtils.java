package com.caihan.scframe.utils.file;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.storage.StorageManager;

import com.blankj.utilcode.util.FileUtils;
import com.caihan.scframe.utils.log.ScLog;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 文件路径选择器
 * <p>
 * 内部存储
 * Environment.getDataDirectory()->/data
 * Environment.getDownloadCacheDirectory()->/cache
 * Environment.getRootDirectory()->/system
 * <p>
 * 外部存储
 * Environment.getExternalStorageDirectory()->/storage/sdcard0
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)->/storage/sdcard0/Alarms
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)->/storage/sdcard0/DCIM
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)->/storage/sdcard0/Download
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)->/storage/sdcard0/Movies
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)->/storage/sdcard0/Music
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)->/storage/sdcard0/Notifications
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)->/storage/sdcard0/Pictures
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)->/storage/sdcard0/Podcasts
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)->/storage/sdcard0/Ringtones
 * 上面的九个方法对应的就是SD卡的九大公有目录，Google官方建议我们数据应该存储在私有目录下，不建议存储在公有目录下或其他地方
 * <p>
 * 私有目录(外部存储的App的包名下)
 * Context.getExternalFilesDir()->/storage/emulated/0/Android/data/com.caihan.scframe/files
 * Context.getExternalCacheDir()->/storage/emulated/0/Android/data/com.caihan.scframe/cache
 * <p>
 * 自定义外部存储目录的话需要传入{@link IFileConfigure},然后调用{@link ScFileOptionsUtils#ScFileOptionsUtils(Context, IFileConfigure)}
 *
 * @author caihan
 * @date 2019/1/18
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class ScFileOptionsUtils {

    /**
     * 外部存储器默认根目录
     */
    private String mExternalStorageBaseFile = "ScFrameFile";

    private IFileConfigure mFileConfigure;

    //内部存储
    public enum ScFileInternalDir {
        DATA_DIRECTORY,
        DOWNLOAD_CACHE_DIRECTORY,
        ROOT_DIRECTORY,
    }

    public enum ScFileExternalDir {
        //外部存储
        EXTERNAL_STORAGE_DIRECTORY,
        DIRECTORY_ALARMS,
        DIRECTORY_DCIM,
        DIRECTORY_DOWNLOADS,
        DIRECTORY_MOVIES,
        DIRECTORY_MUSIC,
        DIRECTORY_NOTIFICATIONS,
        DIRECTORY_PICTURES,
        DIRECTORY_PODCASTS,
        DIRECTORY_RINGTONES,
        //自定义应用目录
        DIRECTORY_CAMERA,
        DIRECTORY_APP_BASE_FILE,
        //私有目录
        EXTERNAL_FILES_DIR,
        EXTERNAL_CACHE_DIR,
    }

    /**
     * 后缀
     */
    public enum Suffix {
        JPG(".jpg"),
        TXT(".txt"),
        APK(".apk");

        Suffix(String suffix) {
            this.suffix = suffix;
        }

        final String suffix;
    }

    private Context mContext;
    private File mRootFile;

    public File getRootFile() {
        if (mRootFile == null) {
            throw new NullPointerException("mRootFile 不能为空");
        }
        return mRootFile;
    }

    /**
     * 内部存储
     *
     * @param context
     * @param internalDir
     */
    public ScFileOptionsUtils(Context context, ScFileInternalDir internalDir) {
        if (!hasExternalStoragePermission(context)) {
            return;
        }
        mContext = context;
        switch (internalDir) {
            case DATA_DIRECTORY:
                mRootFile = Environment.getDataDirectory();
                break;
            case DOWNLOAD_CACHE_DIRECTORY:
                mRootFile = Environment.getDownloadCacheDirectory();
                break;
            case ROOT_DIRECTORY:
                mRootFile = Environment.getRootDirectory();
                break;
            default:
                break;
        }
    }

    /**
     * 外部存储
     *
     * @param context
     * @param externalDir
     */
    public ScFileOptionsUtils(Context context, ScFileExternalDir externalDir) {
        if (!hasExternalStoragePermission(context)) {
            return;
        }
        mContext = context;
        switch (externalDir) {
            case EXTERNAL_STORAGE_DIRECTORY:
                mRootFile = Environment.getExternalStorageDirectory();
                break;
            case DIRECTORY_ALARMS:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
                break;
            case DIRECTORY_DCIM:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                break;
            case DIRECTORY_DOWNLOADS:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                break;
            case DIRECTORY_MOVIES:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                break;
            case DIRECTORY_MUSIC:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                break;
            case DIRECTORY_NOTIFICATIONS:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
                break;
            case DIRECTORY_PICTURES:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                break;
            case DIRECTORY_PODCASTS:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
                break;
            case DIRECTORY_RINGTONES:
                mRootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
                break;
            case DIRECTORY_CAMERA:
                mRootFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
                if (!FileUtils.createOrExistsDir(mRootFile)) {
                    ScLog.e("目录创建失败 mRootFile = " + mRootFile.getAbsolutePath());
                }
                break;
            case DIRECTORY_APP_BASE_FILE:
                mRootFile = new File(Environment.getExternalStorageDirectory(), mExternalStorageBaseFile);
                if (!FileUtils.createOrExistsDir(mRootFile)) {
                    ScLog.e("目录创建失败 mRootFile = " + mRootFile.getAbsolutePath());
                }
                break;
            case EXTERNAL_FILES_DIR:
                mRootFile = mContext.getExternalFilesDir(null);
                break;
            case EXTERNAL_CACHE_DIR:
                mRootFile = mContext.getExternalCacheDir();
                break;
            default:
                break;
        }
    }

    /**
     * 外部存储,指定目录
     *
     * @param context
     * @param dir
     */
    public ScFileOptionsUtils(Context context, String dir) {
        if (!hasExternalStoragePermission(context)) {
            return;
        }
        mContext = context;
        mRootFile = new File(Environment.getExternalStorageDirectory(), dir);
        boolean create = FileUtils.createOrExistsDir(mRootFile);
        if (!create) {
            ScLog.e("目录创建失败 dir = " + dir);
        }
    }

    /**
     * 自定义目录
     *
     * @param context
     * @param rootFile
     * @param dir
     */
    public ScFileOptionsUtils(Context context, File rootFile, String dir) {
        if (!hasExternalStoragePermission(context)) {
            return;
        }
        mContext = context;
        mRootFile = new File(rootFile, dir);
        boolean create = FileUtils.createOrExistsDir(mRootFile);
        if (!create) {
            ScLog.e("目录创建失败 rootFile = " + rootFile.getAbsolutePath() + " / dir = " + dir);
        }
    }

    /**
     * 自定义外部存储路径
     *
     * @param context
     * @param iFileConfigure
     */
    public ScFileOptionsUtils(Context context, IFileConfigure iFileConfigure) {
        if (!hasExternalStoragePermission(context)) {
            return;
        }
        mContext = context;
        if (iFileConfigure == null) {
            throw new NullPointerException("iFileConfigure 不能为空");
        } else {
            mFileConfigure = iFileConfigure;
            mRootFile = new File(Environment.getExternalStorageDirectory(), iFileConfigure.getExternalStorageAppBaseChild());
            boolean create = FileUtils.createOrExistsDir(mRootFile);
            if (!create) {
                ScLog.e("目录创建失败 mRootFile = " + mRootFile.getAbsolutePath());
            }
        }
    }

    /**
     * 判断是否有存储卡写的权限
     *
     * @param context
     * @return
     */
    private boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (perm != PackageManager.PERMISSION_GRANTED) {
            ScLog.e("无存储卡权限");
        }
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取文件路径
     *
     * @param fileName 文件名
     * @param suffix   后缀
     * @return
     */
    public String getFilePathS(String fileName, Suffix suffix) {
        return getRootFile().getAbsolutePath() + "/" + fileName + suffix.suffix;
    }


    /**
     * 获取外置SD卡路径
     *
     * @param mContext
     * @return
     */
    private static String getExtendedMemoryPath(Context mContext) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        if (mStorageManager != null) {
            Class<?> storageVolumeClazz = null;
            try {
                storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
                Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
                Method getPath = storageVolumeClazz.getMethod("getPath");
                Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
                Object result = getVolumeList.invoke(mStorageManager);
                final int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    Object storageVolumeElement = Array.get(result, i);
                    String path = (String) getPath.invoke(storageVolumeElement);
                    boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                    if (removable) {
                        return path;
                    }
                }
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

package com.caihan.scframe.utils.file;

/**
 * @author caihan
 * @date 2019/1/26
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface IFileConfigure {


//    /**
//     * 内部存储
//     * Environment.getDataDirectory()->/data
//     *
//     * @return
//     */
//    File getDataDirectory();
//
//    /**
//     * 内部存储
//     * Environment.getDownloadCacheDirectory()->/cache
//     *
//     * @return
//     */
//    File getDownloadCacheDirectory();
//
//    /**
//     * 内部存储
//     * Environment.getRootDirectory()->/system
//     *
//     * @return
//     */
//    File getRootDirectory();
//
//
//    /**
//     * 外部存储
//     * Environment.getExternalStorageDirectory()->/storage/sdcard0
//     *
//     * @return
//     */
//    File getExternalStorageDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)->/storage/sdcard0/Alarms
//     *
//     * @return
//     */
//    File getExternalStorageAlarmsDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)->/storage/sdcard0/DCIM
//     *
//     * @return
//     */
//    File getExternalStorageDCIMDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)->/storage/sdcard0/Download
//     *
//     * @return
//     */
//    File getExternalStorageDownloadDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)->/storage/sdcard0/Movies
//     *
//     * @return
//     */
//    File getExternalStorageMoviesDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)->/storage/sdcard0/Music
//     *
//     * @return
//     */
//    File getExternalStorageMusicDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)->/storage/sdcard0/Notifications
//     *
//     * @return
//     */
//    File getExternalStorageNotificationsDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)->/storage/sdcard0/Pictures
//     *
//     * @return
//     */
//    File getExternalStoragePicturesDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)->/storage/sdcard0/Podcasts
//     *
//     * @return
//     */
//    File getExternalStoragePodcastsDirectory();
//
//    /**
//     * 外部存储
//     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)->/storage/sdcard0/Ringtones
//     *
//     * @return
//     */
//    File getExternalStorageRingtonesDirectory();

    /**
     * 自定义外部存储目录
     * /storage/sdcard0/<自定义>
     *
     * @return
     */
    String getExternalStorageAppBaseChild();



//    /**
//     * 私有目录(外部存储的App的包名下)
//     * Context.getExternalFilesDir()->/storage/emulated/0/Android/data/com.caihan.scframe/files
//     *
//     * @return
//     */
//    File getExternalFilesDir();
//
//    /**
//     * 私有目录(外部存储的App的包名下)
//     * Context.getExternalCacheDir()->/storage/emulated/0/Android/data/com.caihan.scframe/cache
//     *
//     * @return
//     */
//    File getExternalCacheDir();

}

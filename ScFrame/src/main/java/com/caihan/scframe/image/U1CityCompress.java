package com.caihan.scframe.image;

import android.content.Context;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import top.zibin.luban.OnCompressListener;

/**
 * 图片压缩,采用鲁班压缩技术
 *
 * @author caihan
 * @date 2018/3/5
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class U1CityCompress {


    /**
     * 普通用法
     *
     * @param context
     * @param photos    图片
     * @param targetDir 压缩后文件存储位置
     * @param listener
     */
    public static void Compress(Context context, String photos, String targetDir,
                                OnCompressListener listener) {
        LubanCompress.Compress(context, photos, targetDir, listener);
    }

    /**
     * Rx用法
     *
     * @param context
     * @param photos    图片
     * @param targetDir 压缩后文件存储位置
     * @return
     * @throws IOException
     */
    public static List<File> RxCompress(Context context, String photos, String targetDir) throws IOException {
        return LubanCompress.RxCompress(context, photos, targetDir);
    }

    /**
     * 删除图片
     *
     * @param file
     * @return true = 删除成功
     */
    public static boolean delPhoto(File file) {
        return FileUtils.deleteDir(file);
    }

    public static boolean delPhoto(String path) {
        return FileUtils.deleteFile(path);
    }
}

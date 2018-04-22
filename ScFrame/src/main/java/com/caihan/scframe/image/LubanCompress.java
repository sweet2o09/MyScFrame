package com.caihan.scframe.image;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author caihan
 * @date 2018/3/5
 * @e-mail 93234929@qq.com
 * 维护者
 */
class LubanCompress {

    public static final int MIN_SIZE = 100;//100KB
    /**
     * 普通用法
     * @param context
     * @param photos
     * @param targetDir
     * @param listener
     */
    public static void Compress(Context context, String photos, String targetDir,
                                OnCompressListener listener) {
        Luban.with(context)
                .load(photos)// 传人要压缩的图片列表
                .ignoreBy(MIN_SIZE)// 忽略不压缩图片的大小
                .setTargetDir(targetDir)// 设置压缩后文件存储位置
//                .setCompressListener(new OnCompressListener() { //设置回调
//                    @Override
//                    public void onStart() {
//                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // TODO 当压缩过程出现问题时调用
//                    }
//                })
                .setCompressListener(listener)
                .launch();//启动压缩
    }

    /**
     * Rx用法
     * @param context
     * @param photos
     * @param targetDir
     * @return
     * @throws IOException
     */
    public static List<File> RxCompress(Context context, String photos, String targetDir) throws IOException {
        return Luban.with(context)
                .load(photos)// 传人要压缩的图片列表
                .ignoreBy(MIN_SIZE)// 忽略不压缩图片的大小
                .setTargetDir(targetDir)// 设置压缩后文件存储位置
                .get();
    }
}

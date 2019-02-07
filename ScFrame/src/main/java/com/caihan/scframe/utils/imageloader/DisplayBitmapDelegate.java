package com.caihan.scframe.utils.imageloader;

import android.graphics.Bitmap;

/**
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface DisplayBitmapDelegate {

    void getBitmapSuccess(String path, Bitmap bitmap);

    void getBitmapError(String path);
}

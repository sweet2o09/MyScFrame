package com.caihan.scframe.utils.imageloader;

import android.graphics.drawable.Drawable;

/**
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface DisplayDrawableDelegate {

    void getDrawableSuccess(String path, Drawable drawable);

    void getDrawableError(String path);
}

package com.caihan.scframe.album;

import android.graphics.Bitmap;
import android.net.Uri;

import com.caihan.scframe.config.IntentValue;

/**
 * @author caihan
 * @date 2018/3/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class CropInfo {
    //地址
    Uri cropUri;
    //裁剪框比例
    int aspectX = 1;
    int aspectY = 1;
    //图片输出大小
    int outputX = 480;
    int outputY = 480;

    boolean enableScale = true;
    boolean enableScaleUpIfNeed = true;
    String outputFormat = Bitmap.CompressFormat.JPEG.toString();

    int requestCode = IntentValue.REQUEST_CODE_CROP;
}

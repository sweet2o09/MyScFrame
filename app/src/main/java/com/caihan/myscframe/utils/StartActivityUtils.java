package com.caihan.myscframe.utils;

import android.content.Context;
import android.content.Intent;

import com.caihan.myscframe.ui.home.MainActivity;

/**
 * 启动Activity工具类
 *
 * @author caihan
 * @date 2018/4/22
 * @e-mail 93234929@qq.com
 * 维护者
 */
public final class StartActivityUtils {

    public static void toMainActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }
}

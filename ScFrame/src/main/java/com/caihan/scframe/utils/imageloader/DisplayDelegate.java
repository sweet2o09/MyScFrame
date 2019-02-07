package com.caihan.scframe.utils.imageloader;

import android.view.View;

/**
 * 图片加载监听，只是用于监听成功或失败的状态
 *
 * @author caihan
 * @date 2019/2/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface DisplayDelegate {
    void onSuccess(View view, String path);

    void onError(View view, String path);
}

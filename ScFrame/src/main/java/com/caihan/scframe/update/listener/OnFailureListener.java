package com.caihan.scframe.update.listener;

import com.caihan.scframe.update.error.UpdateError;

/**
 * Created by caihan on 2017/5/22.
 * 没有新版本或出错
 */
public interface OnFailureListener {
    void onFailure(UpdateError error);
}
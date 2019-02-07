package com.caihan.scframe.utils.dialog;

import android.app.Dialog;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 弹窗队列
 *
 * @author caihan
 * @date 2019/2/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MyQueue {

    private Queue<Dialog> mDialogQueue = new LinkedList<>();

    /**
     * 进队
     *
     * @param dialog
     */
    public void offer(Dialog dialog) {
        mDialogQueue.offer(dialog);
    }

    /**
     * 出队
     *
     * @return
     */
    public Dialog poll() {
        return mDialogQueue.poll();
    }

}

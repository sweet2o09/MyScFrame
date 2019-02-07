package com.caihan.scframe.utils.dialog;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.List;

/**
 * 弹窗队列工具类
 *
 * @author caihan
 * @date 2019/2/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class DialogQueueUtils {

    private static final String TAG = "DialogQueueUtils";
    private MyQueue mMyQueue;

    private Dialog mCurrentDialog = null;//当前显示的Dialog

    private DialogQueueUtils() {
        mMyQueue = new MyQueue();
    }

    public static DialogQueueUtils getInstance() {
        return DialogQueueHolder.singleton;
    }

    /**
     * 单例模式->静态内部类<br/>
     * 多线程情况下，使用合理一些,推荐
     */
    static class DialogQueueHolder {
        private static DialogQueueUtils singleton = new DialogQueueUtils();
    }

    public void addDialog(List<Dialog> dialogs){
        for (Dialog dialog : dialogs) {
            if (dialog != null){
                mMyQueue.offer(dialog);
            }
        }
    }

    public void addDialog(Dialog dialog){
        if (dialog != null){
            mMyQueue.offer(dialog);
        }
    }

    public void show(){
        if (mCurrentDialog == null){
            //从队列中拿出一个Dialog实例,并从列表中移除
            mCurrentDialog = mMyQueue.poll();
            //当队列为空的时候拿出来的会是null
            if (mCurrentDialog != null){
                mCurrentDialog.show();
                mCurrentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //这边设置了dismiss监听,在监听回调中再次调用show方法,可以获取下一个弹窗
                        mCurrentDialog = null;
                        show();
                    }
                });
            }
        }
    }
}
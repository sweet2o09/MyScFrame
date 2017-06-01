package com.caihan.scframe.update.defaultclass;

import android.content.DialogInterface;

import com.caihan.scframe.update.agent.IUpdateAgent;

/**
 * Created by caihan on 2017/5/22.
 * 升级Dialog按钮监听
 */
public class DefaultPromptClickListener implements DialogInterface.OnClickListener {
    private final IUpdateAgent mAgent;
    private final boolean mIsAutoDismiss;

    /**
     * @param agent
     * @param isAutoDismiss 是否自动关闭Dialog
     */
    public DefaultPromptClickListener(IUpdateAgent agent, boolean isAutoDismiss) {
        mAgent = agent;
        mIsAutoDismiss = isAutoDismiss;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //确定,立即更新
                mAgent.update();
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                //忽略该版
                mAgent.ignore();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                //以后再说
                break;
            default:
                break;
        }
        if (mIsAutoDismiss) {
            dialog.dismiss();
        }
    }
}
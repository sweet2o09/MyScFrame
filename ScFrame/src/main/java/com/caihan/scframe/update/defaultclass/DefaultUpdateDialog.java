package com.caihan.scframe.update.defaultclass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.caihan.scframe.update.agent.IUpdateAgent;
import com.caihan.scframe.update.builder.IUpdatePrompter;
import com.caihan.scframe.update.data.ScUpdateInfo;

/**
 * Created by caihan on 2017/5/22.
 * 默认的新版本升级Dialog
 */
public class DefaultUpdateDialog implements IUpdatePrompter {

    private Context mContext;

    public DefaultUpdateDialog(Context context) {
        mContext = context;
    }

    @Override
    public void prompt(IUpdateAgent agent) {
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }
        final ScUpdateInfo info = agent.getInfo();
        String size = Formatter.formatShortFileSize(mContext, info.size);
        String content = String.format("最新版本：%1$s\n新版本大小：%2$s\n\n更新内容\n%3$s",
                info.versionName, size, info.updateContent);

        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();

        dialog.setTitle("应用更新");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        float density = mContext.getResources().getDisplayMetrics().density;
        TextView tv = new TextView(mContext);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setVerticalScrollBarEnabled(true);
        tv.setTextSize(14);
        tv.setMaxHeight((int) (250 * density));

        dialog.setView(tv, (int) (25 * density), (int) (15 * density), (int) (25 * density), 0);


        DialogInterface.OnClickListener listener = new DefaultPromptClickListener(agent, true);

        if (info.isForce) {
            tv.setText("您需要更新应用才能继续使用\n\n" + content);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", listener);
        } else {
            tv.setText(content);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "立即更新", listener);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "以后再说", listener);
            if (info.isIgnorable) {
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "忽略该版", listener);
            }
        }
        dialog.show();
    }
}

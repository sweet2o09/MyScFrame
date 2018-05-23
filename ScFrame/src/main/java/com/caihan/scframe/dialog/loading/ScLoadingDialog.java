package com.caihan.scframe.dialog.loading;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.caihan.scframe.R;
import com.caihan.scframe.utils.ScOutdatedUtils;

/**
 * Created by caihan on 2017/5/2.
 * 框架自带的默认LoadingDialog
 */
public class ScLoadingDialog extends Dialog {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static ScLoadingDialog dialog;
    private Context context;
    private TextView loadingMessage;
    private ProgressBar progressBar;
    private LinearLayout loadingView;
    private ScColorDrawable drawable;

    public ScLoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
        drawable = new ScColorDrawable();
        setContentView(R.layout.dialog_loading_default_layout);
        loadingMessage = findViewById(R.id.loading_message);
        progressBar = findViewById(R.id.loading_progressbar);
        loadingView = findViewById(R.id.loading_layout);
        loadingMessage.setPadding(0, 15, 0, 0);
        drawable.setColor(Color.WHITE);
        ScOutdatedUtils.setBackground(loadingView, drawable);
    }

    public static ScLoadingDialog with(Context context) {
        if (dialog == null) {
            dialog = new ScLoadingDialog(context);
        }
        return dialog;
    }

    public ScLoadingDialog setOrientation(int orientation) {
        loadingView.setOrientation(orientation);
        if (orientation == HORIZONTAL) {
            loadingMessage.setPadding(15, 0, 0, 0);
        } else {
            loadingMessage.setPadding(0, 15, 0, 0);
        }
        return dialog;
    }

    public ScLoadingDialog setBackgroundColor(@ColorInt int color) {
        drawable.setColor(color);
        ScOutdatedUtils.setBackground(loadingView, drawable);
        return dialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog != null)
            dialog = null;
    }

    public ScLoadingDialog setCanceled(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
        return dialog;
    }

    public ScLoadingDialog setMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            loadingMessage.setText(message);
        }
        return this;
    }

    public ScLoadingDialog setMessageColor(@ColorInt int color) {
        loadingMessage.setTextColor(color);
        return this;
    }
}

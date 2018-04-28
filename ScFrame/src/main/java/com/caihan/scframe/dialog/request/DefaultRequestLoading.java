package com.caihan.scframe.dialog.request;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.caihan.scframe.R;

/**
 * @author caihan
 * @date 2018/2/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class DefaultRequestLoading implements IRequestLoad {

    private Context mContext;
    /**
     * 用于网络请求的Dialog
     */
    private MaterialDialog mRequestLoading;
    private boolean cancelable = false;

    public DefaultRequestLoading(Context context) {
        mContext = context;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public MaterialDialog getRequestLoading() {
        if (mRequestLoading == null) {
            mRequestLoading = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.scframe_base_request_loading_layout, false)
                    .cancelable(cancelable)
                    .build();

            Window window = mRequestLoading.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable());
                WindowManager.LayoutParams params = window.getAttributes();
                params.dimAmount = 0f;
                window.setAttributes(params);
            }
        }
        return mRequestLoading;
    }

    @Override
    public boolean isShowing() {
        return mRequestLoading != null && mRequestLoading.isShowing();
    }

    @Override
    public void show() {
        getRequestLoading().show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            getRequestLoading().dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismiss();
        mRequestLoading = null;
    }
}

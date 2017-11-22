package com.caihan.scframe.widget.loadingview.shapeloading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.caihan.scframe.R;

/**
 * 作者：caihan
 * 创建时间：2017/11/20
 * 邮箱：93234929@qq.com
 * 说明：
 * 高仿58网络加载Dialog
 * https://github.com/zzz40500/android-shapeLoadingView
 */
public class ShapeLoadingDialog extends Dialog {

    private FrameLayout mFrameLayout;

    private LoadingView mLoadingView;

    private Builder mBuilder;

    private ShapeLoadingDialog(Builder builder) {
        super(builder.mContext, R.style.loading_dialog_58);
        mBuilder = builder;
        setCancelable(mBuilder.mCancelable);
        setCanceledOnTouchOutside(mBuilder.mCanceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_58_layout);

        mFrameLayout = findViewById(R.id.loading_layout);
        mLoadingView = findViewById(R.id.loadView);

        mLoadingView.setDelay(mBuilder.mDelay);
        mLoadingView.setLoadingText(mBuilder.mLoadText);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        mLoadingView.setVisibility(View.VISIBLE);
    }


    public Builder getBuilder() {
        return mBuilder;
    }

    public static class Builder {

        private Context mContext;

        private int mDelay = 80;

        private CharSequence mLoadText;

        private boolean mCancelable = false;

        private boolean mCanceledOnTouchOutside = false;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder delay(int delay) {
            mDelay = delay;
            return this;
        }

        public Builder loadText(CharSequence loadText) {
            mLoadText = loadText;
            return this;
        }

        public Builder loadText(int resId) {
            mLoadText = mContext.getString(resId);
            return this;
        }

        /**
         * 是否允许点击返回键以及其他地方关闭Dialog,默认是false
         *
         * @param cancelable
         * @return
         */
        public Builder cancelable(boolean cancelable) {
            mCancelable = cancelable;
            mCanceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public ShapeLoadingDialog build() {
            return new ShapeLoadingDialog(this);
        }

        public ShapeLoadingDialog show() {
            ShapeLoadingDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}

package com.caihan.scframe.framework.v1.support.mvp.lce.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.caihan.scframe.R;


/**
 * MVP->LCE框架->默认动画策略
 *
 * @author caihan
 * @date 2018/1/20
 * @e-mail 93234929@qq.com
 * 维护者
 */
@SuppressLint("NewApi")
public class DefaultLceAnimator implements ILceAnimator {

    private volatile static DefaultLceAnimator lceAnimator;
    private Handler mMainHandler;

    public DefaultLceAnimator() {
    }

    public Handler getMainHandler() {
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        return mMainHandler;
    }

    public static DefaultLceAnimator getInstance() {
        if (lceAnimator == null) {
            synchronized (DefaultLceAnimator.class) {
                if (lceAnimator == null) {
                    lceAnimator = new DefaultLceAnimator();
                }
            }
        }
        return lceAnimator;
    }

    @Override
    public void showLoadingLayout(final View loadingView, final View contentView, final View errorView) {
        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                //已在主线程中，可以更新UI
                contentView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showErrorLayout(final View loadingView, final View contentView, final View errorView) {
        contentView.setVisibility(View.GONE);

        final Resources resources = loadingView.getResources();
        // Not visible yet, so animate the view in
        final AnimatorSet set = new AnimatorSet();
        ObjectAnimator in = ObjectAnimator.ofFloat(errorView, "alpha", 1f);
        ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView,
                "alpha", 0f);

        set.playTogether(in, loadingOut);
        set.setDuration(resources
                .getInteger(R.integer.lce_error_view_show_animation_time));

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                errorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                loadingView.setVisibility(View.GONE);
                loadingView.setAlpha(1f); // For future showLoading calls
            }
        });

        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                //已在主线程中，可以更新UI
                set.start();
            }
        });
    }

    @Override
    public void showContentLayout(final View loadingView, final View contentView, final View errorView) {
        if (contentView.getVisibility() == View.VISIBLE) {
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        } else {

            errorView.setVisibility(View.GONE);

            final Resources resources = loadingView.getResources();
            final int translateInPixels = resources
                    .getDimensionPixelSize(R.dimen.lce_content_view_animation_translate_y);
            // Not visible yet, so animate the view in
            final AnimatorSet set = new AnimatorSet();
            ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView,
                    "alpha", 0f, 1f);
            ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(
                    contentView, "translationY", translateInPixels, 0);

            ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView,
                    "alpha", 1f, 0f);
            ObjectAnimator loadingTranslateOut = ObjectAnimator.ofFloat(
                    loadingView, "translationY", 0, -translateInPixels);

            set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut,
                    loadingTranslateOut);
            set.setDuration(resources
                    .getInteger(R.integer.lce_content_view_show_animation_time));

            set.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    contentView.setTranslationY(0);
                    loadingView.setTranslationY(0);
                    contentView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingView.setVisibility(View.GONE);
                    loadingView.setAlpha(1f); // For future showLoading calls
                    contentView.setTranslationY(0);
                    loadingView.setTranslationY(0);
                }
            });

            getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    //已在主线程中，可以更新UI
                    set.start();
                }
            });
        }
    }

}

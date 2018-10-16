package com.caihan.myscframe.ui.multilayerfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScMvpFragment;
import com.caihan.scframe.utils.log.ScLog;

import butterknife.BindView;

/**
 * @author caihan
 * @date 2018/9/28
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultilayerSecondMvpFragment
        extends BaseScMvpFragment<MultilayerSecondMvpFragmentContract.View, MultilayerSecondMvpFragmentPresenter>
        implements MultilayerSecondMvpFragmentContract.View{

    @BindView(R.id.text_view)
    TextView mTextView;
    @BindView(R.id.btn)
    Button mBtn;

    private int mType;

    public static MultilayerSecondMvpFragment newInstance(int type) {
        MultilayerSecondMvpFragment fragment = new MultilayerSecondMvpFragment();
        Bundle args = new Bundle();
        args.putInt("key", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionTransparentDarkFont(true);
    }

    @Override
    protected void onViewCreatedMvp() {
        mType = getArguments().getInt("key");
        ScLog.debug("SecondFragment mType = " + mType + " onViewCreatedMvp");
        mTextView.setText("我是SecondFragment mType = " + mType);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_multilayer_second_mvp;
    }

    @Override
    protected void lazyLoadData() {
        ScLog.debug("SecondFragment mType = " + mType + " lazyLoadData");
        getPresenter().showWord();
    }

    @NonNull
    @Override
    public MultilayerSecondMvpFragmentPresenter createPresenter() {
        return new MultilayerSecondMvpFragmentPresenter(mContext);
    }

    @Override
    public void showWord() {
        mBtn.setText("" + mType);
        showToast("SecondFragment mType = " + mType + " lazyLoadData");
    }
}

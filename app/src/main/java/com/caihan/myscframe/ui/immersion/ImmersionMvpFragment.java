package com.caihan.myscframe.ui.immersion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScMvpFragment;
import com.caihan.scframe.utils.log.ScLog;

import butterknife.BindView;

/**
 * @author caihan
 * @date 2018/5/12
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionMvpFragment
        extends BaseScMvpFragment<ImmersionMvpFragmentContract.View, ImmersionMvpFragmentPresenter>
        implements ImmersionMvpFragmentContract.View {

    @BindView(R.id.text_view)
    TextView mTextView;


    private int mType;

    public static ImmersionMvpFragment newInstance(int type) {
        ImmersionMvpFragment fragment = new ImmersionMvpFragment();
        Bundle args = new Bundle();
        args.putInt("key", type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public boolean openImmersion() {
        return true;
    }

    @Override
    public void setImmersion() {
        ScLog.debug("ImmersionMvpFragment mType = " + mType + " setImmersion");
        getImmersion().setImmersionTransparentDarkFont(mType % 2 == 1);
    }


    @NonNull
    @Override
    public ImmersionMvpFragmentPresenter createPresenter() {
        return new ImmersionMvpFragmentPresenter(mContext);
    }

    @Override
    protected void onViewCreatedMvp() {
        mType = getArguments().getInt("key");
        ScLog.debug("ImmersionMvpFragment mType = " + mType + " onViewCreatedMvp");
        mTextView.setText("我是MvpFragment mType = " + mType);
    }

    @Override
    protected int setLayoutResId() {
        ScLog.debug("ImmersionMvpFragment mType = " + mType + " setLayoutResId");
        return R.layout.fragment_immersion_mvp;
    }

    @Override
    protected void lazyLoadData() {
        showToast("ImmersionMvpFragment mType = " + mType + " lazyLoadData");
    }
}

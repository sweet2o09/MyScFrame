package com.caihan.myscframe.ui.immersion;

import android.os.Bundle;
import android.widget.TextView;

import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScFragment;
import com.caihan.scframe.utils.log.ScLog;

import butterknife.BindView;

/**
 * @author caihan
 * @date 2018/5/11
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionFragment extends BaseScFragment {

    @BindView(R.id.text_view)
    TextView mTextView;


    private int mType;

    public static ImmersionFragment newInstance(int type) {
        ImmersionFragment fragment = new ImmersionFragment();
        Bundle args = new Bundle();
        args.putInt("key", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setImmersion() {
        ScLog.debug("ImmersionFragment mType = " + mType + " setImmersion");
        getImmersion().setImmersionTransparentDarkFont(mType % 2 == 1);
    }

    @Override
    protected void onViewCreated() {
        mType = getArguments().getInt("key");
        ScLog.debug("ImmersionFragment mType = " + mType + " onViewCreated");
        mTextView.setText("我是Fragment mType = " + mType);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_immersion;
    }

    @Override
    protected void lazyLoadData() {
        showToast("ImmersionFragment mType = " + mType + " lazyLoadData");
    }
}

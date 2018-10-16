package com.caihan.myscframe.ui.product;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.caihan.myscframe.BuildConfig;
import com.caihan.myscframe.R;
import com.caihan.myscframe.base.BaseScActivity;
import com.caihan.scframe.utils.MetaDataUtils;

import butterknife.BindView;

/**
 * 动态打包参数
 *
 * @author caihan
 * @date 2018/7/9
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ProductActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.product_tv)
    TextView mProductTv;


    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_product;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("动态打包参数");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mProductTv.setText(new SpanUtils()
                .appendLine("applicationId = " + AppUtils.getAppPackageName())
                .appendLine("versionCode = " + AppUtils.getAppVersionCode())
                .appendLine("versionName = " + AppUtils.getAppVersionName())
                .appendLine("API_SETTING = " + BuildConfig.API_SETTING)
                .appendLine("H5_SETTING = " + BuildConfig.H5_SETTING)
                .appendLine("DEBUG_RELEASE = " + BuildConfig.DEBUG_RELEASE)
                .appendLine("business_id = " + getString(R.string.business_id))
                .appendLine("WEICHAT_APPKEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"WEICHAT_APPKEY"))
                .appendLine("WEICHAT_SECRET = " + MetaDataUtils.getMetaDataFromApp(mContext,"WEICHAT_SECRET"))
                .appendLine("QQ_APPKEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"QQ_APPKEY"))
                .appendLine("QZ_APPID = " + MetaDataUtils.getMetaDataFromApp(mContext,"QZ_APPID"))
                .appendLine("XL_APPKEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"XL_APPKEY"))
                .appendLine("XL_SECRET = " + MetaDataUtils.getMetaDataFromApp(mContext,"XL_SECRET"))
                .appendLine("OPEN_IM_APPKEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"OPEN_IM_APPKEY"))
                .appendLine("OPEN_IM_TARGET_APPKEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"OPEN_IM_TARGET_APPKEY"))
                .appendLine("OPEN_SECRET = " + MetaDataUtils.getMetaDataFromApp(mContext,"OPEN_SECRET"))
                .appendLine("OPEN_GUIDE_IMAGE_NUM = " + MetaDataUtils.getMetaDataFromApp(mContext,"OPEN_GUIDE_IMAGE_NUM"))
                .appendLine("UMENG_APPKEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"UMENG_APPKEY"))
                .appendLine("UMENG_MESSAGE_SECRET = " + MetaDataUtils.getMetaDataFromApp(mContext,"UMENG_MESSAGE_SECRET"))
                .appendLine("ALIAS_TYPE = " + MetaDataUtils.getMetaDataFromApp(mContext,"ALIAS_TYPE"))
                .appendLine("GAODE_KEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"GAODE_KEY"))
                .appendLine("BAIDU_MOB_AD_KEY = " + MetaDataUtils.getMetaDataFromApp(mContext,"BAIDU_MOB_AD_KEY"))
                .appendLine("app_name = " + getString(R.string.app_name))
                .create());
    }
}

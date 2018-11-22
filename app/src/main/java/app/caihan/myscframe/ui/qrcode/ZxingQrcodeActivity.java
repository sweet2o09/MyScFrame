package app.caihan.myscframe.ui.qrcode;

import android.os.Vibrator;
import android.support.constraint.Guideline;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caihan.scframe.permission.PermissionGroup;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.caihan.scframe.utils.ScOutdatedUtils;
import com.caihan.scframe.utils.log.ScLog;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCheckedTextView;

import java.util.concurrent.TimeUnit;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 二维码扫描
 *
 * @author caihan
 * @date 2018/4/22
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ZxingQrcodeActivity
        extends BaseScActivity
        implements OnPermissionListener, QRCodeView.Delegate {

    @BindView(R.id.zxing_view)
    ZXingView mZxingView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_tv)
    TextView mToolbarRightTv;
    @BindView(R.id.toolbar_right_layout)
    LinearLayout mToolbarRightLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.album_tv)
    TextView mAlbumTv;
    @BindView(R.id.open_flash_light_ctv)
    CheckedTextView mOpenFlashLightCtv;
    @BindView(R.id.guideline2)
    Guideline mGuideline2;

    private boolean isInitZxingView = false;//是否初始化扫描工具
    private int mScanDelay = 2000;//暂停2000ms后继续扫描


    @Override
    protected void onStart() {
        super.onStart();
        ScLog.debug("onStart");
        //初始化后才调用,处理首次进入权限申请逻辑
        if (isInitZxingView) {
            initZxingView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScLog.debug("onResume");
        if (isInitZxingView) {
            mZxingView.startSpotDelay(mScanDelay);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScLog.debug("onPause");
        changeLight(false);
        mZxingView.stopSpot();
    }

    @Override
    protected void onStop() {
        mZxingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxingView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, false);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_zxing_qrcode;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("扫描二维码");
        mToolbarTitle.setTextColor(ScOutdatedUtils.getColor(R.color.black));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mZxingView.setDelegate(this);
        changeLight(false);
        requestPermission(this, PermissionGroup.CAMERA, PermissionGroup.STORAGE);

        addDisposable(RxView.clicks(mOpenFlashLightCtv)
                .throttleFirst(1, TimeUnit.SECONDS)
                .map(new Function<Object, Boolean>() {
                    @Override
                    public Boolean apply(Object o) throws Exception {
                        return !mOpenFlashLightCtv.isChecked();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RxCheckedTextView.check(mOpenFlashLightCtv).accept(aBoolean);
                        changeLight(aBoolean);
                    }
                }));

        addDisposable(RxView.clicks(mAlbumTv)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        chooseQrcdeFromGallery();
                    }
                }));

        addDisposable(RxView.clicks(mToolbarRightTv)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mZxingView.getScanBoxView().setTipText("你好TipText");
                    }
                }));
    }

    @Override
    public void onPermissionSuccessful() {
        showToast("动态权限申请成功");
        initZxingView();
    }

    @Override
    public void onPermissionFailure() {
        showToast("动态权限申请失败");
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        ScLog.debug("扫码成功 内容 : " + result);
        showToast("扫码成功 内容 : " + result);
        mZxingView.startSpotDelay(mScanDelay);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showToast("扫码失败");
    }

    /**
     * 初始化
     */
    private void initZxingView() {
        ScLog.debug("initZxingView");
        //刚开始ZxingView要处于GONE状态,等权限申请回来后再设置成VISIBLE,避免黑屏
        if (mZxingView.getVisibility() == View.GONE) {
            mZxingView.setVisibility(View.VISIBLE);
        }
        mZxingView.startCamera();
        mZxingView.showScanRect();
        mZxingView.startSpotDelay(mScanDelay);
        isInitZxingView = true;
        showToast("初始化成功");
    }

    /**
     * 震动效果
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 从相册中选图片
     */
    private void chooseQrcdeFromGallery() {
        showToast("打开相册");
    }

    /**
     * @param isOpen 需要变更的状态
     */
    private void changeLight(boolean isOpen) {
        if (isOpen) {
            mZxingView.openFlashlight();
        } else {
            mZxingView.closeFlashlight();
        }
        mOpenFlashLightCtv.setCompoundDrawablesWithIntrinsicBounds(
                null, getResources().getDrawable(isOpen ?
                        R.drawable.ic_flash_light_w : R.drawable.ic_flash_light_b),
                null, null);
    }
}

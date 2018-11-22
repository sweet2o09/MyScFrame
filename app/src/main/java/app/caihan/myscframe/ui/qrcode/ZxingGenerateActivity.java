package app.caihan.myscframe.ui.qrcode;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.caihan.scframe.rxjava.RxSchedulers;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * 生成和识别二维码
 *
 * @author caihan
 * @date 2018/5/1
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ZxingGenerateActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.image_view_1)
    ImageView mImageView1;
    @BindView(R.id.decode_tv_1)
    Button mDecodeTv1;
    @BindView(R.id.image_view_2)
    ImageView mImageView2;
    @BindView(R.id.decode_tv_2)
    Button mDecodeTv2;
    @BindView(R.id.image_view_3)
    ImageView mImageView3;
    @BindView(R.id.decode_tv_3)
    Button mDecodeTv3;

    ZxingCodeEncoder mZxingCodeEncoder;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_zxing_generate;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("识别二维码");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mZxingCodeEncoder = new ZxingCodeEncoder();

        mZxingCodeEncoder.setQrParameter(SizeUtils.dp2px(150), SizeUtils.dp2px(150))
                .createQRImage("https://www.baidu.com", mImageView1);

        mZxingCodeEncoder.setBarParameter(SizeUtils.dp2px(200), SizeUtils.dp2px(100))
                .createBarCode("9781234567897", mImageView2);

        Bitmap logoBitmap = ImageUtils.getBitmap(R.drawable.ic_zxing_logo);
        mZxingCodeEncoder.setQrParameter(SizeUtils.dp2px(150), SizeUtils.dp2px(150))
                .createQRImage("https://github.com/sweet2o09/MyScFrame", mImageView3, logoBitmap);
    }

    @OnClick({R.id.decode_tv_1, R.id.decode_tv_2, R.id.decode_tv_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.decode_tv_1:
                mImageView1.setDrawingCacheEnabled(true);
                Bitmap bitmap = mImageView1.getDrawingCache();
                decode(bitmap, "解析二维码失败");
                break;
            case R.id.decode_tv_2:
                mImageView2.setDrawingCacheEnabled(true);
                Bitmap bitmap2 = mImageView2.getDrawingCache();
                decode(bitmap2, "解析条形码失败");
                break;
            case R.id.decode_tv_3:
                mImageView3.setDrawingCacheEnabled(true);
                Bitmap bitmap3 = mImageView3.getDrawingCache();
                decode(bitmap3, null);
                break;
        }
    }

    private void decode(final Bitmap bitmap, final String errorTip) {
        addDisposable(Observable.create(
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        String result = QRCodeDecoder.syncDecodeQRCode(bitmap);
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                })
                .compose(RxSchedulers.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) throws Exception {
                        showToast(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (StringUtils.isEmpty(errorTip)) {
                            showToast("解析失败 " + throwable.getMessage().toString());
                        } else {
                            showToast(errorTip);
                        }
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        mZxingCodeEncoder = null;
        super.onDestroy();
    }
}

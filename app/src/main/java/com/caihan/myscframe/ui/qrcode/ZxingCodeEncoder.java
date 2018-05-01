package com.caihan.myscframe.ui.qrcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

import com.caihan.scframe.rxjava.RxSchedulers;
import com.caihan.scframe.utils.log.ScLog;
import com.caihan.scframe.utils.toast.ScToast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * 二维码与条码创建工具类
 *
 * @author caihan
 * @date 2018/5/1
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ZxingCodeEncoder {

    private int QR_WIDTH = 250;
    private int QR_HEIGHT = 250;
    private int BAR_WIDTH = 500;
    private int BAR_HEIGHT = 200;
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xffffffff;
    private IEncodeHint mEncodeHint;

    public ZxingCodeEncoder() {
    }

    public ZxingCodeEncoder setEncodeHint(IEncodeHint encodeHint) {
        mEncodeHint = encodeHint;
        return this;
    }

    public ZxingCodeEncoder setQrParameter(int widthPx, int heightPx) {
        QR_WIDTH = widthPx;
        QR_HEIGHT = heightPx;
        return this;
    }

    public ZxingCodeEncoder setBarParameter(int widthPx, int heightPx) {
        BAR_WIDTH = widthPx;
        BAR_HEIGHT = heightPx;
        return this;
    }

    public IEncodeHint getEncodeHint() {
        if (mEncodeHint == null) {
            mEncodeHint = new EncodeHint();
        }
        return mEncodeHint;
    }

    /**
     * 生成二维码图片
     *
     * @param url
     * @param imageView
     */
    public void createQRImage(final String url, final ImageView imageView) {
        createQRImage(url, imageView, null);
    }

    /**
     * 生成二维码图片
     *
     * @param url 要转换的地址或字符串,可以是中文
     */
    public void createQRImage(final String url, final ImageView imageView, final Bitmap logo) {
        //判断URL合法性
        if (url == null || "".equals(url) || url.length() < 1) {
            return;
        }

        Observable.create(
                new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                        //图像数据转换，使用了矩阵转换
                        BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                                BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, getEncodeHint().getEncodeHintMap());
                        emitter.onNext(BitMatrixToBitmap(bitMatrix, logo));
                        emitter.onComplete();
                    }
                })
                .compose(RxSchedulers.<Bitmap>io_main())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        imageView.setImageBitmap(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ScToast.showToast("生成二维码失败");
                    }
                });
    }

    /**
     * 生成条形码,实际使用时要初始化sweepIV, 不然会报空指针错误
     *
     * @param url 要转换的地址或字符串,可以是中文
     */
    public void createBarCode(final String url, final ImageView imageView) {
        //判断URL合法性
        if (url == null || "".equals(url) || url.length() < 1) {
            return;
        }
        Observable.create(
                new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
                        BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
                                BarcodeFormat.CODE_128, BAR_WIDTH, BAR_HEIGHT, getEncodeHint().getEncodeHintMap());
                        emitter.onNext(BitMatrixToBitmap(bitMatrix));
                        emitter.onComplete();
                    }
                })
                .compose(RxSchedulers.<Bitmap>io_main())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        imageView.setImageBitmap(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ScToast.showToast("生成条形码失败");
                    }
                });
    }

    private Bitmap BitMatrixToBitmap(BitMatrix matrix) {
        return BitMatrixToBitmap(matrix, null);
    }

    /**
     * BitMatrix转换成Bitmap
     *
     * @param matrix
     * @return
     */
    private Bitmap BitMatrixToBitmap(BitMatrix matrix, final Bitmap logo) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        //下面这里按照二维码的算法，逐个生成二维码的图片，
        //两个for循环是图片横列扫描的结果
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[offset + x] = BLACK; //上面图案的颜色
                } else {
                    pixels[offset + x] = WHITE;//底色
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        ScLog.debug("width:" + bitmap.getWidth() + " height:" + bitmap.getHeight());
        if (logo != null) {
            return addLogoToQRCode(bitmap, logo);
        }
        return bitmap;
    }

    /**
     * 添加logo到二维码图片上
     *
     * @param src
     * @param logo
     * @return
     */
    private static Bitmap addLogoToQRCode(Bitmap src, Bitmap logo) {
        if (src == null || logo == null) {
            return src;
        }

        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
        }
        return bitmap;
    }
}

package app.caihan.myscframe.ui.qrcode;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author caihan
 * @date 2018/5/1
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class EncodeHint implements IEncodeHint{

    /**
     * 获得设置好的编码参数
     *
     * @return 编码参数
     */
    @Override
    public Map<EncodeHintType, Object> getEncodeHintMap() {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        //设置编码为utf-8
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置QR二维码的纠错级别——这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        hints.put(EncodeHintType.MARGIN, 1); // default is 4,二维码四周留白
//        hints.put(EncodeHintType.MAX_SIZE, 350);
//        hints.put(EncodeHintType.MIN_SIZE, 100);
        return hints;
    }
}

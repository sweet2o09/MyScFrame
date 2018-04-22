package com.caihan.scframe.utils.text;

import android.text.TextUtils;

/**
 * emoji表情判断
 *
 * @author caihan
 * @date 2018/3/5
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class EmojiUtils {

    /**
     * 是否包含Emoji表情
     *
     * @param str
     * @return
     */
    public static boolean containsEmoji(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            int cp = str.codePointAt(i);
            if (isEmojiCharacter(cp)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否是Emoji表情
     *
     * @param first
     * @return
     */
    private static boolean isEmojiCharacter(int first) {

       /* 1F30D - 1F567
        1F600 - 1F636
        24C2 - 1F251
        1F680 - 1F6C0
        2702 - 27B0
        1F601 - 1F64F*/
        return !
                ((first == 0x0) ||
                        (first == 0x9) ||
                        (first == 0xA) ||
                        (first == 0xD) ||
                        ((first >= 0x20) && (first <= 0xD7FF)) ||
                        ((first >= 0xE000) && (first <= 0xFFFD)) ||
                        ((first >= 0x10000))) ||
                (first == 0xa9 || first == 0xae || first == 0x2122 ||
                        first == 0x3030 || (first >= 0x25b6 && first <= 0x27bf) ||
                        first == 0x2328 || (first >= 0x23e9 && first <= 0x23fa))
                || ((first >= 0x1F000 && first <= 0x1FFFF))
                || ((first >= 0x2702) && (first <= 0x27B0))
                || ((first >= 0x1F601) && (first <= 0x1F64F));
    }


    /**
     * 过滤Emoji表情
     *
     * @param str
     * @return
     */
    public static String filterEmoji(String str) {
        if (!containsEmoji(str)) {
            return str;
        }
        StringBuilder buf = null;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char codePoint = str.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(str.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return "";
        } else {
            return buf.toString();
        }
    }
}

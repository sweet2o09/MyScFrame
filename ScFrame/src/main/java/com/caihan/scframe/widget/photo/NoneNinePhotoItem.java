package com.caihan.scframe.widget.photo;

/**
 * 九宫格空Item
 *
 * @author caihan
 * @date 2019/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NoneNinePhotoItem extends NinePhotoItem {

    public NoneNinePhotoItem() {
        setNoneNinePhotoItem(true);
    }

    @Override
    public String getNinePhotoImageUrl() {
        return "";
    }
}

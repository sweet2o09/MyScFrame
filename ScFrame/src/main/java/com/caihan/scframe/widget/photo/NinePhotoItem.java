package com.caihan.scframe.widget.photo;

import com.caihan.scframe.utils.IUnProguard;

/**
 * 九宫格Item基类
 *
 * @author caihan
 * @date 2019/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public abstract class NinePhotoItem implements INinePhoto, IUnProguard {

    private boolean isNoneNinePhotoItem = false;//是否是空

    public void setNoneNinePhotoItem(boolean noneNinePhotoItem) {
        isNoneNinePhotoItem = noneNinePhotoItem;
    }

    public boolean isNoneNinePhotoItem() {
        return isNoneNinePhotoItem;
    }
}

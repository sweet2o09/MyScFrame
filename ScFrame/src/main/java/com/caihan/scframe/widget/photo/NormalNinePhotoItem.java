package com.caihan.scframe.widget.photo;

/**
 * 九宫格正常Item
 *
 * @author caihan
 * @date 2019/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NormalNinePhotoItem extends NinePhotoItem {
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getNinePhotoImageUrl() {
        return url;
    }
}

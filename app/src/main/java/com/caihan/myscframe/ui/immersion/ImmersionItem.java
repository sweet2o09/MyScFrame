package com.caihan.myscframe.ui.immersion;

/**
 * @author caihan
 * @date 2018/5/10
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ImmersionItem {

    private static final String TAG = "ImmersionItem";
    private String title;
    private Class<?> activity;
    private int imageResource;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }
}

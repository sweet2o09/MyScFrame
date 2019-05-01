package com.caihan.scframe.widget.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caihan
 * @date 2019/4/16
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class LineDes {
    //行高度以最高的view为基准
    int rowsMaxHeight;
    //
    int rowsMaxWidth;
    //一行收集到的item
    List<View> views = new ArrayList<>();

    //用完了该行的信息后进行清除的操作
    public void clearLineDes() {
        if (views.size() > 0) {
            views.clear();
        }
        rowsMaxHeight = 0;
    }
}

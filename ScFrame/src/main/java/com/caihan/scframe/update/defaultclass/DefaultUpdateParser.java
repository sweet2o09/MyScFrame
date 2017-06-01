package com.caihan.scframe.update.defaultclass;

import com.caihan.scframe.update.builder.IUpdateParser;
import com.caihan.scframe.update.data.ScUpdateInfo;

/**
 * Created by caihan on 2017/5/22.
 * 默认的检测版本回调解析
 */
public class DefaultUpdateParser implements IUpdateParser {
    @Override
    public ScUpdateInfo parse(String source) throws Exception {
        return ScUpdateInfo.parse(source);
    }
}

package com.caihan.scframe.update.builder;

import com.caihan.scframe.update.data.ScUpdateInfo;

public interface IUpdateParser {
    ScUpdateInfo parse(String source) throws Exception;
}
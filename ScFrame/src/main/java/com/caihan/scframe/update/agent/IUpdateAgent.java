package com.caihan.scframe.update.agent;

import com.caihan.scframe.update.data.ScUpdateInfo;

public interface IUpdateAgent {
    ScUpdateInfo getInfo();

    void update();

    void ignore();
}
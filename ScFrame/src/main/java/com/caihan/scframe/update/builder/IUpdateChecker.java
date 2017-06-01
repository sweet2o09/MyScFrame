package com.caihan.scframe.update.builder;

import com.caihan.scframe.update.agent.ICheckAgent;

public interface IUpdateChecker {
    void check(ICheckAgent agent, String url);
}
package com.caihan.scframe.update.builder;

import com.caihan.scframe.update.agent.IUpdateAgent;

/**
 * Created by caihan on 2017/5/23.
 * 更新版本对话框接口
 */
public interface IUpdatePrompter {
    void prompt(IUpdateAgent agent);
}
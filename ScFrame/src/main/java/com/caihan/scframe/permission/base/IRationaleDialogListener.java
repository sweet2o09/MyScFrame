package com.caihan.scframe.permission.base;

import com.yanzhenjie.permission.Rationale;

/**
 * rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；<br/>
 * 这样避免用户勾选不再提示，导致以后无法申请权限。<br/>
 * 在PositiveButton点击事件下调用{@link Rationale#resume()}<br/>
 * 在NegativeButton点击事件下调用{@link Rationale#cancel()}<br/>
 *
 * @author caihan
 * @date 2018/1/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface IRationaleDialogListener {

    /**
     * 自定义RationaleDialog
     *
     * @param requestCode
     * @param rationale
     */
    void showRationaleDialog(int requestCode, Rationale rationale);
}

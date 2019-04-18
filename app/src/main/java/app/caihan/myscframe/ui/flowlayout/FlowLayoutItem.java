package app.caihan.myscframe.ui.flowlayout;

import com.caihan.scframe.widget.flowlayout.TagFlowItemEntity;

/**
 * @author caihan
 * @date 2019/4/16
 * @e-mail 2730045482@qq.com
 * 维护者
 */
public class FlowLayoutItem implements TagFlowItemEntity {

    private String msg;
    private int type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getTagFlowItemType() {
        return type;
    }
}

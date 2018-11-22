package app.caihan.myscframe.ui.switchbtn;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.scframe.widget.switchview.OnToggleListener;
import com.caihan.scframe.widget.switchview.WeChatSwitchBtn;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class WeChatSwitchBtnActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.switch_btn)
    WeChatSwitchBtn mSwitchBtn;
    @BindView(R.id.show_dialog_btn)
    Button mShowDialogBtn;

    AlertDialog mAlertDialog;


    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_wechat_switch_btn;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("WeChatSwitchBtn");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwitchBtn.setOnToggleListener(new OnToggleListener() {
            @Override
            public void onSwitchChangeListener(boolean switchState) {
                showToast("当前状态 :: " + switchState);
            }
        });
    }

    @OnClick(R.id.show_dialog_btn)
    public void onViewClicked() {
        showDialog();
    }

    private void showDialog() {
        if (mAlertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View view = View.inflate(mContext, R.layout.wechat_switch_btn_dialog, null);
            builder.setTitle("WeChatSwitchBtnDialog")
                    .setView(view)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null);
            WeChatSwitchBtn weChatSwitchBtn = view.findViewById(R.id.switch_btn);
            //取消或确定按钮监听事件处理
            mAlertDialog = builder.create();
            weChatSwitchBtn.setChecked(true);
        }
        mAlertDialog.show();
    }
}

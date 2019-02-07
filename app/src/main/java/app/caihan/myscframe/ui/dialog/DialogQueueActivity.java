package app.caihan.myscframe.ui.dialog;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.caihan.scframe.dialog.DefaultDialog;
import com.caihan.scframe.utils.dialog.DialogQueueUtils;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 弹窗队列
 *
 * @author caihan
 * @date 2019/2/7
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class DialogQueueActivity extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.show_dialog_btn)
    Button mShowDialogBtn;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_dialog_queue;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("弹窗队列");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.show_dialog_btn)
    public void onViewClicked() {
        MaterialDialog dialog1 = DefaultDialog.getInstance()
                .getDialog(this)
                .content("我是第一个弹窗")
                .build();
        MaterialDialog dialog2 = DefaultDialog.getInstance()
                .getDialog(this)
                .content("我是第二个弹窗")
                .build();
        MaterialDialog dialog3 = DefaultDialog.getInstance()
                .getDialog(this)
                .content("我是第三个弹窗")
                .build();
        MaterialDialog dialog4 = DefaultDialog.getInstance()
                .getDialog(this)
                .content("我是第四个弹窗")
                .build();
        MaterialDialog dialog5 = DefaultDialog.getInstance()
                .getDialog(this)
                .content("我是第五个弹窗")
                .build();
        DialogQueueUtils.getInstance().addDialog(dialog1);
        DialogQueueUtils.getInstance().addDialog(dialog2);
        DialogQueueUtils.getInstance().addDialog(dialog3);
        DialogQueueUtils.getInstance().addDialog(dialog4);
        DialogQueueUtils.getInstance().addDialog(dialog5);
        DialogQueueUtils.getInstance().show();
    }
}

package app.caihan.myscframe.ui.qrcode;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.scframe.config.IntentValue;
import com.caihan.scframe.permission.PermissionGroup;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.caihan.scframe.utils.ScAppUtils;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二维码扫描Demo
 *
 * @author caihan
 * @date 2018/4/28
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ZxingQrcodeDemoActivity
        extends BaseScActivity
        implements OnPermissionListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.open_permission)
    Button mOpenPermission;
    @BindView(R.id.closed_permission)
    Button mClosedPermission;
    @BindView(R.id.go_scan_activity)
    Button mGoScanActivity;
    @BindView(R.id.go_generate_activity)
    Button mGoGenerateActivity;


    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_zxing_qrcode_demo;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        mToolbarTitle.setText("扫描二维码Demo");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.open_permission, R.id.closed_permission, R.id.go_scan_activity, R.id.go_generate_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_permission:
                requestPermission(this, PermissionGroup.CAMERA, PermissionGroup.STORAGE);
                break;
            case R.id.closed_permission:
                ScAppUtils.getAppDetailsSettings(mContext, IntentValue.REQUEST_CODE_APP_DETAILS_SETTINGS);
                break;
            case R.id.go_scan_activity:
                Intent intent = new Intent(mContext, ZxingQrcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.go_generate_activity:
                Intent intent2 = new Intent(mContext, ZxingGenerateActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionSuccessful() {
        showToast("动态权限申请成功");
    }

    @Override
    public void onPermissionFailure() {
        showToast("动态权限申请失败");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentValue.REQUEST_CODE_APP_DETAILS_SETTINGS) {
            showToast("系统设置跳转回来了");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

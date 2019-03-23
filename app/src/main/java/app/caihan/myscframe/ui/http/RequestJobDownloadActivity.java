package app.caihan.myscframe.ui.http;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.FileUtils;
import com.caihan.scframe.api.ScHttpManager;
import com.caihan.scframe.api.callback.RequestDownloadCallBack;
import com.caihan.scframe.permission.PermissionGroup;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.caihan.scframe.utils.file.ScFileOptionsUtils;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScActivity;
import app.caihan.myscframe.utils.FileOptionsUtils;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


/**
 * 下载文件
 *
 * @author caihan
 * @date 2019/3/21
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class RequestJobDownloadActivity
        extends BaseScActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.download_btn)
    Button mDownloadBtn;

    private Disposable mDisposable;
    private MaterialDialog mRequestLoading;
    private FileOptionsUtils mFileOptionsUtils;

    private String mDownLoadUrl;
    private String mSavePath;
    private String mSaveName;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_request_job_download;
    }

    @Override
    protected void onCreate() {
        setImmersion();
        setBaseToolbarLayout(mToolbar,"下载");
    }

    @OnClick(R.id.download_btn)
    public void onViewClicked() {
        requestPermission(new OnPermissionListener() {
            @Override
            public void onPermissionSuccessful() {
                downLoad();
            }

            @Override
            public void onPermissionFailure() {
            }
        }, PermissionGroup.STORAGE);
    }

    private void downLoad() {
        if (mFileOptionsUtils == null) {
            mFileOptionsUtils = new FileOptionsUtils(mContext, "ScFrameDemoFile/downLoad");
            mDownLoadUrl = "http://common.ushopn7.com/app/ldy/635/%E8%9C%82%E4%BC%98%E7%94%9F%E6%B4%BB.apk";
            mSavePath = mFileOptionsUtils.getRootFile().getAbsolutePath();
            mSaveName = "蜂优生活.apk";//FileUtils.getFileName(downLoadUrl);
        }
        if (mRequestLoading == null) {
            mRequestLoading = new MaterialDialog.Builder(mContext)
                    .progress(false, 100)
                    .cancelable(false)
                    .title("下载文件")
                    .content("正在下载...")
                    .negativeText("取消")
                    .negativeColorRes(R.color.scframe_dialog_negative_color)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ScHttpManager.cancelSubscription(mDisposable);
                            FileUtils.delete(mFileOptionsUtils.getFilePathS("蜂优生活", ScFileOptionsUtils.Suffix.APK));
                        }
                    })
                    .build();
        }
        mDisposable = ScHttpManager.downLoad(mDownLoadUrl, mSavePath, mSaveName,
                new RequestDownloadCallBack() {

                    @Override
                    public void update(long progress, boolean done) {
                        mRequestLoading.setProgress((int) progress);
                        if (done) {
                            mRequestLoading.setContent("下载完成");
                        }
                    }

                    @Override
                    public void onStart() {
                        mRequestLoading.show();
                    }

                    @Override
                    public void onComplete(String path) {
                        showToast("文件保存路径：" + path);
                        mRequestLoading.dismiss();
                        mDisposable = null;
                    }

                    @Override
                    public void onError(String errorMsg) {
                        showToast(errorMsg);
                        mRequestLoading.dismiss();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
            FileUtils.delete(mFileOptionsUtils.getFilePathS("蜂优生活", ScFileOptionsUtils.Suffix.APK));
        }
        super.onDestroy();
    }
}

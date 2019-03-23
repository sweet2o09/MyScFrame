package app.caihan.myscframe.ui.traverse;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.scframe.permission.PermissionGroup;
import com.caihan.scframe.permission.base.OnPermissionListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.ArrayList;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScMvpActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class TraverseSDcardActivity
        extends BaseScMvpActivity<TraverseSDContract.View, TraverseSDPresenter>
        implements TraverseSDContract.View {

    public static final int STATUS_WAITING = 0;//等待
    public static final int STATUS_WORKING = 1;//进行中
    public static final int STATUS_ENDING = 2;//结束

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.scan_status_tv)
    TextView mScanStatusTv;
    @BindView(R.id.scan_btn)
    Button mScanBtn;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mStatus = STATUS_WAITING;

    private TraverseSDcardAdapter mAdapter;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, false);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_traverse_sdcard;
    }

    @NonNull
    @Override
    public TraverseSDPresenter createPresenter() {
        return new TraverseSDPresenter(mContext);
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        mToolbarTitle.setText("扫描SD卡");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        changeStatusTv();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new TraverseSDcardAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @OnClick(R.id.scan_btn)
    public void onViewClicked() {
        requestPermission(new OnPermissionListener() {
            @Override
            public void onPermissionSuccessful() {
                onClick();
            }

            @Override
            public void onPermissionFailure() {

            }
        }, PermissionGroup.STORAGE);
    }

    private void onClick(){
        mStatus = STATUS_WORKING;
        changeStatusTv();
        String path = Environment.getExternalStorageDirectory() + "/AppDown/laidianyi/android/";
        getPresenter().find(path);
    }

    @Override
    public void getFile(ArrayList<File> delList) {
        mStatus = STATUS_ENDING;
        changeStatusTv();
        mAdapter.setNewData(delList);
    }

    private void changeStatusTv() {
        switch (mStatus) {
            case STATUS_WAITING:
                mScanStatusTv.setText("准备扫描...");
                break;
            case STATUS_WORKING:
                mScanStatusTv.setText("正在扫描...");
                break;
            case STATUS_ENDING:
                mScanStatusTv.setText("扫描结束");
                break;
            default:
                break;
        }
    }

    public class TraverseSDcardAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

        public TraverseSDcardAdapter() {
            super(R.layout.item_traverse_sdcard);
        }

        @Override
        protected void convert(BaseViewHolder helper, File item) {
            helper.setText(R.id.path_tv, item.getAbsolutePath());
        }
    }
}

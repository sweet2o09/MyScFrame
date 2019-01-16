package app.caihan.myscframe.ui.ninephoto;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.caihan.scframe.widget.photo.NinePhotoAddLayout;

import app.caihan.myscframe.R;
import app.caihan.myscframe.base.BaseScMvpActivity;
import butterknife.BindView;

/**
 * 九宫格添加图片
 *
 * @author caihan
 * @date 2019/1/15
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NinePhotoAddActivity
        extends BaseScMvpActivity<NinePhotoAddContract.View, NinePhotoAddPresenter>
        implements NinePhotoAddContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nine_photo_add_layout)
    NinePhotoAddLayout mNinePhotoAddLayout;

    @Override
    public void setImmersion() {
        getImmersion().setImmersionDarkFont(mToolbar, true);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_nine_photo_add;
    }

    @NonNull
    @Override
    public NinePhotoAddPresenter createPresenter() {
        return new NinePhotoAddPresenter(mContext);
    }

    @Override
    protected void onCreateMvp() {
        setImmersion();
        mToolbarTitle.setText("九宫格添加图片");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

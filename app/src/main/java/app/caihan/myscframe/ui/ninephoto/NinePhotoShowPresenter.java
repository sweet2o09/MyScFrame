package app.caihan.myscframe.ui.ninephoto;

import android.content.Context;

import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;
import com.caihan.scframe.rxjava.RxSchedulers;
import com.caihan.scframe.rxjava.RxSubscriber;
import com.caihan.scframe.widget.photo.NormalNinePhotoItem;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author caihan
 * @date 2019/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NinePhotoShowPresenter extends MvpBasePresenter<NinePhotoShowContract.View> {

    private static final String[] HEADER_IMAGE_URL = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725356786&di=b0514fc12e947f224578f7f881190f4f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201801%2F12%2F20180112185135_8ckVa.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725368059&di=a1d49cedc5e065c611af60aa8169c876&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F18%2F20180418103346_nilns.thumb.700_0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3423387854,2582964374&fm=26&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725368059&di=a1d49cedc5e065c611af60aa8169c876&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F18%2F20180418103346_nilns.thumb.700_0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725356786&di=b0514fc12e947f224578f7f881190f4f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201801%2F12%2F20180112185135_8ckVa.jpeg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3423387854,2582964374&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3423387854,2582964374&fm=26&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725368059&di=a1d49cedc5e065c611af60aa8169c876&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F18%2F20180418103346_nilns.thumb.700_0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725368059&di=a1d49cedc5e065c611af60aa8169c876&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F18%2F20180418103346_nilns.thumb.700_0.jpg",
    };

    private static final String[] IMAGE_URL = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724835470&di=ab204e0ef0220ea64dbcfa3ec217a465&imgtype=0&src=http%3A%2F%2Fandroid-screenimgs.25pp.com%2F87%2F312407_137894815202.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725040245&di=c0423f633ae178cc56642b59c9528c90&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F35a85edf8db1cb13b43c9eabd654564e92584b35.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724819404&di=5439eb3a2f1d91e1c55261b875e6bb33&imgtype=0&src=http%3A%2F%2Fclubimg.dbankcdn.com%2Fdata%2Fattachment%2Fforum%2F201411%2F18%2F163244ds6zl26zxqxax04x.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724795769&di=4d7c0d847b3d248581a57ad7c027e554&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201407%2F06%2F20140706211323_2SBXv.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724889240&di=28c23c01aceda3a1f9d0d0e9b9a0c5e6&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F3725cde1a652ccbe5b6c0964aaae069154aa9759.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724889236&di=beb334ce1770080b152fe5d6983a2ed6&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2Fdd0bf5cac4d5e55951b5264d06bbcb1893b2fa6b.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724835470&di=ab204e0ef0220ea64dbcfa3ec217a465&imgtype=0&src=http%3A%2F%2Fandroid-screenimgs.25pp.com%2F87%2F312407_137894815202.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549725040245&di=c0423f633ae178cc56642b59c9528c90&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F35a85edf8db1cb13b43c9eabd654564e92584b35.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549724819404&di=5439eb3a2f1d91e1c55261b875e6bb33&imgtype=0&src=http%3A%2F%2Fclubimg.dbankcdn.com%2Fdata%2Fattachment%2Fforum%2F201411%2F18%2F163244ds6zl26zxqxax04x.jpg",
    };

    public NinePhotoShowPresenter(Context context) {
        super(context);
        setPageSize(100);
    }

    public void getData(boolean isRefresh) {
        if (isRefresh) {
            resetPage();
        }
        Observable.create(new ObservableOnSubscribe<NinePhotoShowBean>() {
            @Override
            public void subscribe(ObservableEmitter<NinePhotoShowBean> emitter) throws Exception {
                NinePhotoShowBean bean = getLocalData();
                emitter.onNext(bean);
                emitter.onComplete();//第一次才有效,表示结束
            }
        }).compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<NinePhotoShowBean>(getView()) {
                    @Override
                    public void _onNext(NinePhotoShowBean ninePhotoShowBean) {
                        getView().getDataFinish(isRefresh, ninePhotoShowBean);
                    }

                    @Override
                    public void _onError(Throwable error) {
                        getView().getDataError();
                    }
                });
    }

    private NinePhotoShowBean getLocalData() {
        NinePhotoShowBean bean = new NinePhotoShowBean();
        bean.setTotal("9");
        ArrayList<NinePhotoShowItem> list = new ArrayList<>();
        NinePhotoShowItem item;
        ArrayList<NormalNinePhotoItem> images;
        NormalNinePhotoItem photoItem;
        for (int i = 1; i <= 9; i++) {
            item = new NinePhotoShowItem();
            item.setHeadImageUrl(HEADER_IMAGE_URL[i - 1]);
            item.setTitle("标题 : " + i);
            if (i == 5) {
                item.setContent("");
            } else {
                item.setContent(i + "张网络图片");
            }
            item.setShowAsLargeWhenOnlyOne("0");
            item.setShowTwoItemSpanCount("0");
            images = new ArrayList<>();
            for (int i1 = 0; i1 < i; i1++) {
                photoItem = new NormalNinePhotoItem();
                photoItem.setUrl(IMAGE_URL[i1]);
                images.add(photoItem);
            }
            item.setImageList(images);
            list.add(item);
        }

        for (int i = 1; i <= 9; i++) {
            item = new NinePhotoShowItem();
            item.setHeadImageUrl(HEADER_IMAGE_URL[i - 1]);
            item.setTitle("第二套标题 : " + i);
            if (i == 2 || i == 7) {
                item.setContent("");
            } else {
                item.setContent(i + "张网络图片");
            }
            item.setShowAsLargeWhenOnlyOne("1");
            item.setShowTwoItemSpanCount("1");
            images = new ArrayList<>();
            for (int i1 = 0; i1 < i; i1++) {
                photoItem = new NormalNinePhotoItem();
                photoItem.setUrl(IMAGE_URL[i1]);
                images.add(photoItem);
            }
            item.setImageList(images);
            list.add(item);
        }

        for (int i = 1; i <= 2; i++) {
            item = new NinePhotoShowItem();
            item.setHeadImageUrl(HEADER_IMAGE_URL[i - 1]);
            item.setTitle("第三套标题 : " + i);
            if (i == 2 || i == 7) {
                item.setContent("");
            } else {
                item.setContent(i + "张网络图片");
            }
            item.setShowAsLargeWhenOnlyOne("1");
            item.setShowTwoItemSpanCount("1");
            images = new ArrayList<>();
            if (i == 1) {
                photoItem = new NormalNinePhotoItem();
                photoItem.setUrl(IMAGE_URL[2]);
                images.add(photoItem);
            }
            item.setImageList(images);
            list.add(item);
        }
        bean.setList(list);
        return bean;
    }

    @Override
    public void destroy() {

    }
}
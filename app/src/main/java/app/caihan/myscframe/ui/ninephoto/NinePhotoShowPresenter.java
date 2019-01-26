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
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLqks3571rzukAP6ibA0fM3pK3FMUXNU532MZmxVhzYCNWlicCA8Horib5DLPrtTgIaABLH9tgmOibpBg/132",
            "http://qnimg.xingqiuxiuchang.cn/b77ecde8-8c9d-4a6b-ac47-4bb2d7ef1a17.jpg",
            "http://qnimg.xingqiuxiuchang.cn/92f1b6b6-71b7-4d58-979d-ed74bcd3f5c7.jpg",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLqks3571rzukAP6ibA0fM3pK3FMUXNU532MZmxVhzYCNWlicCA8Horib5DLPrtTgIaABLH9tgmOibpBg/132",
            "http://qnimg.xingqiuxiuchang.cn/b77ecde8-8c9d-4a6b-ac47-4bb2d7ef1a17.jpg",
            "http://qnimg.xingqiuxiuchang.cn/92f1b6b6-71b7-4d58-979d-ed74bcd3f5c7.jpg",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLqks3571rzukAP6ibA0fM3pK3FMUXNU532MZmxVhzYCNWlicCA8Horib5DLPrtTgIaABLH9tgmOibpBg/132",
            "http://qnimg.xingqiuxiuchang.cn/b77ecde8-8c9d-4a6b-ac47-4bb2d7ef1a17.jpg",
            "http://qnimg.xingqiuxiuchang.cn/92f1b6b6-71b7-4d58-979d-ed74bcd3f5c7.jpg",
    };

    private static final String[] IMAGE_URL = {
            "http://qnimg.xingqiuxiuchang.cn/2379d373-20d1-4cd9-8589-89667af08ebb.jpg",
            "http://qnimg.xingqiuxiuchang.cn/02645d2e-aaf2-485a-9bda-40c3ce5cfba2.jpg",
            "http://qnimg.xingqiuxiuchang.cn/927dc809-797a-4d23-95d2-1b774d38f71f.jpg",
            "http://qnimg.xingqiuxiuchang.cn/7c2b179a-2e1b-41ce-adc1-4aaaaba83693.jpg",
            "http://qnimg.xingqiuxiuchang.cn/2c62706b-ac84-4cb8-baa3-ffd69e0961e9.jpg",
            "http://qnimg.xingqiuxiuchang.cn/0dc5a76b-3238-4c25-9b9b-30d94ed19dae.jpg",
            "http://qnimg.xingqiuxiuchang.cn/2379d373-20d1-4cd9-8589-89667af08ebb.jpg",
            "http://qnimg.xingqiuxiuchang.cn/02645d2e-aaf2-485a-9bda-40c3ce5cfba2.jpg",
            "http://qnimg.xingqiuxiuchang.cn/927dc809-797a-4d23-95d2-1b774d38f71f.jpg",
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
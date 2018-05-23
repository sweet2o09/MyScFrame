package com.caihan.myscframe.ui.multistoreylist.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.caihan.myscframe.R;
import com.caihan.myscframe.ui.multistoreylist.DeliveryTypeAdapter;
import com.caihan.myscframe.ui.multistoreylist.request.DeliveryTypeBean;
import com.caihan.myscframe.ui.multistoreylist.request.DeliveryTypeItemBean;
import com.caihan.scframe.utils.text.BaseParser;
import com.caihan.scframe.utils.text.ListUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author caihan
 * @date 2018/5/19
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class ShopCartDeliveryView {

    @BindView(R.id.single_delivery_type_name_tv)
    TextView mSingleDeliveryTypeNameTv;//一个配送方式
    @BindView(R.id.delivery_type_rv)
    RecyclerView mDeliveryTypeRv;//多个配送方式
    @BindView(R.id.delivery_type_tips_tv)
    TextView mDeliveryTypeTipsTv;//配送提示语
    @BindView(R.id.xu_line)
    ImageView mXuLine;//送货地址上边虚线,与送货地址显示状态一致
    @BindView(R.id.delivery_address_tv)
    TextView mDeliveryAddressTv;//送货地址
    @BindView(R.id.color_line)
    ImageView mColorLine;//送货地址下边彩线,与送货地址显示状态一致
    @BindView(R.id.cart_no_goods_tips_tv)
    TextView mCartNoGoodsTipsTv;//购物车内无商品提示语
    @BindView(R.id.next_day_no_goods_ll)
    LinearLayout mNextDayNoGoodsLl;//次日达专属空界面
    @BindView(R.id.choose_delivery_address_tv)
    TextView mChooseDeliveryAddressTv;//次日达专属空界面,选择配送区域文字
    @BindView(R.id.delivery_type_container_layout)
    RelativeLayout mDeliveryTypeContainerLl;//配送布局根布局

    private Context mContext;
    private View mContentView;
    private Drawable mRightArrowDrawable;
    private Drawable mLeftLocationDrawable;
    private DeliveryTypeAdapter mDeliveryTypeAdapter;
    private int mDeliveryTypePosition = 0;//配送类型选中的position

    private DeliveryTypeBean mDeliveryTypeBean;//购物车配送方式数据
    private IDeliveryViewCallBack mCallBack;


    Unbinder mUnbinder;

    public interface IDeliveryViewCallBack {
        /**
         * 切换了配送方式,需要更新购物车
         *
         * @param deliveryTypeBean
         */
        void onDeliveryViewChangeType(DeliveryTypeBean deliveryTypeBean);

        /**
         * 只有快递配送模式,移除ShopCartDeliveryView布局
         */
        void onDeliveryisOnlyType1();

    }

    /**
     * 获取回调实例
     *
     * @return
     */
    public IDeliveryViewCallBack getCallBack() {
        if (mCallBack == null) {
            throw new NullPointerException("IDeliveryViewCallBack为空");
        }
        return mCallBack;
    }

    public ShopCartDeliveryView(Context context, IDeliveryViewCallBack deliveryViewCallBack) {
        mCallBack = deliveryViewCallBack;
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.view_shop_cart_delivery_layout, null);
        mUnbinder = ButterKnife.bind(this, mContentView);

        setDefaultUI();
        mRightArrowDrawable = mContext.getResources().getDrawable(R.drawable.right_arrow);
        mLeftLocationDrawable = mContext.getResources().getDrawable(R.drawable.ic_location_empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDeliveryTypeRv.setLayoutManager(layoutManager);

        mDeliveryTypeAdapter = new DeliveryTypeAdapter();
        mDeliveryTypeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mDeliveryTypeRv.setAdapter(mDeliveryTypeAdapter);
        mDeliveryTypeRv.setHasFixedSize(true);
        mDeliveryTypeRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int width = SizeUtils.dp2px(10);
                if (mDeliveryTypeRv.getChildAdapterPosition(view) == 0) {
                    outRect.left = width;
                    outRect.right = width;
                } else {
                    outRect.right = width;
                }

            }
        });

        mDeliveryTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (mDeliveryTypePosition == position) {
                    //重复点击无效
                    return;
                }
                if (mDeliveryTypeBean != null) {
                    //先将所有选中状态取消
                    for (DeliveryTypeItemBean item : mDeliveryTypeAdapter.getData()) {
                        item.setCheck(false);
                    }
                    //设置当前选中状态
                    mDeliveryTypeAdapter.getItem(position).setCheck(true);
                    mDeliveryTypePosition = position;
                    mDeliveryTypeAdapter.notifyDataSetChanged();
                    DeliveryTypeItemBean deliveryTypeItemBean = mDeliveryTypeAdapter.getItem(position);
                    //配送方式 1-快递配送 2-快速送 3-门店自提 4-次日达
                    String deliveryTypeId = deliveryTypeItemBean.getDeliveryTypeId();
                    //快递配送业务类型ID
                    String deliveryBusinessType = deliveryTypeItemBean.getDeliveryBusinessType();

                    getCallBack().onDeliveryViewChangeType(mDeliveryTypeBean);
                }
            }
        });
    }

    /**
     * 默认UI->全部gone
     */
    private void setDefaultUI(){
        mDeliveryTypeContainerLl.setVisibility(View.GONE);
        mSingleDeliveryTypeNameTv.setVisibility(View.GONE);
        mDeliveryTypeRv.setVisibility(View.GONE);
        mDeliveryTypeTipsTv.setVisibility(View.GONE);
        mXuLine.setVisibility(View.GONE);
        mDeliveryAddressTv.setVisibility(View.GONE);
        mColorLine.setVisibility(View.GONE);
        mCartNoGoodsTipsTv.setVisibility(View.GONE);
        mNextDayNoGoodsLl.setVisibility(View.GONE);
    }

    /**
     * 配送数据返回
     *
     * @param bean                  数据
     * @param mIsFromNextDayPage    是否从次日达页面过来
     * @param mIsFromFastSendPage   是否从快速送页面过来
     * @param mIsFromArriveShopPage 是否从门店自提页面过来
     */
    private void setNewData(DeliveryTypeBean bean, boolean mIsFromNextDayPage,
                            boolean mIsFromFastSendPage, boolean mIsFromArriveShopPage) {
        mDeliveryTypeBean = bean;
        DeliveryTypeItemBean deliveryTypeItemBean = null;//配送方式item
        //配送方式列表
        List<DeliveryTypeItemBean> deliveryTypeList = mDeliveryTypeBean.getDeliveryTypeList();
        if (ListUtils.isEmpty(deliveryTypeList)){
            return;
        }

        //判断如果从次日达专区进入
        if (mIsFromNextDayPage) {
            mDeliveryTypePosition = customDeliverySelectedStyleWithFromPage(deliveryTypeList, "4");
        } else if (mIsFromFastSendPage) {//判断如果从快速送专区进入
            mDeliveryTypePosition = customDeliverySelectedStyleWithFromPage(deliveryTypeList, "2");
        } else if (mIsFromArriveShopPage) {//判断如果从快速送专区进入
            mDeliveryTypePosition = customDeliverySelectedStyleWithFromPage(deliveryTypeList, "3");
        }
        mDeliveryTypeRv.scrollToPosition(mDeliveryTypePosition);

        //初始化的时候已经把所有布局都Gone掉了,这里再次Gone掉是为了防止切换门店导致UI判断漏洞
        setDefaultUI();
        mDeliveryTypeContainerLl.setVisibility(View.VISIBLE);
        //判断是否只有一种配送方式
        if (deliveryTypeList.size() == 1){
            mSingleDeliveryTypeNameTv.setVisibility(View.VISIBLE);
            deliveryTypeItemBean = deliveryTypeList.get(0);
            //配送方式 1-快递配送 2-快速送 3-门店自提 4-次日达
            String deliveryTypeId = deliveryTypeItemBean.getDeliveryTypeId();
            //配送方式名称
            String deliveryTypeName = deliveryTypeItemBean.getDeliveryTypeName();
            if ("1".equals(deliveryTypeId)) {
                //1-快递配送
                getCallBack().onDeliveryisOnlyType1();
                return;
            }else if ("4".equals(deliveryTypeId)){
                //4-次日达
                mSingleDeliveryTypeNameTv.setText(new SpanUtils()
                        .append(deliveryTypeName)
                        .append("（" + mDeliveryTypeBean.getNextDayServiceTips() + "）")
                        .setForegroundColor(mContext.getResources().getColor(R.color.light_text_color))
                        .setFontSize(10,true)
                        .create());
            } else {
                mSingleDeliveryTypeNameTv.setText(deliveryTypeName);
            }
        }else {
            //多配送方式
            mDeliveryTypeRv.setVisibility(View.VISIBLE);

            //当有多个配送方式时，设置默认选中状态
            deliveryTypeItemBean = deliveryTypeList.get(mDeliveryTypePosition);
            deliveryTypeItemBean.setCheck(true);
            mDeliveryTypeAdapter.setNewData(deliveryTypeList);
        }

        ///deliveryTypeItemBean要么是单一配送bean,要么是多配送模式下,选中的配送方式bean
        changeUI(mDeliveryTypeBean,deliveryTypeItemBean);
    }

    private void changeUI(DeliveryTypeBean bean,DeliveryTypeItemBean typeItemBean) {
        mDeliveryTypeTipsTv.setVisibility(View.GONE);
        mXuLine.setVisibility(View.GONE);
        mDeliveryAddressTv.setVisibility(View.GONE);
        mColorLine.setVisibility(View.GONE);
        mCartNoGoodsTipsTv.setVisibility(View.GONE);
        mNextDayNoGoodsLl.setVisibility(View.GONE);
        //配送方式 1-快递配送 2-快速送 3-门店自提 4-次日达
        int deliveryBusinessType = BaseParser.parseInt(typeItemBean.getDeliveryBusinessType());
        switch (deliveryBusinessType) {
            case 1:
                //1-快递配送
                express();
                break;
            case 2:
                //2-快速送
                sendQuickly(bean);
                break;
            case 3:
                //3-门店自提
                break;
            case 4:
                //4-次日达
                break;
            default:
                break;
        }
    }

    /**
     * 快递配送,如果只有一种配送并且是快递配送的话,已经return掉,不会走到这里
     */
    private void express(){
    }

    /**
     * 快速送
     *
     * @param bean
     */
    private void sendQuickly(DeliveryTypeBean bean){
        //设置配送提示语
        if (!StringUtils.isEmpty(bean.getDeliveryTips())) {
            mDeliveryTypeTipsTv.setVisibility(View.VISIBLE);
            mDeliveryTypeTipsTv.setText(bean.getDeliveryTips());
        } else {
            mDeliveryTypeTipsTv.setVisibility(View.GONE);
        }
    }


    /**
     * 从哪个页面进入购物车，需要判断选中相应的配送类别
     *
     * @param deliveryTypeList
     * @param type
     * @return
     */
    private int customDeliverySelectedStyleWithFromPage(List<DeliveryTypeItemBean> deliveryTypeList, String type) {
        for (int position = 0; position < deliveryTypeList.size(); position++) {
            DeliveryTypeItemBean typeItemBean = deliveryTypeList.get(position);
            String deliveryTypeId = typeItemBean.getDeliveryTypeId();
            if (deliveryTypeId.equals(type)) {
                return position;
            }
        }
        return 0;
    }

}

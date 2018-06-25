package com.jinke.community.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsm.xiaodi.biz.sdk.XiaodiSdkLibInit;
import com.dsm.xiaodi.biz.sdk.servercore.ServerUnit;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseFragment;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.bean.TempHouseBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.presenter.PersonalCenterPresent;
import com.jinke.community.ui.activity.base.MsgCenterActivity;
import com.jinke.community.ui.activity.base.MyMeansActivity;
import com.jinke.community.ui.activity.base.ScanActivity;
import com.jinke.community.ui.activity.base.SettingActivity;
import com.jinke.community.ui.activity.base.ShareAppActivity;
import com.jinke.community.ui.activity.broken.PropertyHistoryActivity;
import com.jinke.community.ui.activity.house.MyHouseActivity;
import com.jinke.community.ui.activity.payment.PaymentRecordActivity;
import com.jinke.community.ui.activity.payment.PrepaidExpensesActivity;
import com.jinke.community.ui.activity.vehicle.VehicleManagementActivity;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.activity.wisdom.HealthActivity;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.utils.CameraUtil;
import com.jinke.community.utils.RedCircleControlUtil;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.utils.ThemeUtils;
import com.jinke.community.view.IPersonalCenterView;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.charmhome.application.LuMiConfig;
import www.jinke.com.charmhome.ui.activity.StartPageActivity;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-25.
 */

public class PersonalCenterFragment extends BaseFragment<IPersonalCenterView, PersonalCenterPresent> implements IPersonalCenterView {
    @Bind(R.id.tx_present_userName)
    TextView userName;
    @Bind(R.id.tx_owner_name)
    TextView txOwnerName;
    @Bind(R.id.tx_default_hosue)
    TextView defaultHouse;
    @Bind(R.id.tx_personal_identity)
    TextView identity;
    @Bind(R.id.present_head_image)
    SimpleDraweeView headImage;
    @Bind(R.id.present_balance_number)
    TextView banlanceNumber;
    @Bind(R.id.present_integrate_number)
    TextView integrateNumber;
    @Bind(R.id.present_integrate_car_number)
    TextView integrateCardNumber;
    @Bind(R.id.rl_my_vehicle)
    RelativeLayout rlMyVehicle;
    @Bind(R.id.img_person_order)
    ImageView imgPersonOrder;
    @Bind(R.id.img_person_post)
    ImageView imgPersonPost;
    @Bind(R.id.img_app_spread)
    ImageView imgAppSpread;
    @Bind(R.id.img_scan)
    ImageView imgScan;
    @Bind(R.id.tx_edit_info)
    TextView txEditInfo;
    @Bind(R.id.rl_msg)
    RelativeLayout rlMsg;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    @Bind(R.id.present_balance_image)
    ImageView imgBlance;
    @Bind(R.id.present_integrate_image)
    ImageView imgIntegrate;
    @Bind(R.id.present_integrate_car_image)
    ImageView imgCard;
    @Bind(R.id.img_unread)
    ImageView imgUnRead;

    private ACache aCache = null;
    private HouseListBean.ListBean houseBean = null;
    private BaseUserBean userBean = null;
    private HouseListBean houseListBean = null;
    private MsgBean msgBean = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personalcenter;
    }

    @Override
    public PersonalCenterPresent initPresenter() {
        return new PersonalCenterPresent(getActivity());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        aCache = ACache.get(getActivity());
        //获取预存余额信息
        presenter.getIntegral();
        //获取积分信息
        presenter.getPoint();
        //获取配置信息
        presenter.getConfigUrl();
        //检测图标是否需要更新
        ThemeUtils.checkThemeIcon(getActivity(), 3, imgBg, imgBlance, imgIntegrate, imgCard);
    }

    /**
     * 获取预存费用信息成功
     */
    @Override
    public void onPrePayList(PrepaidExpensesBean bean) {
        userBean.setPre_money(bean.getPre_money());
        SharedPreferencesUtils.saveBaseUserBean(getActivity(), userBean);
        banlanceNumber.setText(StringUtils.equals(userBean.getPre_money(), "0.00") ? "--" : userBean.getPre_money() + "元");//显示预存余额
        integrateNumber.setText(StringUtils.equals(userBean.getPoint_num(), "0") ? "--" : userBean.getPoint_num() + "");//积分
    }

    /**
     * 获取预存费用信息失败
     */
    @Override
    public void getPrePayListError() {
    }

    /**
     * 获取积分信息成功
     */
    @Override
    public void getPointInfo(PrepaidExpensesBean bean) {
        userBean.setPoint_num(bean.getPoint_num() + "");
        SharedPreferencesUtils.saveBaseUserBean(getActivity(), userBean);
        integrateNumber.setText(StringUtils.equals(userBean.getPoint_num(), "0") ? "--" : userBean.getPoint_num() + "");//积分
    }

    /**
     * 获取配置信息成功
     */
    @Override
    public void onConfigUrl(UrlConfigBean bean) {
        SharedPreferencesUtils.saveUrlConfig(getActivity(), bean);
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取消息
        presenter.getMsg();
        houseBean = MyApplication.getInstance().getDefaultHouse();//获取默认房屋信息
//        presenter.getUserInfo();
        setUserInfo();
        if (houseBean == null)
            presenter.getHouseList();
        setHouseInfo();
        getRedCircleState(getActivity());
        AnalyUtils.setBAnalyResume(getActivity(),"个人中心");
    }

    @Override
    public void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(getActivity(),"个人中心");
    }

    /**
     * 获取用户信息成功回调
     */
    @Override
    public void getUserInfoNext(UserInfo info) {
        setUserInfo();
    }


    //检测红点状态
    public void getRedCircleState(Context context) {
        RedCircleControlUtil.setPersonRedCirlce(context, imgPersonOrder, imgPersonPost, imgAppSpread);
    }

    //设置用户信息
    private void setUserInfo() {
        userBean = MyApplication.getBaseUserBean();
        userName.setVisibility(StringUtils.isEmpty(userBean.getNickName()) ? View.INVISIBLE : View.VISIBLE);
        identity.setVisibility(StringUtils.isEmpty(userBean.getIdentity()) ? View.INVISIBLE : View.VISIBLE);
        userName.setText(StringUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
        identity.setText(StringUtils.isEmpty(userBean.getIdentity()) ? "" : userBean.getIdentity());
        txEditInfo.setVisibility(StringUtils.equals("false", userBean.getIsSuccess()) ? View.VISIBLE : View.GONE);
        headImage.setImageURI(userBean.getAvatar());
        banlanceNumber.setText(StringUtils.isEmpty(userBean.getPre_money()) || Double.parseDouble(userBean.getPre_money()) == 0 ? "--" : DecimalFormatUtils.format(Double.parseDouble(userBean.getPre_money()), "0.00") + "元");//显示预存余额
        integrateNumber.setText(StringUtils.equals(userBean.getPoint_num(), "0") ? "--" : userBean.getPoint_num() + "");//积分
    }

    //设置房屋信息
    private void setHouseInfo() {
        defaultHouse.setText(houseBean == null ? getString(R.string.house_empty) : "当前房屋：" + houseBean.getCommunity_name() + houseBean.getHouse_name());
        txOwnerName.setText(userBean == null ? "邻居，您好！" : !StringUtils.isEmpty(userBean.getName()) ? userBean.getName() + "，您好！" : "邻居，您好！");
    }

    /**
     * 获取房屋列表成功回调
     */
    @Override
    public void getHouseListNext(HouseListBean info) {
        aCache.put("HouseListBean", info, ACache.TIME_DAY);
        houseBean = MyApplication.getInstance().getDefaultHouse();//获取默认房屋信息
        setHouseInfo();
    }

    /**
     * 获取房屋列表 失败回调
     */
    @Override
    public void getHouseListError() {
        setHouseInfo();
    }

    @Override
    public void onDefaultDataNext(DefaultHouseBean info) {
        userBean = null;
        userBean = MyApplication.getBaseUserBean();
    }

    @OnClick({R.id.present_balance_root, R.id.present_named_view, R.id.present_head_image,
            R.id.rl_wisdom_house, R.id.rl_my_health, R.id.rl_my_vehicle, R.id.rl_payment_record,
            R.id.rl_post_record, R.id.rl_set, R.id.present_integrate_root, R.id.rl_app_spread,
            R.id.img_scan, R.id.rl_msg})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.present_head_image://个人设置
                startActivity(new Intent(getActivity(), MyMeansActivity.class));
                break;

            case R.id.present_balance_root://预存余额
                if (houseBean != null) {
                    Intent prepaidExpenses = new Intent(getActivity(), PrepaidExpensesActivity.class);
                    prepaidExpenses.putExtra("isPaymentChange", "0");//个人中心进入预存
                    startActivity(prepaidExpenses);
                } else {
                    ToastUtils.showShort(getActivity(), "您还没有绑定房屋!");
                    return;
                }
                break;

            case R.id.present_integrate_root://积分
                break;

            case R.id.present_named_view://我的房屋列表
                AnalyUtils.addAnaly(10041);
                startActivity(new Intent(getActivity(), MyHouseActivity.class));
                break;

            case R.id.rl_wisdom_house://魅力家
                getCharm();
                break;

            case R.id.rl_my_health://我的健康
                startActivity(new Intent(getActivity(), HealthActivity.class));
                break;

            case R.id.rl_my_vehicle://我的车辆
                presenter.getParkingInfo();
                break;

            case R.id.rl_payment_record://物业缴费记录
                if (houseBean != null) {
                    intent = new Intent(getActivity(), PaymentRecordActivity.class);
                    intent.putExtra("recorderType", "0");//0：缴费记录，1：代扣记录
                    TempHouseBean tempHouseBean = new TempHouseBean();
                    tempHouseBean.setAccessToken(userBean.getAccessToken());
                    tempHouseBean.setHouseId(houseBean.getHouse_id());
                    tempHouseBean.setHouseName(houseBean.getCommunity_name() + houseBean.getHouse_name());
                    SharedPreferencesUtils.saveTempHouseBean(getActivity(), tempHouseBean);
                } else {
                    presenter.showBindHouseTips();
                }
                break;

            case R.id.rl_post_record://物业报事记录
                if (houseBean != null) {
                    //更新红点状态(红点为显示状态时)
                    if (StringUtils.equals(SharedPreferencesUtils.getRedCircle(getActivity()).getPersonBreak(), "1"))
                        RedCircleControlUtil.upDateCicle(getActivity(), SharedPreferencesUtils.getRedCircle(getActivity()).getPersonBreakCode());
                    intent = new Intent(getActivity(), PropertyHistoryActivity.class);
                } else {
                    presenter.showBindHouseTips();
                    return;
                }
                break;

            case R.id.rl_set://设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;

            case R.id.rl_app_spread://app推广
                //更新红点状态(红点为显示状态时)
                if (StringUtils.equals(SharedPreferencesUtils.getRedCircle(getActivity()).getPersonSpread(), "1"))
                    RedCircleControlUtil.upDateCicle(getActivity(), SharedPreferencesUtils.getRedCircle(getActivity()).getPersonSpreadCode());
                startActivity(new Intent(getActivity(), ShareAppActivity.class));
                AnalyUtils.addAnaly(10051);
                break;

            case R.id.img_scan://二维码扫描
                startActivity(new Intent(getActivity(), ScanActivity.class));
                break;

            case R.id.rl_msg:
                startActivity(new Intent(getActivity(), MsgCenterActivity.class).putExtra("MsgBean", msgBean));
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    CameraUtil cameraUtil;

    //魅力家
    public void getCharm() {
//        魅力家初始化
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        XiaodiSdkLibInit.init(getActivity(), "29");
        //启用正是服务器
        ServerUnit.getInstance().enableOnlineServer();
        if (houseListBean != null) {
            LuMiConfig.ACCOUNT = userBean.getPhone();
            for (int x = 0; x < houseListBean.getList().size(); x++) {
//                        //多套房屋或id以竖线分割
                HouseListBean.ListBean bean = houseListBean.getList().get(x);
                if (x == houseListBean.getList().size()) {
                    LuMiConfig.HOURSEID += bean.getHouse_id();
                    LuMiConfig.HOURSENAME += bean.getHouse_name();
                } else {
                    LuMiConfig.HOURSEID += bean.getHouse_id() + "|";
                    LuMiConfig.HOURSENAME += bean.getHouse_name() + "|";
                }
            }
            startActivity(new Intent(getActivity(), StartPageActivity.class));
            AnalyUtils.addAnaly(10053);
        } else {
            ToastUtils.showShort(getActivity(), "请先绑定房屋信息");
        }
    }

    @OnClick({R.id.rl_my_order, R.id.rl_shipping_car, R.id.rl_shipping_address, R.id.rl_life_record})
    public void urlClick(View view) {
        Intent intent = null;
        UrlConfigBean urlConfigBean = MyApplication.getInstance().getUrlConfigBean();
        switch (view.getId()) {
            //我的订单
            case R.id.rl_my_order:
                RedCircleGroupBean beans = SharedPreferencesUtils.getRedCircle(getActivity());
                beans.setPersonOrder("0");
                SharedPreferencesUtils.setRedCircle(getActivity(), beans);

                String ShopOrderUrl = StringUtils.isEmpty(urlConfigBean.getShopOrderUrl()) ?
                        "http://wx-dev.jinke-service.com/wap/trade-list.html" : urlConfigBean.getShopOrderUrl();
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", ShopOrderUrl);
                intent.putExtra("title", "我的订单");
                break;

            //我的购物车
            case R.id.rl_shipping_car:
                String ShopCartUrl = StringUtils.isEmpty(urlConfigBean.getShopCartUrl()) ?
                        "http://wx-dev.jinke-service.com/wap/cart.html" : urlConfigBean.getShopCartUrl();
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", ShopCartUrl);
                intent.putExtra("title", "我的购物车");
                break;

            //收货地址
            case R.id.rl_shipping_address:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", "http://wx-dev.jinke-service.com/appbridge/");
                intent.putExtra("title", "收货地址");
                break;

            //生活服务记录
            case R.id.rl_life_record:
                String ayiHome = StringUtils.isEmpty(urlConfigBean.getAyiHomeUrl()) ?
                        "http://wx-dev.jinke-service.com/ayi/Home/sign" : urlConfigBean.getAyiHomeUrl();
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", ayiHome);
                intent.putExtra("title", "生活服务记录");
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 获取车位信息成功回调
     */
    @Override
    public void getParkInfoNext(ParkInfoBean info) {
        if (info != null && info.getList() != null && info.getList().size() > 0) {
            Intent rechargeIntent = new Intent(getActivity(), VehicleManagementActivity.class);
            startActivity(rechargeIntent);
            AnalyUtils.addAnaly(10052);
        } else {
            showMsg("未找到车辆绑定信息或当前社区暂未开通此功能！");
        }
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(getActivity(), msg);
    }

    /**
     * 消息中心回调
     *
     * @param info
     */
    @Override
    public void getMsgNext(MsgBean info) {
        if (info != null && info.getList() != null && info.getList().size() > 0) {
            imgUnRead.setVisibility(StringUtils.equals("1", info.getList().get(0).getIsRead()) ? View.VISIBLE : View.GONE);
            msgBean = info;
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog(null);
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }


}

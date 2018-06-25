package com.jinke.community.ui.fragment;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseFragment;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.acachebean.WeatherBean;
import com.jinke.community.presenter.HouseKeeperPresenter;
import com.jinke.community.ui.activity.base.CallActivity;
import com.jinke.community.ui.activity.broken.AnnouncementActivity;
import com.jinke.community.ui.activity.broken.BrokeNewsActivity;
import com.jinke.community.ui.activity.broken.BrokeStageActivity;
import com.jinke.community.ui.activity.broken.PropertyWebActivity;
import com.jinke.community.ui.activity.control.AccessControlActivity;
import com.jinke.community.ui.activity.control.OpenDoorActivity;
import com.jinke.community.ui.activity.house.MyHouseActivity;
import com.jinke.community.ui.activity.payment.PropertyPaymentActivity;
import com.jinke.community.ui.adapter.KeeperRecycleAdapter;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.GoTopScrollView;
import com.jinke.community.ui.widget.hightlight.HighLight;
import com.jinke.community.ui.widget.hightlight.OnTopPosCallback;
import com.jinke.community.ui.widget.hightlight.RectLightShape;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.LocationServiceUtils;
import com.jinke.community.utils.RedCircleControlUtil;
import com.jinke.community.utils.ShakeUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.utils.ThemeUtils;
import com.jinke.community.view.IHouseKeeperView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-25.
 * 管家界面
 */
public class HouseKeeperFragment extends BaseFragment<IHouseKeeperView, HouseKeeperPresenter> implements
        IHouseKeeperView, GoTopScrollView.ISmartScrollChangedListener, KeeperRecycleAdapter.KeeperRecycleListener {
    @Bind(R.id.rl_dynamic)
    RelativeLayout rlDynamic;
    @Bind(R.id.ls_home_bottom_list)
    FillRecyclerView listView;
    @Bind(R.id.scrollView)
    GoTopScrollView scrollView;
    @Bind(R.id.tx_title_community)
    TextView txCommunity;
    @Bind(R.id.tx_title_address)
    TextView txAddress;
    @Bind(R.id.tx_title_house_pic)
    TextView housePic;
    @Bind(R.id.tx_title_house_rent)
    TextView houseRent;
    @Bind(R.id.tx_title_weather_city)
    TextView weatherCity;
    @Bind(R.id.tx_title_weather_pm)
    TextView weatherPm;
    @Bind(R.id.tx_weather_qlty)
    TextView weatherQlty;
    @Bind(R.id.tx_title_weather_air)
    TextView weatherAir;
    @Bind(R.id.tx_title_weather_state)
    TextView weatherState;
    @Bind(R.id.tx_title_weather)
    TextView weatherD;
    @Bind(R.id.image_weather_icon)
    ImageView weatherIcon;
    @Bind(R.id.image_housekeeper_top)
    ImageView houseKeeperTop;
    @Bind(R.id.ll_weather_view)
    LinearLayout weatherView;
    @Bind(R.id.rl_first_break)
    RelativeLayout rlFirstBreak;
    @Bind(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @Bind(R.id.tx_see_more)
    TextView txSeeMore;
    @Bind(R.id.img_home_call)
    ImageView imgHomeCall;

    @Bind(R.id.img_root)
    ImageView imgRoot;
    @Bind(R.id.img_payment)
    ImageView imgPayment;
    @Bind(R.id.img_door)
    ImageView imgDoor;
    @Bind(R.id.img_call)
    ImageView imgCall;
    @Bind(R.id.img_break)
    ImageView imgBreak;

    RelativeLayout rlMain;
    private KeeperRecycleAdapter adapter;

    private ACache aCache;
    private List<NoticeListBean.ListBean> breakList = new ArrayList<>();//爆料列表
    private List<NoticeListBean.ListBean> noticeList = new ArrayList<>();//公告列表
    public static DefaultHouseBean defaultHouseBean = null;
    private HighLight hightLight = null;
    //缓存数据
    private WeatherBean weatherBean = null;
    private HouseListBean houseListBean = null;
    private NoticeListBean noticeListBean = null;
    private HouseValueBean houseValueBean = null;
    private boolean noHouse = false;
    //摇一摇
    private ShakeUtils mShakeUtils = null;
    private boolean isOpen = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_housekeeper_two;
    }

    @Override
    public HouseKeeperPresenter initPresenter() {
        return new HouseKeeperPresenter(getActivity());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        aCache = ACache.get(getActivity());
        adapter = new KeeperRecycleAdapter(getActivity(), breakList);
        adapter.setListener(this);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adapter);
        scrollView.setScanScrollChangedListener(this);
        listView.setFocusable(false);
        ThemeUtils.checkThemeIcon(getActivity(), 2, imgRoot, imgPayment, imgDoor, imgCall, imgBreak);
        //测试摇一摇开门
        mShakeUtils = new ShakeUtils(getActivity());
        addShakeListener();
        checkPermission();
    }

    private void addShakeListener() {
        mShakeUtils.setOnShakeListener(new ShakeUtils.OnShakeListener() {
            @Override
            public void onShake() {
                if (MyApplication.getInstance().getDefaultHouse() != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0过后需要判断位置服务
                        isOpen = LocationServiceUtils.checkLocationEnable(getActivity());
                        if (!isOpen) {
                            Toast.makeText(getActivity(), "获取位置信息失败，请打开位置服务！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            return;
                        }
                    }
                    if (!isOpen) {
                        ToastUtils.showShort(getActivity(), "蓝牙权限获取失败，请前往应用权限管理中，进行授权!");
                        return;
                    }
                    BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBtAdapter.isEnabled()) {
                        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableIntent, 1313);
                    } else {
                        startActivity(new Intent(getActivity(), OpenDoorActivity.class));
                    }
                } else {
                    presenter.showBandingHouseDialog();
                }
            }
        });
    }

    private void checkPermission() {
        RxPermissions.getInstance(getActivity())
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            isOpen = true;
                        } else {
                            ToastUtils.showLong(getActivity(), "权限获取失败，请前往应用权限管理中进行授权!");
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                            startActivity(localIntent);
                            isOpen = false;
                        }
                    }
                });
    }

    //检测红点
    public void getRedCircleState(Context context) {
        RedCircleControlUtil.setHouseRedCirlce(context, imgHomeCall);
    }

    @Override
    public void onResume() {
        super.onResume();
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        presenter.getDefaultData();//获取默认房屋信息
        initData();
        if (!MyApplication.getBaseUserBean().isHouse() && !StringUtils.isEmpty(SharedPreferencesUtils.getCommunityId(getActivity()))) {//如果没有默认房屋根据小区ID查看列表
            getBreakList();
            getWeatheInfo();
        }
        getRedCircleState(getActivity());
        AnalyUtils.setBAnalyResume(getActivity(), "管家");
        AnalyUtils.addAnaly(10055);
        mShakeUtils.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(getActivity(), "管家");
        mShakeUtils.onPause();
    }

    @Override
    public void onDefaultHouse(DefaultHouseBean bean) {
        if (bean != null) {
            defaultHouseBean = bean;
            noHouse = false;
            SharedPreferencesUtils.saveDefaultHouse(getContext(), bean);
            getWeatheInfo();
        }
        if (!StringUtils.isEmpty(bean.getAddress())) {
            SharedPreferencesUtils.saveDefaultHouse(getActivity(), bean);
            txCommunity.setText(bean.getCommunity_name());
            txAddress.setText(bean.getHouse_name());
        }
        getBreakList();
    }

    private void initData() {//加载本地缓存数据
        if (houseListBean != null && houseListBean.getList() != null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (bean.getDft_house() == 1) {
                    txCommunity.setText(bean.getCommunity_name());
                    txAddress.setText(bean.getHouse_name());
                }
            }
        } else {
            txCommunity.setText(getString(R.string.fragment_housekeeper_selected_house));
            txAddress.setText("");
        }
        presenter.getHouseList();//获取房屋列表

        //天气
        weatherBean = (WeatherBean) aCache.getAsObject("WeatherBean");
        if (weatherBean != null) {
            getWeatheInfoNext(weatherBean);//刷新天气信息
        }
        //公告
        noticeListBean = (NoticeListBean) aCache.getAsObject("NoticeListBean");
        if (noticeListBean != null) {
            showListUI(noticeListBean);//刷新公告/爆料台显示列表
        }
        //房屋估价
        houseValueBean = (HouseValueBean) aCache.getAsObject("HouseValueBean");
        if (houseValueBean != null) {
            housePic.setText("参考房价: " + houseValueBean.getTotalprice() + "万");
            houseRent.setText("租金:" + houseValueBean.getRent());
        }
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        hideDialog();
        houseListBean = info;
        noHouse = false;
        aCache.put("HouseListBean", info, ACache.TIME_DAY);
    }

    @Override
    public void setDefaultData(String substring) {
        noHouse = false;
        txCommunity.setText(substring);
    }

    @Override
    public void getHouseListEmpty() {
        HouseListBean bean = null;
        aCache.put("HouseListBean", bean, ACache.TIME_DAY);
        txCommunity.setText("请绑定房屋！");
        housePic.setText("参考房价: --");
        txAddress.setText("");
        listView.setVisibility(View.GONE);
        rlFirstBreak.setVisibility(View.VISIBLE);
        noHouse = true;
    }

    @OnClick({R.id.rl_open_door, R.id.rl_broken, R.id.rl_payment, R.id.rl_call, R.id.rl_dynamic,
            R.id.image_housekeeper_top, R.id.rl_house_manager, R.id.rl_break, R.id.tx_break, R.id.tx_see_more, R.id.img_root})
    public void onClick(View view) {
        if (view == null)
            return;
        switch (view.getId()) {
            case R.id.image_housekeeper_top://回到顶部
                getSileTop();
                break;

            case R.id.rl_house_manager://我的房屋
                AnalyUtils.addAnaly(10040);
                startActivity(new Intent(getActivity(), MyHouseActivity.class));
                break;

            case R.id.rl_dynamic://通知公告
                startActivity(new Intent(getActivity(), AnnouncementActivity.class));
                break;

            case R.id.rl_open_door://门禁开门
                getIsHouse(AccessControlActivity.class);
                break;

            case R.id.rl_payment://缴费中心
                getIsHouse(PropertyPaymentActivity.class);
                break;

            case R.id.tx_break:
            case R.id.rl_broken://报事爆料
                getIsHouse(BrokeNewsActivity.class);
                break;

            case R.id.rl_call://呼叫中心
                isHouse();
                break;

            case R.id.rl_break://平台爆料列表
                if (MyApplication.getInstance().getDefaultHouse() != null)
                    AnalyUtils.addAnaly(10034, MyApplication.getInstance().getDefaultHouse().getHouse_id());
                else
                    AnalyUtils.addAnaly(10034);
                getIsHouse(BrokeStageActivity.class);
                break;

            case R.id.tx_see_more:
                if (MyApplication.getInstance().getDefaultHouse() != null)
                    AnalyUtils.addAnaly(10035, MyApplication.getInstance().getDefaultHouse().getHouse_id());
                else
                    AnalyUtils.addAnaly(10035);
                getIsHouse(BrokeStageActivity.class);
                break;
            case R.id.img_root://shuimian
//                startActivity(new Intent(getActivity(), SleepWebActivity.class).putExtra("url","file:///android_asset/sss.html"));

//                startActivity(new Intent(getActivity(), SleepWebActivity.class)
//                        .putExtra("url", "https://api.cqjiaonan.com/web.php?s=/Demo/demo")
//                        .putExtra("title", "睡眠仓"));

//                startActivity(new Intent(getActivity(), SleepWebActivity.class)
//                        .putExtra("url", "https://api.cqjiaonan.com/web.php?s=/Demo/page_test")
//                        .putExtra("title", "睡眠仓"));

                break;
        }
    }

    public void getSileTop() {
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, 0);
                    houseKeeperTop.setVisibility(View.GONE);
                    presenter.setAnimation(houseKeeperTop, R.anim.main_housekeeper_top_hide);
                }
            });
        }
    }

    //判断是否有房屋  有就跳转到CallActivity界面，没有就弹框提示
    private void isHouse() {
        if (MyApplication.getInstance().getDefaultHouse() != null) {
            //更新红点状态(红点为显示状态时)
            if (StringUtils.equals(SharedPreferencesUtils.getRedCircle(getActivity()).getHouseKeeperCall(), "1"))
                RedCircleControlUtil.upDateCicle(getActivity(), SharedPreferencesUtils.getRedCircle(getActivity()).getHouseKeeperCalloCode());
            startActivity(new Intent(getActivity(), CallActivity.class));
        } else {
            presenter.showBandingHouseDialog();
        }
    }

    //判断是否有房屋，跳转其他界面
    public void getIsHouse(Class activity) {
        if (MyApplication.getInstance().getDefaultHouse() != null) {
            startActivity(new Intent(getActivity(), activity));
        } else {
            presenter.showBandingHouseDialog();
        }
    }

    @Override
    public void showMsg(String msg) {
        hideDialog();
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(getActivity(), msg);
    }


    //更改爆料列表的UI 将爆料和物业报事分开
    private void showListUI(NoticeListBean bean) {
        if (bean != null && bean.getList() != null) {
            breakList.clear();
            noticeList.clear();
            for (NoticeListBean.ListBean listBean : bean.getList()) {
                switch (listBean.getType()) {
                    case 1:
                        noticeList.add(listBean);
                        break;
                    case 2:
                        breakList.add(listBean);
                        break;
                }
            }
            if (breakList != null && breakList.size() > 0 && noHouse == false) {
                rlFirstBreak.setVisibility(View.GONE);//列表不为空
                listView.setVisibility(View.VISIBLE);
                txSeeMore.setVisibility(View.VISIBLE);
            } else {
                rlFirstBreak.setVisibility(View.VISIBLE);//列表为空
                listView.setVisibility(View.GONE);
                txSeeMore.setVisibility(View.GONE);
            }
            if (getActivity() != null && !StringUtils.isEmpty(SharedPreferencesUtils.getFirst(getActivity()))) {
                setViewFilpperData(noticeList);
            }
            adapter.setDataList(breakList);
        }
    }

    //设置ViewFilpper 数据
    private void setViewFilpperData(final List<NoticeListBean.ListBean> noticeList) {
        viewFlipper.removeAllViews();
        if (noticeList != null) {
            for (NoticeListBean.ListBean bean : noticeList) {
                View view = View.inflate(getActivity(), R.layout.item_view_filpper, null);
                TextView txTitle = (TextView) view.findViewById(R.id.tx_notice_title);
                TextView txTime = (TextView) view.findViewById(R.id.tx_notice_time);
                ImageView imageNew = (ImageView) view.findViewById(R.id.image_new);
                txTitle.setText(bean.getTitle().toString().trim());
                try {
                    txTime.setText(TextUtils.timeChangeBreakStage(bean.getCreateTime()));
                    if (txTime.getText().toString().trim().contains("-")) {
                        imageNew.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewFlipper.addView(view);
                viewFlipper.startFlipping();
            }
            viewFlipper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewFlipper.getDisplayedChild();
                    Intent intent = new Intent(getActivity(), PropertyWebActivity.class);
                    if (position < noticeList.size()) {
                        AnalyUtils.addAnaly(10023);//统计信息
                        intent.putExtra("noticeId", String.valueOf(noticeList.get(position).getNoticeId()));
                        startActivity(intent);
                    }
                }
            });
        }
    }

    /**
     * 获取天气信息 回调
     */
    private void getWeatheInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("communityId", presenter.getCommitId());
        presenter.getWeatheInfo(map);//获取天气数据
    }

    /**
     * 获取天气信息 回调
     *
     * @param bean
     */
    @Override
    public void getWeatheInfoNext(WeatherBean bean) {
        if (bean != null) {
            weatherBean = bean;
            aCache.put("WeatherBean", bean, ACache.TIME_DAY);
            weatherView.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(bean.getIcon())
                    .error(R.drawable.icon_life_fail_type)
                    .placeholder(R.drawable.icon_life_fail_type)
                    .into(weatherIcon);
            weatherD.setText(StringUtils.isEmpty(bean.getMax()) ? "--" : bean.getMax() + "°");
            weatherState.setText(StringUtils.isEmpty(bean.getTxt()) ? "-" : bean.getTxt());
            weatherCity.setText(StringUtils.isEmpty(bean.getCity()) ? "--" : bean.getCity());
            weatherPm.setText(StringUtils.isEmpty(bean.getPm25()) || bean.getPm25().equals("0") ? "--" : "PM2.5  " + bean.getPm25());
            weatherPm.setVisibility(StringUtils.isEmpty(bean.getPm25()) || bean.getPm25().equals("0") ? View.GONE : View.VISIBLE);
            weatherQlty.setText(StringUtils.isEmpty(bean.getQlty()) ? "--" : bean.getQlty());
            weatherQlty.setVisibility(StringUtils.isEmpty(bean.getAqi()) || bean.getAqi().equals("0") ? View.GONE : View.VISIBLE);
            weatherAir.setText(StringUtils.isEmpty(bean.getAqi()) || bean.getAqi().equals("0") ? "--" : bean.getAqi());
            weatherAir.setVisibility(StringUtils.isEmpty(bean.getAqi()) || bean.getAqi().equals("0") ? View.GONE : View.VISIBLE);
        }
    }

    //获取房屋估价成功
    @Override
    public void onHouseValue(HouseValueBean bean) {
        if (bean != null) {
            aCache.put("HouseValueBean", bean, ACache.TIME_DAY);
            housePic.setText("参考房价: " + bean.getTotalprice() + "万");
            houseRent.setText("租金:" + bean.getRent());
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

    @Override
    public void onScrolledToBottom() {
        houseKeeperTop.setVisibility(View.VISIBLE);
        presenter.setAnimation(houseKeeperTop, R.anim.main_housekeeper_top_show);
    }

    @Override
    public void onScrolledToTop() {
        houseKeeperTop.setVisibility(View.GONE);
        presenter.setAnimation(houseKeeperTop, R.anim.main_housekeeper_top_hide);
    }

    /**
     * KeeperRecycleAdapter 点赞 回调
     *
     * @param position
     */
    @Override
    public void adapterPraiseClick(int position) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("jmOrderId", String.valueOf(breakList.get(position).getNoticeId()));
        presenter.praiseClick(map);
    }

    /**
     * KeeperRecycleAdapter 回调
     */
    @Override
    public void adapterSeeMore() {
        getIsHouse(BrokeStageActivity.class);
    }

    /**
     * 点赞回调
     *
     * @param info
     */
    @Override
    public void praiseClickNext(PraiseresultBean info) {
    }

    //获取公告、爆料列表
    private void getBreakList() {
        Map<String, String> call = new HashMap<>();
        call.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        call.put("communityId", presenter.getCommitId());
        call.put("page", "1");
        call.put("rows", "2");
        presenter.getBreakList(call);
    }

    //获取公告、爆料列表成功
    @Override
    public void onNoticeList(NoticeListBean bean) {
        noticeListBean = bean;
        showListUI(bean);
        aCache.put("NoticeListBean", bean, ACache.TIME_DAY);//缓存管家界面的爆料列表
    }

    //显示引导蒙层
    public void showGuid() {
        if (this.getActivity() != null && StringUtils.isEmpty(SharedPreferencesUtils.getFirst(getActivity())) && viewFlipper != null) {
            viewFlipper.removeAllViews();
            View view = View.inflate(getActivity(), R.layout.item_view_filpper, null);
            if (view == null)
                return;
            TextView txTitle = view.findViewById(R.id.tx_notice_title);
            TextView txTime = view.findViewById(R.id.tx_notice_time);
            ImageView imageNew = view.findViewById(R.id.image_new);
            txTitle.setText("关于停电的紧急通知");
            txTime.setText("1小时以前");
            if (txTime.getText().toString().trim().contains("-")) {
                imageNew.setVisibility(View.GONE);
            }
            viewFlipper.setAutoStart(false);
            viewFlipper.stopFlipping();
            viewFlipper.addView(view);
            getSileTop();
            rlMain = getActivity().findViewById(R.id.activity_main);
            hightLight = new HighLight(getActivity());
            hightLight.autoRemove(false)
                    .intercept(true)
                    .anchor(rlMain)
                    .addHighLight(R.id.rl_dynamic, R.layout.info_known, new OnTopPosCallback(), new RectLightShape());
            hightLight.show();
            View hightview = hightLight.getHightLightView();
            if (hightview != null) {
                TextView textView = hightview.findViewById(R.id.tx_guide_known);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtils.setFirst(getActivity(), false);
                        viewFlipper.removeAllViews();
                        setViewFilpperData(noticeList);
                        hightLight.remove();
                    }
                });
            } else {
                viewFlipper.removeAllViews();
            }
        }
    }
}
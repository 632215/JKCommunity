package com.jinke.community.ui.activity.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.doormaster.vphone.config.DMCallState;
import com.doormaster.vphone.exception.DMException;
import com.doormaster.vphone.inter.DMModelCallBack;
import com.doormaster.vphone.inter.DMVPhoneModel;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.http.door.http.DateCallBackInterface;
import com.jinke.community.http.door.http.HttpDateGet;
import com.jinke.community.presenter.MainPresenter;
import com.jinke.community.ui.activity.step.PreferencesUtils;
import com.jinke.community.ui.activity.step.StepData;
import com.jinke.community.ui.activity.step.StepService;
import com.jinke.community.ui.activity.step.UpdateUiCallBack;
import com.jinke.community.ui.fragment.HouseKeeperFragment;
import com.jinke.community.ui.fragment.LifeFragment;
import com.jinke.community.ui.fragment.PersonalCenterFragment;
import com.jinke.community.ui.fragment.SocialCircleFragment;
import com.jinke.community.ui.toast.BindHouseDialog;
import com.jinke.community.ui.widget.CustomRadioButton;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AppManager;
import com.jinke.community.utils.RedCircleControlUtil;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.utils.ThemeUtils;
import com.jinke.community.view.IMainView;
import com.tencent.stat.StatService;
import com.ypy.eventbus.EventBus;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements RadioGroup.OnCheckedChangeListener
        , IMainView, BindHouseDialog.onCallPhoneListener {
    @Bind(R.id.main_bottom_group)
    RadioGroup mBottomGroup;
    @Bind(R.id.main_housekeeper)
    CustomRadioButton mainHousekeeper;
    @Bind(R.id.main_life)
    CustomRadioButton mainLife;
    @Bind(R.id.main_circle)
    CustomRadioButton mainCircle;
    @Bind(R.id.main_personalCenter)
    CustomRadioButton mainPersonalCenter;

    private final static int AccountType = 1;//1:手机账号，2：设备账号（传入设备的序列号和设备的管理密码）
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private HouseKeeperFragment houseKeeperFragment;
    private LifeFragment lifeFragment;
    private SocialCircleFragment socialCircleFragment;
    private PersonalCenterFragment personalCenterFragment;
    private PreferencesUtils sharedPreferencesUtils;
    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        mBottomGroup.setOnCheckedChangeListener(this);
        setDefaultFragment();
        setupService();
        getHouseList();
        try {
            ThemeUtils.checkThemeButton(this, mainHousekeeper, mainLife, mainCircle, mainPersonalCenter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getHouseList() {
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean == null) {
            presenter.getHouseList();
        }
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        aCache.put("HouseListBean", info, ACache.MAX_COUNT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断开关是否打开
        if (SharedPreferencesUtils.getVisitorVideoAccess(this) != null && StringUtils.equals(SharedPreferencesUtils.getVisitorVideoAccess(this), "true")) {
            switch (SharedPreferencesUtils.getVisitorVideoAccess(this)) {
                case "true":
                    HttpDateGet httpDateGet = new HttpDateGet(MainActivity.this, dateCallBackInterface);
                    httpDateGet.getEmployees(MyApplication.getBaseUserBean().getPhone());
                    break;
                case "false":
                    DMVPhoneModel.exit();
                    break;
            }
        } else if (SharedPreferencesUtils.getVisitorVideoAccess(this) == null || StringUtils.isEmpty(SharedPreferencesUtils.getVisitorVideoAccess(this))) {
            SharedPreferencesUtils.setVisitorVideoAccess(this, true);
            HttpDateGet httpDateGet = new HttpDateGet(MainActivity.this, dateCallBackInterface);
            httpDateGet.getEmployees(MyApplication.getBaseUserBean().getPhone());
        }
        DMVPhoneModel.addCallStateListener(callStateListener);
        presenter.getUserCenter();//获取用户信息
        //检测是否有红点标识
        presenter.getRedCircle();
        StatService.onResume(MainActivity.this);
        StatService.trackBeginPage(MainActivity.this, "主页");
        AnalyUtils.setBAnalyResume(this, "主页");
    }

    @Override
    protected void onPause() {
        super.onPause();
        DMVPhoneModel.removeCallStateListener(callStateListener);
        StatService.onPause(MainActivity.this);
        StatService.trackEndPage(MainActivity.this, "主页");
        AnalyUtils.setBAnalyPause(this, "主页");
    }


    /**
     * 获取红点信息成功回调
     *
     * @param info
     */
    @Override
    public void getRedCircleNext(RedCircleGroupBean info) {
        SharedPreferencesUtils.setRedCircle(this, info);
        RedCircleControlUtil.setRadioButtonVisiable(this, mainHousekeeper, mainLife, mainCircle, mainPersonalCenter);
        if (houseKeeperFragment != null)
            houseKeeperFragment.getRedCircleState(this);
        if (personalCenterFragment != null)
            personalCenterFragment.getRedCircleState(this);
    }

    public void setDefaultFragment() {
        houseKeeperFragment = new HouseKeeperFragment();
        transaction.add(R.id.main_layout, houseKeeperFragment);
        transaction.commit();
        mBottomGroup.check(R.id.main_housekeeper);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (checkedId) {
            //管家
            case R.id.main_housekeeper:
                if (houseKeeperFragment == null)
                    houseKeeperFragment = new HouseKeeperFragment();
                transaction.replace(R.id.main_layout, houseKeeperFragment);
                transaction.commit();
                AnalyUtils.addAnaly(10047);
                break;
            //生活
            case R.id.main_life:
                if (lifeFragment == null)
                    lifeFragment = new LifeFragment();
                transaction.replace(R.id.main_layout, lifeFragment);
                transaction.commit();
                AnalyUtils.addAnaly(10048);
                break;

            //圈子
            case R.id.main_circle:
                if (socialCircleFragment == null)
                    socialCircleFragment = new SocialCircleFragment();
                transaction.replace(R.id.main_layout, socialCircleFragment);
                transaction.commit();
                AnalyUtils.addAnaly(10049);
                break;

            //个人中心
            case R.id.main_personalCenter:
                if (personalCenterFragment == null)
                    personalCenterFragment = new PersonalCenterFragment();
                transaction.replace(R.id.main_layout, personalCenterFragment);
                transaction.commit();
                AnalyUtils.addAnaly(10046);
                break;
        }
    }

    private boolean isBind = false;

    /**
     * 开启计步服务
     */
    private void setupService() {
        sharedPreferencesUtils = new PreferencesUtils(this);
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
        presenter.getUpDate();
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            String planWalk_QTY = (String) sharedPreferencesUtils.getParam("planWalk_QTY", "7000");
            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    sharedPreferencesUtils.setParam("stepCount", stepCount);
                    StepData stepData = new StepData();
                    stepData.setStep(String.valueOf(stepCount));
                    EventBus.getDefault().post(stepData);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(MainActivity.this, msg);
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort(this, getString(R.string.Press_again_to_exit_the_program));
                mExitTime = System.currentTimeMillis();
            } else {
                AppManager.AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断用户是否绑定了房屋
     */
    BindHouseDialog dialog;

    public void showBandingHouseDialog(UserInfo info) {
        boolean isHouse = MyApplication.getBaseUserBean().isShow();
        if (isHouse) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = new BindHouseDialog(this, this, "");
                dialog.show();
                String tx = "您注册的手机号<b><font color='#FF4D63'>" + info.getPhone() + "</font></b>未在物业中心登记为业主，或未被业主授权。请到物业中心登记，或联系业主授权后手动绑定房屋!";
                dialog.setContent(tx);

            } else {
                dialog = new BindHouseDialog(this, this, "");
                dialog.show();
                String tx = "您注册的手机号<b><font color='#FF4D63'>" + info.getPhone() + "</font></b>未在物业中心登记为业主，或未被业主授权。请到物业中心登记，或联系业主授权后手动绑定房屋!";
                dialog.setContent(tx);
            }
            BaseUserBean baseUserBean = MyApplication.getBaseUserBean();
            baseUserBean.setShow(false);
            SharedPreferencesUtils.saveBaseUserBean(this, baseUserBean);
        }
    }

    @Override
    public void onSure(String phone) {
        startActivity(new Intent(this, SelectCommunityActivity.class));
    }

    private DateCallBackInterface dateCallBackInterface = new DateCallBackInterface() {
        @Override
        public void getEmployeesCallBack(String token) {
            if (null != token) {
                DMVPhoneModel.loginVPhoneServer(MyApplication.getBaseUserBean().getPhone(), token, AccountType, MainActivity.this, loginCallback);//登录
            }
        }
    };

    private DMModelCallBack.DMCallback loginCallback = new DMModelCallBack.DMCallback() {
        @Override
        public void setResult(int i, DMException e) {
        }
    };

    private DMModelCallBack.DMCallStateListener callStateListener = new DMModelCallBack.DMCallStateListener() {
        @Override
        public void callState(DMCallState dmCallState, String s) {
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        houseKeeperFragment.showGuid();
    }
}


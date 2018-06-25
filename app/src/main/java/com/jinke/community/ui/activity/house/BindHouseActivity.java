package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.AutoBindBean;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.HouseBean;
import com.jinke.community.bean.RegisterBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.presenter.BindHousePresenter;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.ui.activity.base.SelectCommunityActivity;
import com.jinke.community.ui.adapter.HouseListAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.utils.StatServiceUtils;
import com.jinke.community.view.BindHouseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BindHouseActivity extends BaseActivity<BindHouseView, BindHousePresenter>
        implements BindHouseView, LoadingLayout.OnBindYeslistener, LoadingLayout.OnBindNolistener, AdapterView.OnItemClickListener {
    @Bind(R.id.loadinglayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.fill_list_view)
    FillListView fillListView;

    private List<HouseBean.HouseListBean> houseList = new ArrayList<>();
    private ACache aCache;
    private HouseBean.HouseListBean hosueBean;
    private HouseListAdapter adapter;
    private RegisterBean registerBean = new RegisterBean();
    private String nextPage;//设置下个自动跳转的界面no 选择小区/yes 主页
    private RegisterLoginBean loginBean;


    @Override
    public BindHousePresenter initPresenter() {
        return new BindHousePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bind_house;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_bind_house_title));
        showBackwardView(R.string.empty, false);
        aCache = ACache.get(this);
        loginBean= (RegisterLoginBean) getIntent().getSerializableExtra("RegisterLoginBean");
        registerBean = (RegisterBean) getIntent().getSerializableExtra("registerBean");
        loadingLayout.setStatus(LoadingLayout.Bind_House_Loading);
        loadingLayout.setBindYesListener(this);
        loadingLayout.setBindNoListener(this);
        bindHouse();
//        register();
    }

    /**
     * 绑定房屋
     */
    private void bindHouse() {
        if(loginBean!=null){
            switch (loginBean.getIsHouse()) {
                case "true":
                    Map<String, String> map = new HashMap<>();
                    map.put("accessToken", loginBean.getAccessToken());
                    presenter.autoBindHouse(map);
                    break;

                case "false":
                    nextPage = "no";
                    loadingLayout.setStatus(LoadingLayout.Bind_House_No);
                    break;
            }
        }
    }

    private void register() {
        if (registerBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put("phone", registerBean.getPhone());
            map.put("code", registerBean.getSmsCode());
            map.put("avatar", registerBean.getAvatar());
            map.put("nickName", registerBean.getNickName());
            map.put("sex", registerBean.getSex());
            map.put("source", "App");
            map.put("tokenType", registerBean.getTokenType());
            map.put("exToken", registerBean.getExToken());
            map.put("token", registerBean.getToken());
            presenter.getRegisterData(map);
        }
    }

    @Override
    public void getRegisterDataNext(RegisterLoginBean info) {
        if (info != null && info.getIsLogin() != null) {
            JPushInterface.setAlias(this, 1, info.getAccessToken());//注册极光别名
            BaseUserBean userBean = MyApplication.getBaseUserBean();
            userBean.setAccessToken(info.getAccessToken());
            userBean.setPhone(registerBean.getPhone());
            SharedPreferencesUtils.saveBaseUserBean(this, userBean);
            if (StringUtils.equals(info.getIsLogin(), "false")) {
                switch (info.getIsHouse()) {
                    case "true":
                        Map<String, String> map = new HashMap<>();
                        map.put("accessToken", info.getAccessToken());
                        presenter.autoBindHouse(map);
                        break;

                    case "false":
                        nextPage = "no";
                        loadingLayout.setStatus(LoadingLayout.Bind_House_No);
//                startThread();
                        break;
                }
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

    private void startThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        autoChangeClass();
                    }
                }.start();
            }
        });
    }

    private void autoChangeClass() {
        startActivity(new Intent(this, StringUtils.equals("no", nextPage) ? SelectCommunityActivity.class : MainActivity.class));
        finish();
    }

    @Override
    public void autoBindHouseNext(AutoBindBean info) {
        if (info != null && info.getList() != null) {
            switch (info.getList().size()) {
                case 1:
                    storageHouseList(info);
                    nextPage = "yes";
                    loadingLayout.setStatus(LoadingLayout.Bind_House_Yes);
                    startThread();
                    DefaultHouseBean bean = new DefaultHouseBean();
                    bean.setAddress(info.getList().get(0).getArea());
                    bean.setCommunity_id(info.getList().get(0).getAreaId());
                    bean.setCommunity_name(info.getList().get(0).getProject());
                    bean.setHouse_id(info.getList().get(0).getHouseId());
                    bean.setHouse_name(info.getList().get(0).getHouseName());
                    SharedPreferencesUtils.saveDefaultHouse(this, bean);
                    break;

                default:
                    storageHouseList(info);
                    loadingLayout.setStatus(LoadingLayout.Success);
                    adapter = new HouseListAdapter(this, R.layout.item_house_list, houseList);
                    fillListView.setAdapter(adapter);
                    fillListView.setOnItemClickListener(this);
                    break;
            }
        }
    }

    private void storageHouseList(AutoBindBean info) {
        for (AutoBindBean.ListBean bean : info.getList()) {
            hosueBean = new HouseBean.HouseListBean();
            hosueBean.setHouseName(bean.getHouseName());
            hosueBean.setRole(bean.getRole());
            hosueBean.setName(bean.getName());
            hosueBean.setEsHouseId(bean.getHouseId());
            hosueBean.setArea(bean.getArea());
            hosueBean.setCommunity(bean.getProject());
            houseList.add(hosueBean);
        }
        SharedPreferencesUtils.setUseHouseList(this, houseList);
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showLong(this, msg);
        finish();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void bindYes(View v) {
//        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void bindNo(View v) {
        startActivity(new Intent(this, SelectCommunityActivity.class));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelectPosition(position);
        adapter.notifyDataSetChanged();
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("houseId", houseList.get(position).getEsHouseId());
        presenter.setDefaultHouse(map);
    }

    @Override
    public void setDefaultHouseNext(SetDefaultHouseBean info) {
        DefaultHouseBean defaultHouseBean = SharedPreferencesUtils.getDefaultHouseInfo(this);
        defaultHouseBean.setHouse_name(info.getHouse_name());
        defaultHouseBean.setCommunity_name(info.getCommunity_name());
        defaultHouseBean.setHouse_id(info.getHouse_id());
        defaultHouseBean.setArea_num(String.valueOf(info.getArea_num()));
        defaultHouseBean.setCommunity_id(info.getCommunity_id());
        defaultHouseBean.setCityCode(info.getCityCode());
        defaultHouseBean.setAddress(info.getAddress());
        SharedPreferencesUtils.saveDefaultHouse(this, defaultHouseBean);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatServiceUtils.beginPage(this, "注册绑定房屋");
        AnalyUtils.setBAnalyResume(this, "注册绑定房屋");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatServiceUtils.endPage(this, "注册绑定房屋");
        AnalyUtils.setBAnalyPause(this, "注册绑定房屋");
    }
}


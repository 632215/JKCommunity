package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.view.View;
import android.widget.ScrollView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.MyHousePresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.adapter.HouseAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.IMyHouseView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-31.
 * 我的房屋——使用getHouseList接口获取房屋信息详情
 */

public class MyHouseActivity extends BaseActivity<IMyHouseView, MyHousePresenter>
        implements HouseAdapter.OnDefaultHouseListener, IMyHouseView, LoadingLayout.OnReloadListener, PullToRefreshBase.OnRefreshListener<ScrollView>, LoadingLayout.MyHouseListener {
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.listView)
    FillListView listView;
    HouseAdapter adapter;

    ACache aCache;
    List<HouseListBean.ListBean> list = new ArrayList<>();

    @Override
    public MyHousePresenter initPresenter() {
        return new MyHousePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_house;
    }

    @Override
    protected void initView() {
        setTitle(R.string.house_my_title);
        showBackwardView("", true);
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scrollView.setOnRefreshListener(this);
        scrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        loadingLayout.setOnReloadListener(this);
        loadingLayout.setMyHouseListener(this);
        adapter = new HouseAdapter(this, R.layout.item_my_house, list);
        adapter.setOnDefaultHouseListener(this);
        listView.setAdapter(adapter);
    }

    private void initData() {//加载缓存数据
        aCache = ACache.get(this);
    }

    @OnClick({R.id.tx_add_house})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_add_house:
                AnalyUtils.addAnaly(10042);
                startActivity(new Intent(MyHouseActivity.this, AddHouseActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MyHouseActivity.this);
        StatService.trackBeginPage(MyHouseActivity.this, "我的房屋列表");
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getHouseList(getRequestMap());
        initData();
        AnalyUtils.setBAnalyResume(this, "我的房屋列表");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MyHouseActivity.this);
        StatService.trackEndPage(MyHouseActivity.this, "我的房屋列表");
        AnalyUtils.setBAnalyPause(this, "我的房屋列表");
    }

    //获取房屋列表成功
    @Override
    public void onSuccessBack(HouseListBean bean) {
        list = bean.getList();
        adapter.setDataList(list);
        scrollView.onRefreshComplete();
        aCache.put("HouseListBean", bean, ACache.TIME_DAY);
        loadingLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.MyHouse_Empty);
    }

    //获取房屋列表失败（为空）
    @Override
    public void getHouseListError() {
        HouseListBean bean = null;
        aCache.put("HouseListBean", bean, ACache.TIME_DAY);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        loadingLayout.setStatus(LoadingLayout.MyHouse_Empty);
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void onHouseHouseData(SetDefaultHouseBean bean) {
        BaseUserBean baseUserBean = SharedPreferencesUtils.getBaseUserBean(this);
        baseUserBean.setName(bean.getName());
        baseUserBean.setIdentity(bean.getIdentity());
        SharedPreferencesUtils.saveBaseUserBean(this, baseUserBean);
        SharedPreferencesUtils.clearCommunityId(this);
        presenter.getHouseList(getRequestMap());
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
        list.clear();
        adapter.setDataList(list);
        presenter.getHouseList(getRequestMap());
    }

    public Map<String, String> getRequestMap() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        return map;
    }

    @Override
    public void onReload(View v) {
        switch (v.getId()) {
            case R.id.empty_reload:
                list.clear();
                loadingLayout.setStatus(LoadingLayout.Loading);
                Map<String, String> map = new HashMap<>();
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                presenter.getHouseList(map);
                break;
        }
    }

    @Override
    public void stateDefault(HouseListBean.ListBean bean) {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("houseId", bean.getHouse_id());
        AnalyUtils.addAnaly(10029);
        presenter.getDefaultData(map);
    }

    @Override
    public void showLoading() {
        showProgressDialog("false");
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    /**
     * 房屋为空
     *
     * @param v
     */
    @Override
    public void myHouse(View v) {
        Intent payment = new Intent(this, WebActivity.class);
        payment.putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_QUESTION);
        payment.putExtra("title", getString(R.string.add_house_url));
        startActivity(payment);
    }
}

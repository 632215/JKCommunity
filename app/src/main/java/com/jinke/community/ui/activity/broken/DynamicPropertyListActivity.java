package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.DynamicBrokenListPresenter;
import com.jinke.community.ui.adapter.DynamicPropertyAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.IDynamicBrokenListView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-14.
 * 作废
 */

public class DynamicPropertyListActivity extends BaseActivity<IDynamicBrokenListView, DynamicBrokenListPresenter> implements PullToRefreshBase.OnRefreshListener2<ScrollView>, AdapterView.OnItemClickListener, IDynamicBrokenListView {
    @Bind(R.id.listView)
    FillListView listView;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    List<BrokeNoticeListBean.ListBean> list = new ArrayList<>();
    DynamicPropertyAdapter adapter;

    @Override
    public DynamicBrokenListPresenter initPresenter() {
        return new DynamicBrokenListPresenter(this);
    }

    DefaultHouseBean defaultHouseBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_broken_dynamic_list;
    }

    int page = 1;

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra("title"));
        showBackwardView("", true);
        defaultHouseBean = SharedPreferencesUtils.getDefaultHouseInfo(this);
        presenter.getPostItNoticeList(getMap(page));
        adapter = new DynamicPropertyAdapter(this, R.layout.item_dynamic_broken_list, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(this);
        scrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        scrollView.getLoadingLayoutProxy(false, true).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
        loadingLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Error);
    }

    public Map<String, String> getMap(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("communityId", defaultHouseBean.getCommunity_id());
        map.put("page", page + "");
        map.put("rows", "10");
        return map;
    }

    @Override
    public void onBrokeNewsNoticeList(BrokeNoticeListBean bean) {
        list.addAll(bean.getList());
        adapter.setDataList(list);
        loadingLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        scrollView.onRefreshComplete();
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
    }

    @Override
    public void getCommunityNext(UserCommunityBean info) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BrokeNoticeListBean.ListBean listBean = (BrokeNoticeListBean.ListBean) adapter.getItem(position);
        Intent intent = new Intent(DynamicPropertyListActivity.this, PropertyWebActivity.class);
        intent.putExtra("noticeId", listBean.getNoticeId() + "");
        startActivity(intent);
        AppConfig.trackCustomEvent(this, "DynamicPropertyListActivity_click", "物业动态详情");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
        list.clear();
        page = 1;
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getPostItNoticeList(getMap(page));
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
        page++;
        presenter.getPostItNoticeList(getMap(page));
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(DynamicPropertyListActivity.this);
        StatService.trackBeginPage(DynamicPropertyListActivity.this, "爆料报事列表");
        AnalyUtils.setBAnalyResume(this, "爆料报事列表");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(DynamicPropertyListActivity.this);
        StatService.trackEndPage(DynamicPropertyListActivity.this, "爆料报事列表");
        AnalyUtils.setBAnalyPause(this, "爆料报事列表");
    }
}

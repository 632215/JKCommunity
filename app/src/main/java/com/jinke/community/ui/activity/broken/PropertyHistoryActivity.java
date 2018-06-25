package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.presenter.PropertyHistoryPresent;
import com.jinke.community.ui.adapter.PropertyBrokenRecycleAdapter;
import com.jinke.community.ui.adapter.PropertyHistoryAdapter;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IPropertyHistoryView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-6.
 * 报事记录
 */

public class PropertyHistoryActivity extends BaseActivity<IPropertyHistoryView, PropertyHistoryPresent> implements PullToRefreshBase.OnRefreshListener2, IPropertyHistoryView, LoadingLayout.AddBreakListener, PropertyBrokenRecycleAdapter.AccessListener {
    @Bind(R.id.listView)
    FillRecyclerView listView;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    PropertyBrokenRecycleAdapter propertyBrokenAdapter;
    List<PropertyBean.ListBean> propertyBrokenList = new ArrayList<>();
    int page = 1;

    private int flag = 2;//新版本报事为1，旧版本为0
    PropertyHistoryAdapter propertyHistoryAdapter;

    @Override
    public PropertyHistoryPresent initPresenter() {
        return new PropertyHistoryPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_property_history;
    }

    @Override
    protected void initView() {
        setTitle("报事记录");
        showBackwardView("", true);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        scrollView.getLoadingLayoutProxy(false, true).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        loadingLayout.setAddBreakListener(this);
        presenter.getConfig();//获取配置试点小区
    }

    /**
     * 获取配置试点小区 成功回调
     *
     * @param info
     */
    @Override
    public void getConfigNext(CommunityBean info) {
        if (info != null)
            for (CommunityBean.ListBean bean : info.getList())
                if (StringUtils.equals("property_broken_test", bean.getAuthority_code())) {
                    flag = info != null && (StringUtils.equals("1", bean.getStatus())) ? 1 : 0;
                }
        setAdapter();
    }

    /**
     * 获取配置试点失败 成功回调
     */
    @Override
    public void getConfigError() {
        flag = 0;
        setAdapter();
    }

    private void setAdapter() {
        switch (flag) {
            case 0:
                propertyBrokenAdapter = new PropertyBrokenRecycleAdapter(this, propertyBrokenList);
                propertyBrokenAdapter.setListener(this);
                listView.setLayoutManager(new LinearLayoutManager(this));
                listView.setAdapter(propertyBrokenAdapter);
                break;
            case 1:
                propertyHistoryAdapter = new PropertyHistoryAdapter(this, propertyBrokenList);
                listView.setLayoutManager(new LinearLayoutManager(this));
                listView.setAdapter(propertyHistoryAdapter);
                break;
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    /**
     * 物业报事获取的结果
     *
     * @param bean
     */
    @Override
    public void getKeepPostItListNext(PropertyBean bean) {
        propertyBrokenList.addAll(bean.getList());
        switch (flag) {
            case 0:
                propertyBrokenAdapter.setDataList(propertyBrokenList);
                break;
            case 1:
                propertyHistoryAdapter.setDataList(propertyBrokenList);
                break;
        }
        if (propertyBrokenList.size() > 0) {
            loadingLayout.setStatus(LoadingLayout.Success);
        } else {
            loadingLayout.setBreakPageTips("您还没有报事记录哦！");
            loadingLayout.setBreakPageButton("添加报事");
            loadingLayout.setStatus(LoadingLayout.Break_stage_Empty);
        }
        scrollView.onRefreshComplete();
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        propertyBrokenList.clear();
        page = 1;
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getPostItList(getMap(page));
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        page++;
        presenter.getPostItList(getMap(page));
    }

    public HashMap getMap(int page) {
        HashMap map = new HashMap();
        if (MyApplication.getInstance().getDefaultHouse() == null)
            return null;
        map.put("houseId", MyApplication.getInstance().getDefaultHouse().getHouse_id());
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("page", page + "");
        map.put("rows", "10");
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        propertyBrokenList.clear();
        page = 1;
        presenter.getPostItList(getMap(page));
        StatService.onResume(PropertyHistoryActivity.this);
        StatService.trackBeginPage(PropertyHistoryActivity.this, "报事记录");
        AnalyUtils.setBAnalyResume(this, "报事记录");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PropertyHistoryActivity.this);
        StatService.trackEndPage(PropertyHistoryActivity.this, "报事记录");
        AnalyUtils.setBAnalyPause(this, "报事记录");
    }

    @Override
    public void addBreak(View v) {
        startActivity(new Intent(this, PropertyNewsActivity.class));
        finish();
    }

    @Override
    public void clickAccess(PropertyBean.ListBean bean) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("postId", bean.getKeepId());
        startActivity(intent);
    }

    @Override
    public void itemClick(PropertyBean.ListBean bean) {
        Intent intent = new Intent(this, PropertyDetailsActivity.class);
        intent.putExtra("postId", bean.getKeepId());
        startActivity(intent);
    }
}

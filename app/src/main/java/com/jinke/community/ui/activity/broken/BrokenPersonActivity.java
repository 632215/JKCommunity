package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BrokenPersonBean;
import com.jinke.community.presenter.BrokenPersonPresenter;
import com.jinke.community.ui.adapter.BrokenPersonRecycleAdapter;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.BrokenPersonView;
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
 * Created by Administrator on 2017/11/28.
 * 个人爆料记录
 */
public class BrokenPersonActivity extends BaseActivity<BrokenPersonView, BrokenPersonPresenter> implements
        PullToRefreshBase.OnRefreshListener2, BrokenPersonView, LoadingLayout.AddBreakListener {

    @Bind(R.id.listView)
    FillRecyclerView listView;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    int page = 1;
    BrokenPersonRecycleAdapter adapter;
    List<BrokenPersonBean.ListBean> list = new ArrayList<>();

    @Override
    public BrokenPersonPresenter initPresenter() {
        return new BrokenPersonPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_broken_person_history;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_broken_person_history_title);
        showBackwardView("", true);
        showForwardViewColor(getResources().getColor(R.color.loaclayout_color3));
        showForwardView(R.string.activity_broken_person_history_right_text, true);
        adapter = new BrokenPersonRecycleAdapter(this, list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        scrollView.getLoadingLayoutProxy(false, true).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        loadingLayout.setAddBreakListener(this);
    }

    @Override
    public void getBrokePersonNext(BrokenPersonBean bean) {
        list.addAll(bean.getList());
        adapter.setDataList(list);
        loadingLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Break_stage_Empty);
        scrollView.onRefreshComplete();
    }

    @Override
    public void showMsg(String msg) {
        loadingLayout.setStatus(LoadingLayout.Success);
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        AnalyUtils.addAnaly(10037);
        startActivity(new Intent(this, BrokeStageActivity.class));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        list.clear();
        page = 1;
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getBrokePerson(getMap(page));
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        page++;
        presenter.getBrokePerson(getMap(page));
    }

    public Map<String, String> getMap(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("page", page + "");
        map.put("rows", "10");
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        list.clear();
        presenter.getBrokePerson(getMap(page));
        AnalyUtils.addAnaly(10021);
        StatService.onResume(BrokenPersonActivity.this);
        StatService.trackBeginPage(BrokenPersonActivity.this, "个人爆料记录");
        AnalyUtils.setBAnalyResume(this, "个人爆料记录");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BrokenPersonActivity.this);
        StatService.trackEndPage(BrokenPersonActivity.this, "个人爆料记录");
        AnalyUtils.setBAnalyPause(this, "个人爆料记录");
    }

    @Override
    public void addBreak(View v) {
        finish();
    }
}

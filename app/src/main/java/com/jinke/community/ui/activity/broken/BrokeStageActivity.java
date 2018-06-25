package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.presenter.BrokeStagePresenter;
import com.jinke.community.ui.adapter.RecyclerViewAdapter;
import com.jinke.community.ui.toast.SelectCommunityDialog;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.BrokeStageView;
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
 * Created by Administrator on 2017/11/28.
 * 爆料台
 */

public class BrokeStageActivity extends BaseActivity<BrokeStageView, BrokeStagePresenter> implements BrokeStageView,
        PullToRefreshBase.OnRefreshListener2, LoadingLayout.AddBreakListener, SelectCommunityDialog.SelectCommunityListener, RecyclerViewAdapter.PraiseListener {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tx_address)
    TextView txAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.listView)
    FillRecyclerView listView;

    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.tx_add_break)
    TextView txAddBreak;
    int page = 1;

    private SelectCommunityDialog selectCommunityDialog;
    private RecyclerViewAdapter adapter;

    private List<NoticeListBean.ListBean> breakList = new ArrayList<>();
    private List<UserCommunityBean.ListBean> communityList = new ArrayList<>();
    private UserCommunityBean.ListBean communityBean;

    @Override
    public BrokeStagePresenter initPresenter() {
        return new BrokeStagePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_broken_stage;
    }

    @Override
    protected void initView() {
        setTitle("小金妹爆料台");
        showBackwardView("", true);
        adapter = new RecyclerViewAdapter(this, breakList);
        adapter.setListener(this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);

        loadingLayout.setAddBreakListener(this);
        scrollView.setOnRefreshListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.getLoadingLayoutProxy(true,false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        scrollView.getLoadingLayoutProxy(false,true).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getCommunity();//获取当前用户所属小区列表
    }

    @Override
    public void getCommunityNext(UserCommunityBean info) {
        if (info != null && info.getList() != null) {
            communityList = info.getList();
            loadingLayout.setStatus(LoadingLayout.Success);
            for (UserCommunityBean.ListBean listBean : info.getList()) {
                if (StringUtils.equals(listBean.getEsProjectId(), MyApplication.getInstance().getDefaultHouse().getCommunity_id())) {
                    communityBean = listBean;
                }
            }
            if (communityBean != null)
                txAddress.setText(communityBean.getEsProjectName());
            rlAddress.setClickable(communityList.size() > 1 ? true : false);
            txSelect.setVisibility(communityList.size() > 1 ? View.VISIBLE : View.GONE);
            presenter.getStageBrokeList(getMap(page));
        } else {
            loadingLayout.setBreakPageTips("暂无爆料记录！");
            txAddBreak.setVisibility(View.GONE);
            loadingLayout.setStatus(LoadingLayout.Break_stage_Empty);
        }
    }

    @Override
    public void getStageBrokeListNext(NoticeListBean info) {
        if (info.getList().size() == 0) {
            scrollView.getLoadingLayoutProxy(true, false).setPullLabel("没有更多了");
        }
        breakList.addAll(info.getList());
        adapter.setDataList(breakList);
        if (breakList.size() > 0) {
            txAddBreak.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(LoadingLayout.Success);
        } else {
            loadingLayout.setBreakPageTips("暂无爆料记录！");
            txAddBreak.setVisibility(View.GONE);
            loadingLayout.setStatus(LoadingLayout.Break_stage_Empty);
        }
        scrollView.onRefreshComplete();
    }

    @OnClick({R.id.rl_address, R.id.tx_add_break})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                if (selectCommunityDialog == null) {
                    selectCommunityDialog = new SelectCommunityDialog(this, this, "选择小区");
                }
                selectCommunityDialog.setCommunityList(communityList);
                selectCommunityDialog.show();
                break;

            case R.id.tx_add_break:
                AnalyUtils.addAnaly(10036);
                startActivity(new Intent(this, BrokeUpActivity.class));
                finish();
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
        if (breakList != null && breakList.size() > 0) {
            txAddBreak.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(LoadingLayout.Success);
        } else {
            loadingLayout.setBreakPageTips("暂无爆料记录！");
            txAddBreak.setVisibility(View.GONE);
            loadingLayout.setStatus(LoadingLayout.Break_stage_Empty);
        }
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        breakList.clear();
        page = 1;
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getStageBrokeList(getMap(page));
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        page++;
        presenter.getStageBrokeList(getMap(page));
    }

    public Map<String, String> getMap(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        if (communityBean != null)
            map.put("communityId", communityBean.getEsProjectId());
        map.put("page", page + "");
        map.put("rows", "10");
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(10022);
        StatService.onResume(BrokeStageActivity.this);
        StatService.trackBeginPage(BrokeStageActivity.this, "小金妹爆料台");
        AnalyUtils.setBAnalyResume(this, "小金妹爆料台");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BrokeStageActivity.this);
        StatService.trackEndPage(BrokeStageActivity.this, "小金妹爆料台");
        AnalyUtils.setBAnalyPause(this, "小金妹爆料台");
    }

    @Override
    public void praiseClick(int position) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("jmOrderId", String.valueOf(breakList.get(position).getNoticeId()));
        presenter.praiseClick(map);
    }

    @Override
    public void praiseClickNext(PraiseresultBean info) {
    }

    @Override
    public void addBreak(View v) {
        startActivity(new Intent(this, BrokeUpActivity.class));
        finish();
    }

    @Override
    public void selectCommunity(UserCommunityBean.ListBean bean) {
        breakList.clear();
        page = 1;
        communityBean = bean;
        txAddress.setText(communityBean.getEsProjectName());
        presenter.getStageBrokeList(getMap(page));
    }
}
package com.jinke.community.ui.activity.base;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.presenter.MsgCenterPresenter;
import com.jinke.community.ui.adapter.MsgCenterAdapter;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.MsgCenterView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/6/7.
 */

public class MsgCenterActivity extends BaseActivity<MsgCenterView, MsgCenterPresenter> implements
        PullToRefreshScrollView.OnRefreshListener2, MsgCenterView, MsgCenterAdapter.MsgCenterAdapterListener {
    @Bind(R.id.loadinglayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.refresh_view)
    PullToRefreshScrollView refreshScrollView;
    @Bind(R.id.fill_recycler_view)
    FillRecyclerView fillRecyclerView;

    private int page = 1;
    private MsgCenterAdapter adapter = null;
    private List<MsgBean.ListBean> list = null;
    private MsgBean msgBean = null;

    @Override
    public MsgCenterPresenter initPresenter() {
        return new MsgCenterPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_msg_center;
    }

    @Override
    protected void initView() {
        showBackwardView("", true);
        setTitle(R.string.activity_msgcenter_title);
        msgBean = (MsgBean) getIntent().getSerializableExtra("MsgBean");
        refreshScrollView.onRefreshComplete();
        refreshScrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        refreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshScrollView.setOnRefreshListener(this);
        initDta();
        if (msgBean != null)
            getMsgNext(msgBean);
        else
            presenter.getMsg(page);
    }

    private void initDta() {
        if (list == null)
            list = new ArrayList<>();
        if (adapter == null)
            adapter = new MsgCenterAdapter(list, this, this);
        fillRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fillRecyclerView.setAdapter(adapter);
    }

    /**
     * 请求信息中心接口 成功回调
     *
     * @param info
     */
    @Override
    public void getMsgNext(MsgBean info) {
        if (info != null && info.getList() != null && info.getList().size() > 0) {
            list = info.getList();
            loadingLayout.setStatus(LoadingLayout.Success);
            adapter.setData(list);
        } else {//缺少数据为空的界面
            loadingLayout.setBreakPageTips("暂无消息");
            loadingLayout.setBreakPageButtonVisibility(View.GONE);
            loadingLayout.setStatus(LoadingLayout.Break_stage_Empty);
        }
        if (refreshScrollView != null)
            refreshScrollView.onRefreshComplete();
    }

    @Override
    public void getMsgError(String msg) {
        loadingLayout.setStatus(LoadingLayout.No_Network);
        if (refreshScrollView != null)
            refreshScrollView.onRefreshComplete();
        ToastUtils.showShort(this, msg);
    }

    /**
     * adapter 点击回调
     *
     * @param positon
     */
    @Override
    public void upDateMsg(int positon) {
        presenter.upDateMsg(list.get(positon).getId());
        this.position = positon;
        list.get(positon).setIsRead("0");
        adapter.setData(list);
    }

    private int position = 0;

    @Override
    public void upDateMsgNext() {
        list.get(position).setIsRead("0");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        page = 1;
        list.clear();
        presenter.getMsg(page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        page++;
        presenter.getMsg(page);
    }

    @Override
    public void showLoading() {
        loadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MsgCenterActivity.this);
        StatService.trackBeginPage(MsgCenterActivity.this, "消息中心");
        AnalyUtils.setBAnalyResume(this, "消息中心");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onResume(MsgCenterActivity.this);
        StatService.trackBeginPage(MsgCenterActivity.this, "消息中心");
        AnalyUtils.setBAnalyPause(this, "消息中心");
    }
}

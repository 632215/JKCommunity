package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BaseView;
import com.jinke.community.bean.NoteBean;
import com.jinke.community.presenter.NotePresenter;
import com.jinke.community.ui.adapter.NoteRecyclerAdapter;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.FlowLayoutManager;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.NoteView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.commont.StringUtils;


/**
 * 标签页
 * Created by Administrator on 2018/3/23.
 */
public class NoteActivity extends BaseActivity<NoteView, NotePresenter> implements PullToRefreshBase.OnRefreshListener, BaseView, NoteView, NoteRecyclerAdapter.NoteRecyclerAdapterListener {
    @Bind(R.id.pull_to_refresh_image)
    PullToRefreshScrollView pullToRefreshScrollView;
    @Bind(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @Bind(R.id.tx_title)
    TextView txTitle;
    @Bind(R.id.rv_note)
    FillRecyclerView rvNote;
    @Bind(R.id.tx_sure)
    TextView txSure;

    private String title = null;
    private NoteRecyclerAdapter adapter = null;
    private List<NoteBean.ListBean> mList = new ArrayList<>();

    @Override
    public NotePresenter initPresenter() {
        return new NotePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_note;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_note_title));
        showBackwardView("", true);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
        pullToRefreshScrollView.setOnRefreshListener((this));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        loadinglayout.setStatus(LoadingLayout.Success);
        title = getIntent().getStringExtra("title");
        if (!StringUtils.isEmpty(title)) {//技能skill 兴趣interest
            txTitle.setText(StringUtils.equals("skill", title) ? getString(R.string.activity_note_skill) : getString(R.string.activity_note_interest));
            presenter.getNote(StringUtils.equals("skill", title) ? "skill" : "interest");
        }
        adapter = new NoteRecyclerAdapter(mList, this);
        rvNote.setLayoutManager(new FlowLayoutManager(this, true));
        rvNote.setAdapter(adapter);
        adapter.setListener(this);
    }

    /**
     * 获取标签成功回调
     *
     * @param info
     */
    @Override
    public void getNoteNext(NoteBean info) {
        if (info.getList() != null) {
            mList.clear();
            mList = info.getList();
            adapter.setData(mList);
            changUi(mList);
        }
    }

    @OnClick(R.id.tx_sure)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_sure:
                presenter.setNote(adapter.getData());
                break;
        }
    }

    /**
     * 设置标签成功回调
     */
    @Override
    public void setNoteNext() {
        finish();
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        presenter.getNote(StringUtils.equals("skill", title) ? "skill" : "interest");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog("");
    }

    @Override
    public void hideLoading() {
        hideDialog();
        pullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onErrorMsg(String msg) {

    }

    /**
     * 适配器回调
     *
     * @param mList
     */
    @Override
    public void noteClick(List<NoteBean.ListBean> mList) {
        changUi(mList);
    }

    //更改样式
    private void changUi(List<NoteBean.ListBean> mList) {
        int count = 0;
        for (NoteBean.ListBean bean : mList) {
            if (bean.getIsMark() == 1)
                count++;
        }
        txSure.setAlpha(count > 0 ? 1 : (float) 0.7);
        txSure.setEnabled(count > 0 ? true : false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this, "标签页");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "标签页");
    }
}

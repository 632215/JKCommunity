package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.presenter.AnnouncementPresenter;
import com.jinke.community.ui.activity.base.SelectCommunityActivity;
import com.jinke.community.ui.adapter.AnnouncementAdapter;
import com.jinke.community.ui.toast.SelectCommunityDialog;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.AnnouncementView;
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
 * Created by root on 17-8-14.
 * 通知公告
 * <p>
 * a、如果已经绑定房屋，则显示房屋所在社区，如图
 * b、如果没有绑定房屋，则可以切换小区，查看任意社区的通知公告。（
 */

public class AnnouncementActivity extends BaseActivity<AnnouncementView, AnnouncementPresenter>
        implements AnnouncementView, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView>, SelectCommunityDialog.SelectCommunityListener {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tx_address)
    TextView txAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.listView)
    FillListView listView;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.tx_tips)
    TextView txTips;

    private List<BrokeNoticeListBean.ListBean> list = new ArrayList<>();
    private AnnouncementAdapter adapter;
    private SelectCommunityDialog selectCommunityDialog;
    int page = 1;
    private List<UserCommunityBean.ListBean> communityList = new ArrayList<>();
    private String communityId = "";
    private String communityName = "";
    private ACache aCache;
    private HouseListBean houseListBean;

    @Override
    public AnnouncementPresenter initPresenter() {
        return new AnnouncementPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        setTitle(R.string.activity_broken_dynamic_title);
        showBackwardView("", true);
        adapter = new AnnouncementAdapter(this, R.layout.item_dynamic_broken_list, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(this);
        scrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        scrollView.getLoadingLayoutProxy(false, true).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        initData();
    }

    //判断是否有默认房屋
    private void initData() {
        if (houseListBean != null && houseListBean.getList() != null && houseListBean.getList().size() > 0) {
            loadingLayout.setStatus(LoadingLayout.Loading);
            presenter.getCommunity();//获取当前用户所属小区列表
        } else if (!StringUtils.isEmpty(SharedPreferencesUtils.getCommunityId(this))) {
            String communityInfo = SharedPreferencesUtils.getCommunityId(this);
            communityId = communityInfo.substring(communityInfo.indexOf(",") + 1);
            communityName = communityInfo.substring(0, communityInfo.indexOf(","));
            txAddress.setText(communityName);
            presenter.addPostItNoticeList();
        } else {
            txAddress.setText("");
            loadingLayout.setStatus(LoadingLayout.Announcement_Empty);
        }
    }

    @Override
    public void getCommunityNext(UserCommunityBean info) {
        if (info != null && info.getList() != null) {
            communityList = info.getList();
            loadingLayout.setStatus(LoadingLayout.Success);
            if (MyApplication.getInstance().getDefaultHouse() != null) {
                for (UserCommunityBean.ListBean listBean : info.getList()) {
                    if (StringUtils.equals(listBean.getEsProjectId(), MyApplication.getInstance().getDefaultHouse().getCommunity_id())) {
                        communityId = listBean.getEsProjectId();
                        communityName = listBean.getEsProjectName();
                    }
                }
            } else {
                communityId = communityList.get(0).getEsProjectId();
                communityName = communityList.get(0).getEsProjectName();
            }
            txAddress.setText(communityName);
            rlAddress.setClickable(communityList.size() > 1 ? true : false);
            txSelect.setVisibility(communityList.size() > 1 ? View.VISIBLE : View.GONE);
            presenter.getPostItNoticeList(getMap(page));
        } else {
            loadingLayout.setStatus(LoadingLayout.Announcement_Empty);
        }
    }

    @Override
    public void getPostItNoticeListNext(BrokeNoticeListBean info) {
        if (info != null && info.getList() != null) {
            list.addAll(info.getList());
            adapter.setDataList(list);
            loadingLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Announcement_Empty);
            scrollView.onRefreshComplete();
        }
        txTips.setVisibility(list.size() > 0 && info.getList().size() == 0 ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.rl_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                if (houseListBean != null && houseListBean.getList() != null && houseListBean.getList().size() > 0) {
                    if (selectCommunityDialog == null) {
                        selectCommunityDialog = new SelectCommunityDialog(this, this, "选择小区");
                    }
                    selectCommunityDialog.setCommunityList(communityList);
                    selectCommunityDialog.show();
                } else {
                    Intent intent = new Intent(this, SelectCommunityActivity.class);
                    intent.putExtra("last_activity", "announcement");
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        loadingLayout.setStatus(LoadingLayout.Success);
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
        loadingLayout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Announcement_Empty);
    }

    public Map<String, String> getMap(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("communityId", communityId);
        map.put("page", page + "");
        map.put("rows", "10");
        return map;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BrokeNoticeListBean.ListBean listBean = (BrokeNoticeListBean.ListBean) adapter.getItem(position);
        Intent intent = new Intent(AnnouncementActivity.this, PropertyWebActivity.class);
        intent.putExtra("noticeId", listBean.getNoticeId() + "");
        startActivity(intent);
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
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void selectCommunity(UserCommunityBean.ListBean bean) {
        list.clear();
        page = 1;
        communityId = bean.getEsProjectId();
        communityName = bean.getEsProjectName();
        txAddress.setText(communityName);
        presenter.getPostItNoticeList(getMap(page));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getDefaultHouse() == null && !StringUtils.isEmpty(SharedPreferencesUtils.getCommunityId(this))) {
            String communityInfo = SharedPreferencesUtils.getCommunityId(this);
            communityId = communityInfo.substring(communityInfo.indexOf(",") + 1);
            communityName = communityInfo.substring(0, communityInfo.indexOf(","));
            txAddress.setText(communityName);
            list.clear();
            page = 1;
            loadingLayout.setStatus(LoadingLayout.Loading);
            presenter.getPostItNoticeList(getMap(page));
        }
        AnalyUtils.addAnaly(10024);
        StatService.onResume(AnnouncementActivity.this);
        StatService.trackBeginPage(AnnouncementActivity.this, "通知公告");
        AnalyUtils.setBAnalyResume(this, "通知公告");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AnnouncementActivity.this);
        StatService.trackEndPage(AnnouncementActivity.this, "通知公告");
        AnalyUtils.setBAnalyPause(this, "通知公告");
    }
}

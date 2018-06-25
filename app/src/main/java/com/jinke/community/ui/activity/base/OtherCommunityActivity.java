package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.presenter.OtherCommunityPresenter;
import com.jinke.community.ui.adapter.LeftListViewAdapter;
import com.jinke.community.ui.adapter.RightListAdapter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IOtherCommunityView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-1.
 */

public class OtherCommunityActivity extends BaseActivity<IOtherCommunityView, OtherCommunityPresenter> implements AdapterView.OnItemClickListener, ExpandableListView.OnGroupClickListener, IOtherCommunityView {
    @Bind(R.id.left_listView)
    ListView listView;
    @Bind(R.id.right_listView)
    ExpandableListView rightListView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    @Bind(R.id.expand_loadView)
    LoadingLayout expandLoadView;
    LeftListViewAdapter leftAdapter;
    List<CityBean.ListBean> left = new ArrayList<>();
    RightListAdapter rightListAdapter;
    Map<String, String> map = new HashMap<>();
    private List<CommunityListBean.ListBean> list = new ArrayList<>();

    @Override
    public OtherCommunityPresenter initPresenter() {
        return new OtherCommunityPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_other_community;
    }

    @Override
    protected void initView() {
        setTitle(R.string.select_community_title);
        showBackwardViewIco(R.drawable.icon_main_title_back);
        leftAdapter = new LeftListViewAdapter(OtherCommunityActivity.this, R.layout.item_left_listview, left);
        listView.setAdapter(leftAdapter);
        rightListAdapter = new RightListAdapter(this, list);
        rightListView.setAdapter(rightListAdapter);
        getExpandableView(rightListAdapter, rightListView);
        rightListView.setOnGroupClickListener(this);
        //获取城市列表信息
        presenter.getCityList(map);
        listView.setOnItemClickListener(this);

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    /**
     * 展开全部ｃｈｉｌｄ项
     *
     * @param rightListAdapter
     * @param rightListView
     */
    public void getExpandableView(RightListAdapter rightListAdapter, ExpandableListView rightListView) {
        for (int i = 0; i < rightListAdapter.getGroupCount(); i++) {
            rightListView.expandGroup(i);
        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(OtherCommunityActivity.this, msg);
    }

    @Override
    public void showCityData(CityBean bean) {
        left = bean.getList();
        left.get(0).setSelet(true);
        leftAdapter.setDataList(left);
        loadingLayout.setStatus(LoadingLayout.Success);
        expandLoadView.setStatus(LoadingLayout.Loading);
        Map<String, String> communityMap = new HashMap<>();
        communityMap.put("cityId", left.get(0).getId() + "");
        presenter.getCommunityList(communityMap);
    }

    @Override
    public void showCommunityListData(CommunityListBean bean) {

        list = bean.getList();
        rightListAdapter.setData(list);
        getExpandableView(rightListAdapter, rightListView);
        expandLoadView.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Community_Empty);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (CityBean.ListBean bean : left) {
            bean.setSelet(false);
        }
        left.get(position).setSelet(true);
        leftAdapter.setDataList(left);

        Map<String, String> communityMap = new HashMap<>();
        communityMap.put("cityId", left.get(position).getId() + "");
        presenter.getCommunityList(communityMap);
        expandLoadView.setStatus(LoadingLayout.Loading);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(OtherCommunityActivity.this);
        StatService.trackBeginPage(OtherCommunityActivity.this, "选择小区");
        AnalyUtils.setBAnalyResume(this, "选择小区");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(OtherCommunityActivity.this);
        StatService.trackEndPage(OtherCommunityActivity.this, "选择小区");
        AnalyUtils.setBAnalyPause(this, "选择小区");
    }
}

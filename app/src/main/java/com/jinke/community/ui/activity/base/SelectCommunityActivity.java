package com.jinke.community.ui.activity.base;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.bean.LocationBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.SelectCommunityPresenter;
import com.jinke.community.ui.adapter.LeftListViewAdapter;
import com.jinke.community.ui.adapter.RightListAdapter;
import com.jinke.community.ui.adapter.SelectCommunityAdapter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.ISelectCommunityView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.NetWorksUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-1.
 */

public class SelectCommunityActivity extends BaseActivity<ISelectCommunityView, SelectCommunityPresenter> implements
        RightListAdapter.onSelectCommunityListener, ExpandableListView.OnGroupClickListener,
        LoadingLayout.OnReloadListener, AdapterView.OnItemClickListener, ISelectCommunityView {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.select_other_community)
    TextView otherCommunity;
    @Bind(R.id.tx_select_community_location)
    TextView txLocation;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.left_listView)
    ListView leftView;
    @Bind(R.id.right_listView)
    ExpandableListView rightListView;
    @Bind(R.id.expand_loadView)
    LoadingLayout expandLoadView;
    @Bind(R.id.select_community_view)
    LinearLayout communityView;
    @Bind(R.id.ed_community_search)
    EditText communitySearch;

    LeftListViewAdapter leftAdapter;
    SelectCommunityAdapter adapter;
    RightListAdapter rightListAdapter;

    List<CommunityGPSBean.ListBean> list = new ArrayList<>();
    List<CityBean.ListBean> leftList = new ArrayList<>();
    List<CommunityListBean.ListBean> rightList = new ArrayList<>();

    public static SelectCommunityActivity selectCommunityInstance = null;
    private Map<String, String> cityMap = new HashMap<>();
    Map<String, String> locationMap = new HashMap<>();

    private String last_activity = "";

    @Override
    public SelectCommunityPresenter initPresenter() {
        return new SelectCommunityPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_select_community;
    }

    @Override
    protected void initView() {
        communitySearch.addTextChangedListener(new EmTextWatch(communitySearch, this));
        last_activity = getIntent().getStringExtra("last_activity");
        expandLoadView.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Success : LoadingLayout.No_Network);
        selectCommunityInstance = this;
        setTitle(R.string.select_community_title);
        showBackwardView(R.string.empty, true);

        PermissionGen.with(SelectCommunityActivity.this)
                .addRequestCode(105)
                .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .request();
        adapter = new SelectCommunityAdapter(SelectCommunityActivity.this, R.layout.item_select_community, list);
        leftAdapter = new LeftListViewAdapter(SelectCommunityActivity.this, R.layout.item_left_listview, leftList);
        rightListAdapter = new RightListAdapter(this, rightList);

        rightListView.setAdapter(rightListAdapter);
        leftView.setAdapter(leftAdapter);
        listView.setAdapter(adapter);

        rightListView.setOnGroupClickListener(this);
        listView.setOnItemClickListener(this);
        rightListAdapter.setOnSelectCommunityListener(this);
        checkNetWork();
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getLocationInfo();

        leftView.setOnItemClickListener(leftListener);
        loadingLayout.setOnReloadListener(this);
        expandLoadView.setOnReloadListener(this);
    }

    @Override
    public void showLocationInfo(LocationBean bean) {
        txLocation.setText("当前城市:" + bean.getCity() + "-" + bean.getDistrict());
        locationMap.put("lat", bean.getLatitude());
        locationMap.put("lng", bean.getLongitude());
        presenter.getCommunityListByGps(locationMap);
    }

    @Override
    public void locationBackFailed(int errorCode) {
        checkNetWork();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.select_other_community, R.id.community_search, R.id.tx_select_community_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_other_community:
                AppConfig.trackCustomEvent(this, "community_search_click", "选择小区－选择其他小区");
                communityView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                presenter.getCityList(cityMap);
                otherCommunity.setVisibility(View.GONE);
                loadingLayout.setStatus(LoadingLayout.Loading);
                break;
            //根据关键字搜索小区列表
            case R.id.community_search:
                AppConfig.trackCustomEvent(this, "community_search_click", "选择小区－根据关键字进行搜索");
                if (communitySearch.getText().toString().trim().length() <= 0) {
                    ToastUtils.showShort(this, "搜索关键字不能为空");
                    return;
                }
                Map<String, String> queryMap = new HashMap<>();
                queryMap.put("name", communitySearch.getText().toString().trim());
                presenter.getQueryCommunity(queryMap);
                loadingLayout.setStatus(LoadingLayout.Loading);
                communityView.setVisibility(View.GONE);
                otherCommunity.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                break;

            case R.id.tx_select_community_location:
                AppConfig.trackCustomEvent(this, "tx_select_community_location_click", "选择小区－手动搜索定位");
                presenter.getLocationInfo();
                txLocation.setText("定位中...");
                otherCommunity.setVisibility(View.VISIBLE);
                loadingLayout.setStatus(LoadingLayout.Loading);
                communityView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CommunityGPSBean.ListBean bean = (CommunityGPSBean.ListBean) adapter.getItem(position);
        if (bean != null)
            SharedPreferencesUtils.setCommunityId(this, bean.getName() + "," + bean.getCommunityId());
        if (!StringUtils.isEmpty(last_activity) && StringUtils.equals("announcement", last_activity)) {
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void showMsg(String msg) {
        checkNetWork();
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    private void checkNetWork() {
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Success : LoadingLayout.No_Network);
        expandLoadView.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Success : LoadingLayout.No_Network);
    }

    @Override
    public void showCommunityGpsData(CommunityGPSBean bean) {
        adapter.setDataList(bean.getList());
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? (bean.getList().size() > 0 ? LoadingLayout.Success : LoadingLayout.Community_Empty) : LoadingLayout.No_Network);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 105)
    public void loactionSuccess() {
        presenter.getLocationInfo();
        LogUtils.i("权限获取成功，正在定位!");
    }

    @PermissionFail(requestCode = 105)
    public void loactionFail() {
        ToastUtils.showShort(this, "权限获取失败，请前往应用权限管理中，进行授权!");
    }

    @Override
    public void showQueryCommunity(CommunityGPSBean bean) {
        adapter.setDataList(bean.getList());
        loadingLayout.setStatus(bean.getList().size() > 0 ? LoadingLayout.Success : LoadingLayout.Community_Empty);
    }

    Map<String, String> communityMap = new HashMap<>();

    @Override
    public void showCityData(CityBean bean) {
        communityMap.clear();
        leftList = bean.getList();
        leftList.get(0).setSelet(true);
        leftAdapter.setDataList(leftList);
        loadingLayout.setStatus(LoadingLayout.Success);
        expandLoadView.setStatus(LoadingLayout.Success);
        cityId = leftList.get(0).getId() + "";
        communityMap.put("cityId", cityId);
        presenter.getCommunityList(communityMap);
    }

    String cityId = "";

    @Override
    public void showCommunityListData(CommunityListBean bean) {
        rightList = bean.getList();
        rightListAdapter.setData(rightList);
        getExpandableView(rightListAdapter, rightListView);
        expandLoadView.setStatus(rightList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Community_Empty);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
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

    AdapterView.OnItemClickListener leftListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for (CityBean.ListBean bean : leftList) {
                bean.setSelet(false);
            }
            leftList.get(position).setSelet(true);
            leftAdapter.setDataList(leftList);

            cityId = leftList.get(position).getId() + "";
            Map<String, String> communityMap = new HashMap<>();
            communityMap.put("cityId", cityId);
            presenter.getCommunityList(communityMap);
            expandLoadView.setStatus(LoadingLayout.Loading);
        }
    };

    @Override
    public void onBack(CommunityGPSBean.ListBean listBean) {
        if (listBean != null)
            SharedPreferencesUtils.setCommunityId(this, listBean.getName() + "," + listBean.getCommunityId());
        if (!StringUtils.isEmpty(last_activity) && StringUtils.equals("announcement", last_activity)) {
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onReload(View v) {
        presenter.getLocationInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(10025);
        StatService.onResume(SelectCommunityActivity.this);
        StatService.trackBeginPage(SelectCommunityActivity.this, "选择小区");
        AnalyUtils.setBAnalyResume(this, "选择小区");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(SelectCommunityActivity.this);
        StatService.trackEndPage(SelectCommunityActivity.this, "选择小区");
        AnalyUtils.setBAnalyPause(this, "选择小区");
    }
}

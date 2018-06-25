package com.jinke.community.ui.activity.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.DoorHouseListPresenter;
import com.jinke.community.ui.adapter.DoorListAdapter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IDoorHouseListView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-21.
 */

public class DoorHouseListActivity extends BaseActivity<IDoorHouseListView, DoorHouseListPresenter> implements LoadingLayout.OnReloadListener, AdapterView.OnItemClickListener, IDoorHouseListView {
    @Bind(R.id.door_list)
    ListView doorList;
    @Bind(R.id.loading)
    LoadingLayout loading;
    private DoorListAdapter doorListAdapter;
    private List<HouseListInfo.ListDateBean> listDateBeen = new ArrayList<>();

    @Override
    public DoorHouseListPresenter initPresenter() {
        return new DoorHouseListPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_door_list;
    }

    public final static String DOOR_LIST_BUNDLE = "door_list_bundle";
    public final static String DOOR_LIST_BUNDLE_DATE = "door_list_bundle_date";
    public final static String DOOR_LIST_BUNDLE_DATE_NAME = "door_list_bundle_date_name";
    public final static int REQUEST_CODE = 9211;

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        onBackBtn();
    }

    @Override
    protected void initView() {
        setTitle("房屋列表");
        showBackwardView("", true);
        doorList.setOnItemClickListener(this);
        loading.setOnReloadListener(this);
        getRequest();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onBackBtn();
        return super.onKeyDown(keyCode, event);
    }

    public void onBackBtn() {//点击返回按钮事，参数需要回传到上一个页面
        Intent resultIntent = getIntent();
        Bundle resultBundle = new Bundle();
        resultBundle.putString(DOOR_LIST_BUNDLE_DATE, "success");
        resultIntent.putExtra(DOOR_LIST_BUNDLE, resultBundle);
        setResult(REQUEST_CODE, resultIntent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppConfig.trackCustomEvent(this, "door_house_click", "门禁－选择房屋列表");
        Intent resultIntent = getIntent();
        Bundle resultBundle = new Bundle();
        resultBundle.putString(DOOR_LIST_BUNDLE_DATE, String.valueOf(listDateBeen.get(position).getHouseId()));
        resultBundle.putString(DOOR_LIST_BUNDLE_DATE_NAME, String.valueOf(listDateBeen.get(position).getTqcommunityName() + listDateBeen.get(position).getHouseName()));
        resultIntent.putExtra(DOOR_LIST_BUNDLE, resultBundle);
        setResult(REQUEST_CODE, resultIntent);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(this, msg);
        loading.setStatus(LoadingLayout.No_Network);
    }

    @Override
    public void onSuccess(HouseListInfo info) {
        listDateBeen = info.getListDate();
        doorListAdapter = new DoorListAdapter(this, listDateBeen);
        doorList.setAdapter(doorListAdapter);
        loading.setStatus(listDateBeen.size() > 0 ? LoadingLayout.Success : LoadingLayout.MyHouse_Empty);
    }

    @Override
    public void onReload(View v) {
        switch (v.getId()) {//网络错误刷新按钮
            case R.id.error_reload_btn_aa:
                loading.setStatus(LoadingLayout.Loading);
                getRequest();
                break;
            case R.id.empty_reload://点击屏幕重新加载
                loading.setStatus(LoadingLayout.Loading);
                getRequest();
                break;
        }
    }

    public void getRequest() {//封装请求参数
        Map<String, String> map = new HashMap<>();
        presenter.getHouseList(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(DoorHouseListActivity.this);
        StatService.trackBeginPage(DoorHouseListActivity.this, "门禁－选择房屋列表");
        AnalyUtils.setBAnalyResume(this, "门禁－选择房屋列表");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(DoorHouseListActivity.this);
        StatService.trackEndPage(DoorHouseListActivity.this, "门禁－选择房屋列表");
        AnalyUtils.setBAnalyPause(this, "门禁－选择房屋列表");
    }
}

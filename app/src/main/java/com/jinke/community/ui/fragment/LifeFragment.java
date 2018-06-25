package com.jinke.community.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.donkingliang.banner.CustomBanner;
import com.jinke.community.R;
import com.jinke.community.base.BaseFragment;
import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.presenter.LifePresenter;
import com.jinke.community.ui.activity.web.LifeDetailsActivity;
import com.jinke.community.ui.adapter.GridViewAdapter;
import com.jinke.community.ui.adapter.LifeSelectAdapter;
import com.jinke.community.ui.adapter.LifeTypeAdapter;
import com.jinke.community.ui.adapter.SpikeAdapter;
import com.jinke.community.ui.toast.dialog.SpotsDialog;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.ILifeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * 生活服务
 * Created by root on 17-7-25.
 */

public class LifeFragment extends BaseFragment<ILifeView, LifePresenter> implements ILifeView, CustomBanner.OnPageClickListener {
    @Bind(R.id.food_grid)
    RecyclerView foodGridView;
    @Bind(R.id.daily_grid)
    RecyclerView dailyGridView;
    @Bind(R.id.fruits_grid)
    RecyclerView fruitsGridView;
    @Bind(R.id.fillListView)
    FillListView fillListView;
    @Bind(R.id.life_top_customBanner)
    CustomBanner customBanner;
    @Bind(R.id.life_type)
    FillGridView typeGridView;
    @Bind(R.id.gridView_Spike)
    FillGridView spikeGridView;
    @Bind(R.id.daily_list_text)
    TextView txDaily;
    @Bind(R.id.food_list_text)
    TextView txFood;
    @Bind(R.id.fruits_list_text)
    TextView txFruits;
    @Bind(R.id.select_list_text)
    TextView txSelect;

    private List<LifeRecommendBean.ListBeanX.ListBean> list = new ArrayList<>();
    private GridViewAdapter foodGridAdapter;
    private GridViewAdapter dailyGridAdapter;
    private GridViewAdapter fruitsGridAdapter;
    private List<LifeTopBannerBean.ListBean> selectList = new ArrayList<>();
    private LifeSelectAdapter lifeSelectAdapter;
    private LifeTypeAdapter typeAdapter;
    private SpotsDialog dialog;

    SpikeAdapter spikeAdapter;
    List<LifeTopBannerBean.ListBean> lifeType = new ArrayList<>();
    ACache aCache;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_life;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("生活");

        aCache = ACache.get(getActivity());
        foodGridView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        foodGridAdapter = new GridViewAdapter(getActivity(), list, R.layout.item_grid);
        foodGridView.setAdapter(foodGridAdapter);

        dailyGridView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        dailyGridAdapter = new GridViewAdapter(getActivity(), list, R.layout.item_grid);
        dailyGridView.setAdapter(dailyGridAdapter);

        fruitsGridView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fruitsGridAdapter = new GridViewAdapter(getActivity(), list, R.layout.item_grid);
        fruitsGridView.setAdapter(fruitsGridAdapter);


        lifeSelectAdapter = new LifeSelectAdapter(getActivity(), selectList);
        fillListView.setAdapter(lifeSelectAdapter);

        typeAdapter = new LifeTypeAdapter(getActivity(), R.layout.item_life_type, lifeType);
        typeGridView.setAdapter(typeAdapter);

        presenter.getLifeTopBanner();
        customBanner.setOnPageClickListener(this);
        initData();
    }

    private void initData() {//加载缓存数据
        //顶部广告页
        LifeTopBannerBean TopBannerBean = (LifeTopBannerBean) aCache.getAsObject("TopBannerBean");
        if (TopBannerBean != null && TopBannerBean.getList() != null) {
            presenter.setListData(customBanner, TopBannerBean.getList());
        }


        LifeTopBannerBean TopNavigationOne = (LifeTopBannerBean) aCache.getAsObject("TopNavigationOne");
        if (TopNavigationOne != null && TopNavigationOne.getList() != null) {
            typeAdapter.setDataList(TopNavigationOne.getList());
        }

        LifeTopBannerBean NavigationTwo = (LifeTopBannerBean) aCache.getAsObject("NavigationTwo");
        if (NavigationTwo != null && NavigationTwo.getList() != null) {
            getNavigationTwo(NavigationTwo);
        }

        LifeRecommendBean recommendBean = (LifeRecommendBean) aCache.getAsObject("Ranking");
        if (recommendBean != null && recommendBean.getList() != null) {
            getRanking(recommendBean);
        }

        LifeTopBannerBean bottomBean = (LifeTopBannerBean) aCache.getAsObject("BottomActivity");
        if (bottomBean != null && bottomBean.getList() != null) {
            lifeSelectAdapter.setData(bottomBean.getList());
        }
    }

    @Override
    public LifePresenter initPresenter() {
        return new LifePresenter(getActivity());
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(getActivity(), msg);
    }

    @Override
    public void onTopAdvertising(LifeTopBannerBean bean) {
        if (bean.getList() != null && bean.getList().size() > 0) {
            aCache.put("TopBannerBean", bean, ACache.TIME_DAY);
            presenter.setListData(customBanner, bean.getList());
        }
    }

    @Override
    public void onTopNavigationOne(LifeTopBannerBean bean) {
        if (bean.getList() != null && bean.getList().size() > 0) {
            aCache.put("TopNavigationOne", bean, ACache.TIME_DAY);
            typeAdapter.setDataList(bean.getList());
        }

    }

    @Override
    public void onNavigationTwo(LifeTopBannerBean bean) {
        if (bean.getList() != null && bean.getList().size() > 0) {
            aCache.put("NavigationTwo", bean, ACache.TIME_DAY);
            getNavigationTwo(bean);
        }

    }

    @Override
    public void onRanking(LifeRecommendBean bean) {//果蔬排行
        if (bean.getList() != null && bean.getList().size() > 0) {
            aCache.put("Ranking", bean, ACache.TIME_DAY);
            getRanking(bean);
        }
    }

    @Override
    public void onBottomActivity(LifeTopBannerBean bean) {//
        if (bean.getList() != null && bean.getList().size() > 0) {
            aCache.put("BottomActivity", bean, ACache.TIME_DAY);
            lifeSelectAdapter.setData(bean.getList());
        }
    }

    public void getRanking(LifeRecommendBean bean) {
        if (bean.getList().size() > 0) {
            foodGridAdapter.setData(bean.getList().get(0).getList());
            foodGridAdapter.notifyDataSetChanged();
            txFood.setText(bean.getList().get(0).getTitle());
        }

        if (bean.getList().size() > 1) {
            dailyGridAdapter.setData(bean.getList().get(1).getList());
            dailyGridAdapter.notifyDataSetChanged();
            txDaily.setText(bean.getList().get(1).getTitle());
        }

        if (bean.getList().size() > 2) {
            fruitsGridAdapter.setData(bean.getList().get(2).getList());
            fruitsGridAdapter.notifyDataSetChanged();
            txFruits.setText(bean.getList().get(2).getTitle());
        }

    }

    public void getNavigationTwo(LifeTopBannerBean bean) {
        List<LifeTopBannerBean.ListBean> list = bean.getList();
        if (list.size() > 0) {
            for (LifeTopBannerBean.ListBean listBean : list) {
                listBean.setVisibility(true);
            }
            list.get(list.size() - 1).setVisibility(false);
            LogUtils.i("list----" + bean.getList().size());
            spikeAdapter = new SpikeAdapter(getActivity(), R.layout.item_life_spike, bean.getList());
            spikeGridView.setAdapter(spikeAdapter);
        }
    }

    @Override
    public void onPageClick(int i, Object obj) {
        //顶部广告页点击事件
        LifeTopBannerBean.ListBean bean = (LifeTopBannerBean.ListBean) obj;
        if (StringUtils.equals("#", bean.getCircleLinkUrl())) {
            return;
        }
        AnalyUtils.addLifeAnaly(10026, bean.getId());
        Intent intent = new Intent(getActivity(), LifeDetailsActivity.class);
        intent.putExtra("url", bean.getCircleLinkUrl());
        intent.putExtra("title", bean.getTitle());
        startActivity(intent);
    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = new SpotsDialog(getActivity());
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.show();
    }

    @Override
    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(getActivity(), "生活");
    }

    @Override
    public void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(getActivity(), "生活");
    }
}

package com.jinke.community.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.banner.CustomBanner;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseFragment;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.presenter.SocialCirclePresenter;
import com.jinke.community.ui.activity.base.CompetitionActivity;
import com.jinke.community.ui.activity.web.LifeDetailsActivity;
import com.jinke.community.ui.activity.base.WalkingActivity;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.SocialCircleView;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-25.
 */

public class SocialCircleFragment extends BaseFragment<SocialCircleView, SocialCirclePresenter> implements SocialCircleView, CustomBanner.OnPageClickListener {
    @Bind(R.id.customBanner)
    CustomBanner customBanner;
    @Bind(R.id.circle_head_image)
    SimpleDraweeView circleHeadImage;
    @Bind(R.id.tx_circle_name)
    TextView txCircleName;
    @Bind(R.id.tx_circle)
    TextView txCircle;
    @Bind(R.id.tx_circle_money)
    TextView txCircleMoney;
    @Bind(R.id.tx_circle_money_center)
    TextView txCircleMoneyCenter;
    @Bind(R.id.event_image)
    ImageView eventImage;
    @Bind(R.id.event_layout)
    RelativeLayout eventLayout;
    @Bind(R.id.walking_image)
    ImageView walkingImage;
    @Bind(R.id.walking_layout)
    RelativeLayout walkingLayout;

    private ACache aCache;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_socialcircle;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle(getString(R.string.main_bottom_circle));
        aCache = aCache.get(getActivity());
        presenter.getAdvertisingBanner();
        initBanner();
        initData();
    }

    private void initBanner() {
        customBanner.setOnPageClickListener(this);
    }

    private void initData() {//加载本地缓存
        LifeTopBannerBean topBannerBean = (LifeTopBannerBean) aCache.getAsObject("SocialBanner");
        BaseUserBean baseUserBean = MyApplication.getBaseUserBean();
        circleHeadImage.setImageURI(baseUserBean.getAvatar());
        txCircleName.setText(StringUtils.isEmpty(baseUserBean.getNickName()) ? baseUserBean.getName() : baseUserBean.getNickName());
        if (topBannerBean != null && topBannerBean.getList() != null) {
            presenter.setListData(customBanner, topBannerBean.getList());
        }
    }

    @Override
    public SocialCirclePresenter initPresenter() {
        return new SocialCirclePresenter(getActivity());
    }

    @OnClick({R.id.walking_layout, R.id.event_layout, R.id.tx_circle_money_center})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.walking_layout:
                AnalyUtils.addAnaly(10054);
                startActivity(new Intent(getActivity(), WalkingActivity.class));
                break;
            case R.id.tx_circle_money_center:
                ToastUtils.showShort(getActivity(), "正在建设中,敬请期待！");
                break;
            case R.id.event_layout:
                startActivity(new Intent(getActivity(), CompetitionActivity.class));
                break;
        }
    }


    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(getActivity(), msg);
    }

    @Override
    public void onAdvertising(LifeTopBannerBean bean) {
        aCache.put("SocialBanner", bean, ACache.TIME_DAY);
        presenter.setListData(customBanner, bean.getList());
    }

    @Override
    public void onPageClick(int i, Object obj) {
        //顶部广告页点击事件
        LifeTopBannerBean.ListBean bean = (LifeTopBannerBean.ListBean) obj;
        if (StringUtils.equals("#", bean.getCircleLinkUrl())) {
            return;
        }
        Intent intent = new Intent(getActivity(), LifeDetailsActivity.class);
        intent.putExtra("url", bean.getCircleLinkUrl());
        intent.putExtra("title", bean.getTitle());
        AnalyUtils.addLifeAnaly(10026, bean.getId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(getActivity(), "圈子");
    }

    @Override
    public void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(getActivity(), "圈子");
    }
}

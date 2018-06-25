package com.jinke.community.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.service.impl.LifeImpl;
import com.jinke.community.service.listener.ILifListener;
import com.jinke.community.view.ILifeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-7-25.
 */

public class LifePresenter extends BasePresenter<ILifeView> implements ILifListener {
    Context mContext;
    LifeImpl life;

    public LifePresenter(Context mContext) {
        this.mContext = mContext;
        life = new LifeImpl(mContext);
    }

    Map<String, String> map = new HashMap<>();

    public void getLifeTopBanner() {
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        //异步加载改为同步加载
        life.getTopAdvertising(map, this);
        life.getTopNavigationTwo(map, this);
        life.getTopNavigationOne(map, this);
        life.getRanking(map, this);
        life.getBottomActivity(map, this);
    }

    /**
     * 设置top banner
     *
     * @param customBanner
     * @param imageList
     */
    public void setListData(CustomBanner customBanner, List<LifeTopBannerBean.ListBean> imageList) {
        customBanner.setPages(new CustomBanner.ViewCreator<LifeTopBannerBean.ListBean>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(final Context context, View view, int position, final LifeTopBannerBean.ListBean entity) {
                Glide.with(context).load(entity.getCircleImageUrl()).placeholder(R.drawable.icon_life_banner_fail).error(R.drawable.icon_life_banner_fail).into((ImageView) view);
            }
        }, imageList);
        if (imageList.size() >= 2) {
            customBanner.setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY);
            customBanner.startTurning(2000);//间隔时间
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void onTopAdvertising(LifeTopBannerBean bean) {
        if (mView != null) {
            mView.onTopAdvertising(bean);
        }
    }

    @Override
    public void onTopNavigationOne(LifeTopBannerBean bean) {
        if (mView != null) {
            mView.onTopNavigationOne(bean);
        }
    }

    @Override
    public void onNavigationTwo(LifeTopBannerBean bean) {
        if (mView != null) {
            mView.onNavigationTwo(bean);
        }
    }

    @Override
    public void onRanking(LifeRecommendBean bean) {
        if (mView != null) {
            mView.onRanking(bean);
        }
    }

    @Override
    public void onBottomActivity(LifeTopBannerBean bean) {
        if (mView != null) {
            mView.onBottomActivity(bean);
        }
    }
}

package com.jinke.community.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.service.impl.SocialCircleImpl;
import com.jinke.community.service.listener.ISocialCircleListener;
import com.jinke.community.view.SocialCircleView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/31.
 */

public class SocialCirclePresenter extends BasePresenter<SocialCircleView> implements ISocialCircleListener {
    private Context mContext;
    SocialCircleImpl socialCircle;

    public SocialCirclePresenter(Context mContext) {
        this.mContext = mContext;
        socialCircle = new SocialCircleImpl(mContext);
    }

    public void getAdvertisingBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        socialCircle.getAdvertising(map, this);
    }

    public void setListData(CustomBanner customBanner, List<LifeTopBannerBean.ListBean> imageList) {
        customBanner.setPages(new CustomBanner.ViewCreator<LifeTopBannerBean.ListBean>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, LifeTopBannerBean.ListBean entity) {
                Glide.with(context).load(entity.getCircleImageUrl())
                        .error(R.drawable.icon_life_banner_fail)
                        .placeholder(R.drawable.icon_life_banner_fail)
                        .into((ImageView) view);
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
    public void onAdvertising(LifeTopBannerBean bean) {
        if (mView != null) {
            mView.onAdvertising(bean);
//            getUserCenterInfo();
        }
    }
}

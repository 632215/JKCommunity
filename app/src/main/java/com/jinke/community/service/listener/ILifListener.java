package com.jinke.community.service.listener;

import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.LifeSuperMarketBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;

/**
 * Created by root on 17-8-16.
 */

public interface ILifListener {
    void onError(String code, String msg);

    void onTopAdvertising(LifeTopBannerBean bean);

    void onTopNavigationOne(LifeTopBannerBean bean);

    void onNavigationTwo(LifeTopBannerBean bean);

    void onRanking(LifeRecommendBean bean);

    void onBottomActivity(LifeTopBannerBean bean);
}

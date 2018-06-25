package com.jinke.community.view;

import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.LifeSuperMarketBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;

/**
 * Created by root on 17-7-25.
 */

public interface ILifeView {
    void showMsg(String msg);

    void onTopAdvertising(LifeTopBannerBean bean);

    void onTopNavigationOne(LifeTopBannerBean bean);

    void onNavigationTwo(LifeTopBannerBean bean);

    void onRanking(LifeRecommendBean bean);

    void onBottomActivity(LifeTopBannerBean bean);

    void hideDialog();

    void showDialog();
}

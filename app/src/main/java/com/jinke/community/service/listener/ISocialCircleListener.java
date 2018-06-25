package com.jinke.community.service.listener;

import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.bean.UserInfo;

/**
 * Created by root on 17-8-17.
 */

public interface ISocialCircleListener {

    void onError(String code, String msg);

    void onAdvertising(LifeTopBannerBean bean);
}

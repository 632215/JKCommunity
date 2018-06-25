package com.jinke.community.view;

import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.bean.UserInfo;

/**
 * Created by Administrator on 2017/7/31.
 */

public interface SocialCircleView {
    void showMsg(String msg);
    void onAdvertising(LifeTopBannerBean bean);

}

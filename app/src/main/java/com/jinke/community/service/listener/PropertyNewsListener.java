package com.jinke.community.service.listener;

import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by Administrator on 2017/11/27.
 */

public interface PropertyNewsListener {
    void getHouseListNext(HouseListBean info);

    void onErrorListener(String code, String msg);

    void addPostItNext(PropertyBean.ListBean data);

    void getConfigNext(CommunityBean info);

    void getConfigError();
}

package com.jinke.community.view;

import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-10.
 */

public interface IPropertyNewsView {
    void showMsg(String msg);

    void onSuccess();

    void showLoading();

    void hindLoading();

    void getHouseListNext(HouseListBean info);

    void addPostItNext(PropertyBean.ListBean data);

    void getConfigNext(CommunityBean info);

    void getConfigError();
}

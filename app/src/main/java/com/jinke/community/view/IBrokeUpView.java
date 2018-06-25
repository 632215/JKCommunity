package com.jinke.community.view;

import com.jinke.community.bean.LoginBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-6.
 */

public interface IBrokeUpView {
    void showMsg(String msg);

    void showLoading();

    void hideLoading();

    void onFileUpSuccess(LoginBean bean);

    void getHouseListNext(HouseListBean info);
}

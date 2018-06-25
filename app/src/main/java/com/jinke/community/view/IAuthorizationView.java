package com.jinke.community.view;

import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-7.
 */

public interface IAuthorizationView {
    void showMsg(String msg);
    void showDialog();
    void onSuccess();

    void getHouseListDataNext(HouseListBean info);

    void deleteOnNext(String string);
}

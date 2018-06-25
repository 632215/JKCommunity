package com.jinke.community.view;

import com.jinke.community.bean.ShareLoginBean;
import com.jinke.community.bean.UserLoginBean;

/**
 * Created by root on 17-7-22.
 */

public interface LoginView {

    void loginSuccess(UserLoginBean bean,ShareLoginBean shareLoginBean);

    void showMsg(String msg);

    void showDialog();

    void hideDialog();



}

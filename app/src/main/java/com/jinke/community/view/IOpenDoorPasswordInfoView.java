package com.jinke.community.view;

import com.jinke.community.bean.PasswordInfo;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorPasswordInfoView {
    void onPasswordInfo(PasswordInfo info);
    void showMsg(String msg);
}

package com.jinke.community.service.listener;

import com.jinke.community.bean.AutoBindBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.RegisterLoginBean;

/**
 * Created by Administrator on 2017/11/13.
 */

public interface BindHouseListener {
    void onErrorMsg(String code, String msg);

    void getRegisterDataNext(RegisterLoginBean info);

    void autoBindHouseNext(AutoBindBean info);

    void setDefaultHouseNext(SetDefaultHouseBean info);
}

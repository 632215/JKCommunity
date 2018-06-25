package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.ParkInfoBean;

/**
 * Created by Administrator on 2017/10/17.
 */

public interface VehicleAuthorizedView extends BaseView {
    void getParkInfoonNext(ParkInfoBean info);

    void addAuthorizeonNext();

    void error(String msg);
}

package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by Administrator on 2017/11/18.
 */

public interface PropertyPaymentView extends BaseView{

    void showMessage(String msg);

    void getHouseListNext(HouseListBean info);

    void getParkInfoNext(ParkInfoBean info);
}

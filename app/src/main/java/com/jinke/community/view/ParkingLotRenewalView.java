package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.ParkInfoSelectBean;

import java.util.List;


/**
 * Created by Administrator on 2017/10/21.
 */

public interface ParkingLotRenewalView extends BaseView {
    void error(String msg);

    void getParkInfoonNext(List<ParkInfoSelectBean.ListBean> info);
}

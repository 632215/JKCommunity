package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.HouseWithHoldBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface WithholdingManagementView{
    void getHouseWithHoldNext(HouseWithHoldBean info);

    void showMessage(String msg);
}

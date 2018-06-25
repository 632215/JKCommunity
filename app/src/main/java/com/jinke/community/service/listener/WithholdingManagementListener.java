package com.jinke.community.service.listener;

import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.WithHoldBreakBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface WithholdingManagementListener {
    void onErrorMsg(String code, String msg);

    void getHouseWithHoldNext(HouseWithHoldBean info);

    void withholdBreakNext(WithHoldBreakBean info);
}

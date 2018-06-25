package com.jinke.community.service;

import com.jinke.community.presenter.WithholdingManagementPresenter;
import com.jinke.community.service.listener.WithholdingManagementListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface WithholdingManagementBiz {
    void getHouseWithHold(Map map, WithholdingManagementListener listener);

    void withholdBreak(Map map, WithholdingManagementListener listener);
}

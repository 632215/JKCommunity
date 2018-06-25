package com.jinke.community.service;

import com.jinke.community.presenter.BrokeStagePresenter;
import com.jinke.community.service.listener.BrokeStageListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface BrokeStageBiz {
    void getStageBrokeList(Map<String, String> map, BrokeStageListener listener);

    void praiseClick(Map map, BrokeStageListener listener);

    void getCommunity(Map<String, String> map, BrokeStageListener listener);
}

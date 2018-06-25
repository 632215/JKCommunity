package com.jinke.community.service;

import com.jinke.community.presenter.AnnouncementPresenter;
import com.jinke.community.service.listener.AnnouncementListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface AnnouncementBiz {
    void getCommunity(Map<String, String> map, AnnouncementListener listener);

    void getPostItNoticeList(Map<String, String> map, AnnouncementListener listener);
}

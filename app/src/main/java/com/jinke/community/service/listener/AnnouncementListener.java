package com.jinke.community.service.listener;

import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface AnnouncementListener {
    void getCommunityNext(UserCommunityBean info);

    void onError(String code, String msg);

    void getPostItNoticeListNext(BrokeNoticeListBean info);
}

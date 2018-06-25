package com.jinke.community.view;

import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface AnnouncementView {
    void getCommunityNext(UserCommunityBean info);

    void getPostItNoticeListNext(BrokeNoticeListBean info);

    void showMsg(String msg);
}

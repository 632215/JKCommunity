package com.jinke.community.service.listener;

import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface BrokeStageListener {
    void getStageBrokeListNext(NoticeListBean info);

    void onError(String code, String msg);

    void praiseClickNext(PraiseresultBean info);

    void getCommunityNext(UserCommunityBean info);
}

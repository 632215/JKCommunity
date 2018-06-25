package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface BrokeStageView {
    void getStageBrokeListNext(NoticeListBean info);

    void showMsg(String msg);

    void praiseClickNext(PraiseresultBean info);

    void getCommunityNext(UserCommunityBean info);
}

package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface AddAuthorizedView extends BaseView{
    void showMsg(String msg);

    void addAuthorizationNext(HouseListBean info);
}

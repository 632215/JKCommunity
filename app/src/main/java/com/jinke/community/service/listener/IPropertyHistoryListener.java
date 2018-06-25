package com.jinke.community.service.listener;

import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;

/**
 * Created by root on 17-8-11.
 */

public interface IPropertyHistoryListener {
    void onError(String code,String msg);

    void getKeepPostItListNext(PropertyBean info);

    void getConfigNext(CommunityBean info);

    void getConfigError();
}

package com.jinke.community.view;

import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;

/**
 * Created by root on 17-8-11.
 */

public interface IPropertyHistoryView {
    void showMsg(String msg);

    void getKeepPostItListNext(PropertyBean info);

    void getConfigNext(CommunityBean info);

    void getConfigError();
}

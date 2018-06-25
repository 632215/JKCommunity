package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.PropertyProgressBean;

/**
 * Created by Administrator on 2018/4/25.
 */

public interface NewPropertyDetailsView extends BaseView{
    void showToast(String code, String msg);

    void getProgressSuccess(PropertyProgressBean info, KeepPropertyBean keepPropertyBean);

    void getKeeperDetailSuccess(KeepPropertyBean keepPropertyBean);
}

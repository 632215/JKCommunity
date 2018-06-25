package com.jinke.community.view;

import com.jinke.community.base.BaseView;

/**
 * Created by Administrator on 2018/5/30.
 */

public interface AuthenticationView extends BaseView{
    void uploadSuccess();

    void uploadFail(String s);
}

package com.jinke.community.view;

import com.jinke.community.bean.PostItNoticeDetailBean;

/**
 * Created by root on 17-8-17.
 */

public interface IPropertyWebView {
    void onSuccess(PostItNoticeDetailBean Bean);

    void showMsg(String msg);
}

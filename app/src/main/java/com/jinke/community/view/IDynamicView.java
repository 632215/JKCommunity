package com.jinke.community.view;

import com.jinke.community.bean.NoticeOneBean;

/**
 * Created by root on 17-8-25.
 */

public interface IDynamicView {
    void onSuccess(NoticeOneBean noticeOneBean);

    void showMsg(String msg);

    void showLoading();

    void hideLoading();
}

package com.jinke.community.view;

import com.jinke.community.bean.EmptyObjectBean;

/**
 * Created by root on 17-8-16.
 */

public interface ISuggestionsView {
    void showMsg(String msg);
    void onSuccess(EmptyObjectBean bean);
    void showLoading();
    void hindLoading();

}

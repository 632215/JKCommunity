package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.WithHoldOpenBean;

/**
 * Created by Administrator on 2017/11/23.
 */

public interface WithHoldOpenView extends BaseView{
    void showMsg(String msg);

    void payResult(int i);
}

package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.WorkingSortBean;

/**
 * Created by Administrator on 2017/7/31.
 */

public interface IWalkingView {
    void onWorkingSort(WorkingSortBean bean);
    void showMsg(String msg);
}

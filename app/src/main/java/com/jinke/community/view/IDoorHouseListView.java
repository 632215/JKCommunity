package com.jinke.community.view;

import com.jinke.community.bean.HouseListInfo;

/**
 * Created by root on 17-8-21.
 */

public interface IDoorHouseListView  {
    void  showMsg(String msg);
    void onSuccess(HouseListInfo info);
}

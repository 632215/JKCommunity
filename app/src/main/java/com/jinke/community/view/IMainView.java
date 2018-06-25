package com.jinke.community.view;

import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.RedCircleGroupBean;

/**
 * Created by root on 17-7-21.
 */

public interface IMainView {
    void showMsg(String msg);

    void getHouseListNext(HouseListBean info);

    void getRedCircleNext(RedCircleGroupBean info);
}

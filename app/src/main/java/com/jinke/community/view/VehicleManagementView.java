package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.AuthorizedRecordBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.UserCarBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public interface VehicleManagementView extends BaseView {
    void getMyCarOnNext(UserCarBean info);

    void getMyCarOnError(String msg);

    void successOperating();

    void getAuthorizedVehicleNext(List<AuthorizedRecordBean.ListBean> infoList);

    void deleteAuthorizedOnNext();

    void getParkInfoonNext(ParkInfoBean info);
}

package com.jinke.community.service.listener;
import com.jinke.community.bean.AuthorizedRecordBean;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.UserCarBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public interface VehicleManagementListener {
    void getMyCarOnNext(UserCarBean info);

    void error(String msg);

    void unBanVehicleOnNext(EmptyObjectBean info);

    void banVehicleOnNext();

    void getAuthorizedVehicleNext(List<AuthorizedRecordBean.ListBean> infoList);

    void deleteAuthorizedOnNext(EmptyObjectBean info);

    void getParkInfoonNext(ParkInfoBean info);
}

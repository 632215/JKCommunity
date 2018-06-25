package com.jinke.community.service;

import com.jinke.community.presenter.VehicleManagementPresenter;
import com.jinke.community.service.listener.VehicleManagementListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/19.
 */

public interface VehicleManagementBiz {
    void getMyCar(Map map, VehicleManagementListener listener);

    void banVehicle(Map map, VehicleManagementListener listener);

    void unBanVehicle(Map map, VehicleManagementListener listener);

    void getAuthorizedVehicle(Map map, VehicleManagementListener listener);

    void deleteAuthorized(Map map, VehicleManagementListener listener);

    void getParkingInfo(Map map, VehicleManagementListener listener);
}

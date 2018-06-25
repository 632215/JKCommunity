package com.jinke.community.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jinke.community.bean.LocationBean;

import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-27.
 */

public class LocationUtils implements AMapLocationListener {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private onLocationInfoListener listener;
    private Context mContext;

    public void setOnLocationInfoListener(onLocationInfoListener listener) {
        this.listener = listener;
    }

    public LocationUtils(Context mContext) {
        this.mContext = mContext;
        //初始化定位
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(false);
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    LocationBean locationBean;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                locationBean = new LocationBean();
                locationBean.setLocationType(amapLocation.getLocationType() + "");
                locationBean.setLatitude(amapLocation.getLatitude() + "");
                locationBean.setLongitude(amapLocation.getLongitude() + "");
                locationBean.setAccuracy(amapLocation.getAccuracy() + "");//获取精度信息
                locationBean.setAddress(amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                locationBean.setCountry(amapLocation.getCountry());//国家信息
                locationBean.setProvince(amapLocation.getProvince());//省信息
                locationBean.setCity(amapLocation.getCity());//城市信息
                locationBean.setDistrict(amapLocation.getDistrict());//城区信息
                locationBean.setStreet(amapLocation.getStreet());//街道信息
                locationBean.setStreetNum(amapLocation.getStreetNum());//街道门牌号信息
                locationBean.setCityCode(amapLocation.getCityCode());//城市编码
                locationBean.setAdCode(amapLocation.getAdCode());//地区编码
                locationBean.setAoiName(amapLocation.getAoiName());//获取当前定位点的AOI信息
                locationBean.setBuildingId(amapLocation.getBuildingId());//获取当前室内定位的建筑物Id
                locationBean.setFloor(amapLocation.getFloor());//获取当前室内定位的楼层
                if (listener != null)
                    listener.locationBackInfo(locationBean);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                switch (amapLocation.getErrorCode()){
                    case 4:
                        listener.locationBackFailed(amapLocation.getErrorCode());
                        ToastUtils.showShort(mContext, "请检查您的网络连接！");
                }
            }
        }
    }

    public interface onLocationInfoListener {
        void locationBackInfo(LocationBean locationBean);

        void locationBackFailed(int errorCode);
    }
}

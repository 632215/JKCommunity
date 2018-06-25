package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.ParkInfoSelectBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.ParkingLotRenewalBiz;
import com.jinke.community.service.listener.ParkingLotRenewalListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ParkingLotRenewalImpl implements ParkingLotRenewalBiz {
    private Context mContext;
    private List<ParkInfoSelectBean.ListBean> parkingList = new ArrayList<>();

    public ParkingLotRenewalImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getParkInfo(Map map, final ParkingLotRenewalListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<ParkInfoBean>() {
            @Override
            public void onNext(ParkInfoBean info) {
                ParkInfoSelectBean.ListBean bean;
                parkingList.clear();
                if (info != null && info.getList() != null) {
                    for (ParkInfoBean.ListBean listBean : info.getList()) {
                        bean = new ParkInfoSelectBean.ListBean();
                        bean.setSelectFlag("false");

                        bean.setCarSpace_No(listBean.getCarSpace_No());
                        bean.setCarType_Name(listBean.getCarType_Name());
                        bean.setCarType_No(listBean.getCarType_No());
                        bean.setCarSpace_CarNum(listBean.getCarSpace_CarNum());
                        bean.setCarSpace_LastIssue(listBean.getCarSpace_LastIssue());
                        bean.setCarSpace_Status(listBean.getCarSpace_Status());
                        bean.setCarSpace_UserName(listBean.getCarSpace_UserName());
                        bean.setCarSpace_Phone(listBean.getCarSpace_Phone());
                        bean.setCarSpace_HomeAddress(listBean.getCarSpace_HomeAddress());
                        bean.setCarSpace_CancelTime(listBean.getCarSpace_CancelTime());
                        bean.setCarSpace_IusseTime(listBean.getCarSpace_IusseTime());
                        bean.setCarSpace_EndTime(listBean.getCarSpace_EndTime());
                        bean.setCarSpace_BeginTime(listBean.getCarSpace_BeginTime());
                        bean.setCarTypeChargRules_MthNum(listBean.getCarTypeChargRules_MthNum());
                        bean.setCarTypeChargRules_Money(listBean.getCarTypeChargRules_Money());
                        bean.setPark_Name(listBean.getPark_Name());
                        bean.setParking_Key(listBean.getParking_Key());

                        parkingList.add(bean);
                    }
                }
                listener.getParkInfoonNext(parkingList);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().getParkInfo(new ProgressSubscriber<HttpResult<ParkInfoBean>>(subscriberOnNextListener, mContext), map,MyApplication.creatSign(map));
    }
}

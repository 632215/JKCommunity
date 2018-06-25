package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.http.door.DriveSubscriber;
import com.jinke.community.service.IDoorHouseListBiz;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public class DoorHouseListImpl implements IDoorHouseListBiz {
    private Context mContext;

    public DoorHouseListImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getHouseListDate(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListInfo>() {
            @Override
            public void onNext(HouseListInfo info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        DriveHttpMethods.getInstance(true).getHouseListDate(new DriveSubscriber<HttpResult<HouseListInfo>>(nextListener, mContext), map);

    }
}

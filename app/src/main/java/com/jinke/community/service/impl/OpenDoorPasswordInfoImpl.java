package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PasswordInfo;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.http.door.DriveSubscriber;
import com.jinke.community.service.IOpenDoorPasswordInfoBiz;
import com.jinke.community.service.listener.IOpenDoorPasswordInfoListener;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorPasswordInfoImpl implements IOpenDoorPasswordInfoBiz {
    private Context mContext;

    public OpenDoorPasswordInfoImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getPassWordData(Map<String, String> map, final IOpenDoorPasswordInfoListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PasswordInfo>() {
            @Override
            public void onNext(PasswordInfo info) {
                listener.onPasswordInfo(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        DriveHttpMethods.getInstance().getSaveCode(new DriveSubscriber<HttpResult<PasswordInfo>>(nextListener, mContext), map);

    }
}

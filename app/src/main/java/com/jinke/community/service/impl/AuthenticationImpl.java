package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BasePropertyDetailsBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.listener.AuthenticationListener;
import com.jinke.community.utils.GsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import www.jinke.com.library.utils.commont.LogUtils;


/**
 * Created by Administrator on 2018/5/30.
 */

public class AuthenticationImpl {
    private Context mContext;

    public AuthenticationImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void upload(RequestParams params, final AuthenticationListener listener) {
        HttpUtils http = new HttpUtils(300000);
        http.send(HttpRequest.HttpMethod.POST,
                HttpMethods.BASE_URL + "/houseTransfer/addUserCertified",
                params, new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        listener.uploadSuccess();
//                        LogUtils.i("result-----" + responseInfo.result);
//                        BasePropertyDetailsBean basePropertyDetailsBean = GsonUtil.GsonToBean(responseInfo.result, BasePropertyDetailsBean.class);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        listener.uploadFail(s);

                    }
                });
    }
}

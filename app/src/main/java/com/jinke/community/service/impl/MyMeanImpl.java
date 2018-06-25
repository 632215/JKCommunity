package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.bean.BasePropertyDetailsBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UpdateUserInfoBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.service.MyMeansBiz;
import com.jinke.community.service.listener.MyMeansListener;
import com.jinke.community.utils.GsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by root on 17-8-15.
 */

public class MyMeanImpl implements MyMeansBiz {

    Context mContext;

    public MyMeanImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void AlterMean(RequestParams params, final MyMeansListener listener) {
        HttpUtils http = new HttpUtils(300000);
        http.send(HttpRequest.HttpMethod.POST,
                HttpMethods.BASE_URL + "userCenter/editUser",
                params, new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.i("result-----" + responseInfo.result);
                        UpdateUserInfoBean httpResult = null;
                        try {
                            httpResult = GsonUtil.GsonToBean(responseInfo.result, UpdateUserInfoBean.class);
                        } catch (Exception e) {
                            listener.onError("1000", "数据转化错误");
                        }
                        if (httpResult != null && httpResult.getErrcode() == 200) {
                            listener.onSuccess(httpResult.getData().getAvatar());
                        } else {
                            listener.onError(httpResult.getErrcode() + "", httpResult.getErrmsg());
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        switch (e.getExceptionCode()) {
                            case 0:
                                listener.onError(e.getExceptionCode() + "", "请检查您的网络连接！");
                                break;
                            case 502:
                            case 500:
                            case 503:
                                listener.onError(e.getExceptionCode() + "", "服务器忙，请稍后重试。");
                                break;
                            default:
                                listener.onError(e.getExceptionCode() + "", s);
                        }
                    }
                });
    }
}

package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PostItListBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.PropertyHistoryPresent;
import com.jinke.community.service.IPropertyHistoryBiz;
import com.jinke.community.service.listener.IPropertyHistoryListener;
import com.jinke.community.utils.GsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by root on 17-8-11.
 */

public class PropertyHistoryImpl implements IPropertyHistoryBiz {

    private Context mContext;

    public PropertyHistoryImpl(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void getKeepPostItList(HashMap map, final IPropertyHistoryListener listener) {
//        HttpUtils http = new HttpUtils(200000);
//        http.send(HttpRequest.HttpMethod.POST,
//                HttpMethods.BASE_URL + "postIt/getPostItList/",
//                map, new RequestCallBack<String>() {
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        LogUtils.i("result-----" + responseInfo.result);
//                        PostItListBean defaultHouseBean = GsonUtil.GsonToBean(responseInfo.result, PostItListBean.class);
//                        if (defaultHouseBean.getErrcode() == 200) {
//                            listener.onPropertyList(defaultHouseBean.getData());
//                        } else {
//                            listener.onError(defaultHouseBean.getErrcode() + "", defaultHouseBean.getErrmsg());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        switch (e.getExceptionCode()) {
//                            case 0:
//                                listener.onError(e.getExceptionCode()+"", "请检查您的网络连接！");
//                                break;
//                            case 502:
//                            case 500:
//                            case 503:
//                                listener.onError(e.getExceptionCode()+"", "服务器忙，请稍后重试。");
//                                break;
//                            default:
//                                listener.onError(e.getExceptionCode() + "", s);
//                        }
//                    }
//                });
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PropertyBean>() {
            @Override
            public void onNext(PropertyBean info) {
                listener.getKeepPostItListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getKeepPostItList(new ProgressSubscriber<HttpResult<PropertyBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    public void getConfig(Map map, final IPropertyHistoryListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<CommunityBean>() {
            @Override
            public void onNext(CommunityBean info) {
                listener.getConfigNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getConfigError();
            }
        };
        HttpMethods.getInstance().getConfig(new ProgressSubscriber<HttpResult<CommunityBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}

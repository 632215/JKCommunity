package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BasePropertyDetailsBean;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IPropertyNewsBiz;
import com.jinke.community.service.listener.PropertyNewsListener;
import com.jinke.community.utils.GsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-11.
 */

public class PropertyNewsImpl implements IPropertyNewsBiz {
    Context mContext;

    public PropertyNewsImpl(Context mContext) {
        this.mContext = mContext;
    }

//    @Override
//    public void getAddPostIt(Map<String, String> map, final PropertyNewsListener listener) {
//        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokenDetailsBean>() {
//            @Override
//            public void onNext(BrokenDetailsBean info) {
//                listener.onSuccessListener(info);
//            }
//
//            @Override
//            public void onError(String Code, String Msg) {
//                listener.onErrorListener(Code, Msg);
//            }
//        };
//
//        HttpMethods.getInstance().getPropertyAdd(new ProgressSubscriber<HttpResult<BrokenDetailsBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
//    }

    public void getHouseList(Map<String, String> map, final PropertyNewsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.getHouseListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorListener(Code, Msg);
            }
        };
        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void addPostIt(RequestParams params, final PropertyNewsListener listener) {
        HttpUtils http = new HttpUtils(300000);
        http.send(HttpRequest.HttpMethod.POST,
                HttpMethods.BASE_URL + "postIt/addKeepPostIt",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.i("result-----" + responseInfo.result);
                        BasePropertyDetailsBean basePropertyDetailsBean = null;
                        try {
                            basePropertyDetailsBean = GsonUtil.GsonToBean(responseInfo.result, BasePropertyDetailsBean.class);
                        } catch (Exception e) {
                            listener.onErrorListener("1000", "数据转化异常");
                        }
                        if (basePropertyDetailsBean != null && basePropertyDetailsBean.getErrcode() == 200) {
                            listener.addPostItNext(basePropertyDetailsBean.getData());
                        } else {
                            listener.onErrorListener(basePropertyDetailsBean.getErrcode() + "", basePropertyDetailsBean.getErrmsg());
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        switch (e.getExceptionCode()) {
                            case 0:
                                listener.onErrorListener(e.getExceptionCode() + "", "请检查您的网络连接！");
                                break;
                            case 502:
                            case 500:
                            case 503:
                                listener.onErrorListener(e.getExceptionCode() + "", "服务器忙，请稍后重试。");
                                break;
                            default:
                                listener.onErrorListener(e.getExceptionCode() + "", s);
                        }
                    }
                });
    }

    public void getConfig(Map map, final PropertyNewsListener listener) {
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

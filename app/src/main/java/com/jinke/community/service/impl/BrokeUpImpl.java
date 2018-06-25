package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BaseBrokenDetailsBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.LoginBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IBrokeUpBiz;
import com.jinke.community.service.listener.IBrokeUpListener;
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
 * Created by root on 17-8-6.
 */

public class BrokeUpImpl implements IBrokeUpBiz {
    private Context mContext;

    public BrokeUpImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getUpFile(RequestParams params, final IBrokeUpListener listener) {
        HttpUtils http = new HttpUtils(300000);
        http.send(HttpRequest.HttpMethod.POST,
                HttpMethods.BASE_URL + "brokeNews/addBrokeNews/",
                params, new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.i("result-----" + responseInfo.result);
                        BaseBrokenDetailsBean brokenDetailsBean=null;
                        try {
                            brokenDetailsBean  = GsonUtil.GsonToBean(responseInfo.result, BaseBrokenDetailsBean.class);
                        }catch (Exception e){
                            listener.onError("1000", "数据转化错误");
                        }
                        if (brokenDetailsBean != null && brokenDetailsBean.getErrcode() == 200) {
                            listener.onSuccess((LoginBean) brokenDetailsBean.getData());
                        } else {
                            listener.onError(brokenDetailsBean.getErrcode() + "", brokenDetailsBean.getErrmsg());
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

    @Override
    public void getHouseList(Map<String, String> map, final IBrokeUpListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.getHouseListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}

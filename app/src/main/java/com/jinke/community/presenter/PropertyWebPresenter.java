package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PostItNoticeDetailBean;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.service.impl.PropertyWebImpl;
import com.jinke.community.view.IPropertyWebView;

import java.util.Map;

/**
 * Created by root on 17-8-17.
 */

public class PropertyWebPresenter extends BasePresenter<IPropertyWebView> implements OnRequestListener<PostItNoticeDetailBean> {
    Context mContext;
    PropertyWebImpl web;

    public PropertyWebPresenter(Context mContext) {
        this.mContext = mContext;
        web = new PropertyWebImpl(mContext);
    }

    public void getPostItNoticeDetail(Map<String, String> map) {
        web.getPostItNoticeDetail(map, this);

    }

    @Override
    public void onSuccess(PostItNoticeDetailBean Bean) {
        if (mView != null) {
            mView.onSuccess(Bean);
        }

    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
        }

    }
}

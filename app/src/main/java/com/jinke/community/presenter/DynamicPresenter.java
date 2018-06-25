package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.NoticeOneBean;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.service.impl.DynamicImpl;
import com.jinke.community.view.IDynamicView;

import java.util.Map;

/**
 * Created by root on 17-8-25.
 */

public class DynamicPresenter extends BasePresenter<IDynamicView> implements OnRequestListener<NoticeOneBean> {
    private Context mContext;
    DynamicImpl dynamic;

    public DynamicPresenter(Context mContext) {
        this.mContext = mContext;
        dynamic = new DynamicImpl(mContext);
    }

    public void getNoticeOne(Map<String, String> map) {
        dynamic.getDynamicData(map, this);
        if (mView!=null){
            mView.showLoading();
        }
    }

    @Override
    public void onSuccess(NoticeOneBean noticeOneBean) {
        if (mView != null) {
            mView.hideLoading();
            mView.onSuccess(noticeOneBean);
        }

    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
            mView.hideLoading();
        }
    }
}

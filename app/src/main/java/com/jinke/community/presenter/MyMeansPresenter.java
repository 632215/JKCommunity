package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.service.MyMeansBiz;
import com.jinke.community.service.impl.MyMeanImpl;
import com.jinke.community.service.listener.MyMeansListener;
import com.jinke.community.view.IMyMeansView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by root on 17-8-15.
 */

public class MyMeansPresenter extends BasePresenter<IMyMeansView> implements MyMeansListener {
    private Context mContext;
    private MyMeansBiz myMeansBiz;

    public MyMeansPresenter(Context mContext) {
        this.mContext = mContext;
        myMeansBiz = new MyMeanImpl(mContext);
    }

    //更新用户信息
    public void getAlterMeans(RequestParams params) {
        myMeansBiz.AlterMean(params, this);
        if (mView != null) {
            mView.showLoading();
        }
    }


    /**
     * 更新用户信息成功回调
     * @param avatar
     */
    @Override
    public void onSuccess(String avatar) {
        if (mView != null) {
            mView.getAlterMeansNext(avatar);
            mView.hindLoading();
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
            mView.hindLoading();
        }

    }
}

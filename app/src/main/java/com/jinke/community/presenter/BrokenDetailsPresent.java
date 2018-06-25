package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.service.impl.BrokenDetailsImpl;
import com.jinke.community.service.listener.IBrokenDetailsListener;
import com.jinke.community.view.IBrokenDetailsView;

import java.util.Map;

/**
 * Created by root on 17-8-11.
 */

public class BrokenDetailsPresent extends BasePresenter<IBrokenDetailsView> implements IBrokenDetailsListener {
    private Context mContext;
    private BrokenDetailsImpl brokenDetails;

    public BrokenDetailsPresent(Context mContext) {
        this.mContext = mContext;
        brokenDetails = new BrokenDetailsImpl(mContext);
    }

    /**
     * 爆料详情页面
     *
     * @param map
     */
    public void getBrokeNewsDetail(Map<String, String> map) {
        brokenDetails.getBrokeNewsDetail(map, this);
    }

    /**
     * 报事详情页面
     */
    public void getPostItDetail(Map<String, String> map) {
        brokenDetails.getPostItDetail(map, this);
    }

    @Override
    public void onSuccess(BrokenDetailsBean bean) {
        if (mView != null) {
            mView.onSuccess(bean);
        }

    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void onPropertyInfo(BrokenDetailsBean bean) {
        if (mView != null) {
            mView.onPropertyInfo(bean);
        }
    }

    /**
     * 查询大管家报事详情
     *
     * @param map
     */
    public void getKeeperDetail(Map map) {
        brokenDetails.getKeeperDetail(map, this);
    }

    @Override
    public void getKeeperDetailNext(KeepPropertyBean info) {
        if (mView != null) {
            mView.getKeeperDetailNext(info);
        }
    }
}

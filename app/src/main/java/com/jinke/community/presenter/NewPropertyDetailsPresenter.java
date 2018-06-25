package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.PropertyProgressBean;
import com.jinke.community.service.impl.NewPropertyDetailsImpl;
import com.jinke.community.service.listener.NewPropertyDetailsListener;
import com.jinke.community.view.NewPropertyDetailsView;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/25.
 */

public class NewPropertyDetailsPresenter extends BasePresenter<NewPropertyDetailsView> implements
        NewPropertyDetailsListener {
    private Context mContext;
    private NewPropertyDetailsImpl mImpl;
    private Map map = null;
    private KeepPropertyBean keepPropertyBean;

    public NewPropertyDetailsPresenter(Context mContext) {
        this.mContext = mContext;
        mImpl = new NewPropertyDetailsImpl(mContext);
    }

    public void getKeeperDetail(Map map) {
        if (mView != null && mImpl != null) {
            mView.showLoading();
            this.map = map;
            mImpl.getKeeperDetail(map, this);
        }
    }

    /**
     * 获取报事详情成功回调
     *
     * @param info
     */
    @Override
    public void getKeeperDetailSuccess(KeepPropertyBean info) {
        this.keepPropertyBean = info;
        if (mView != null && mImpl != null) {
            mImpl.getProgress(map, this);
        }
    }

    /**
     * 获取报事详情节点成功回调
     *
     * @param info
     */
    @Override
    public void getProgressSuccess(PropertyProgressBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getProgressSuccess(info, keepPropertyBean);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.hideLoading();
            //防止大管家接口出现问题
            if (keepPropertyBean != null)
                mView.getKeeperDetailSuccess(keepPropertyBean);
            else
                mView.showToast(code, msg);
        }
    }
}

package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.BrokenPersonBean;
import com.jinke.community.service.BrokenPersonBiz;
import com.jinke.community.service.impl.BrokenPersonImpl;
import com.jinke.community.service.listener.BrokenPersonlistener;
import com.jinke.community.view.BrokenPersonView;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BrokenPersonPresenter extends BasePresenter<BrokenPersonView> implements BrokenPersonlistener {
    private Context mContext;
    private BrokenPersonBiz mBiz;

    public BrokenPersonPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new BrokenPersonImpl(mContext);
    }

    /**
     * 个人爆料列表获取
     *
     * @param map
     */
    public void getBrokePerson(Map<String, String> map) {
        if (mView != null) {
            mBiz.getBrokePerson(map, this);
        }
    }

    @Override
    public void getBrokePersonNext(BrokenPersonBean info) {
        mView.getBrokePersonNext(info);
    }

    @Override
    public void onError(String code, String msg) {
        mView.showMsg(msg);
    }
}

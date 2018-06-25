package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.service.BrokeStageBiz;
import com.jinke.community.service.impl.BrokeStageImpl;
import com.jinke.community.service.listener.BrokeStageListener;
import com.jinke.community.view.BrokeStageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BrokeStagePresenter extends BasePresenter<BrokeStageView> implements BrokeStageListener {
    private Context mContext;
    private BrokeStageBiz mBiz;

    public BrokeStagePresenter(Context mContext) {
        this.mContext = mContext;
        this.mBiz = new BrokeStageImpl(mContext);
    }

    /**
     * 获取平台爆料列表
     *
     * @param map
     */
    public void getStageBrokeList(Map<String, String> map) {
        if (mView != null) {
            mBiz.getStageBrokeList(map, this);
        }
    }

    @Override
    public void getStageBrokeListNext(NoticeListBean info) {
        if (mView != null) {
            mView.getStageBrokeListNext(info);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
        }
    }

    /**
     * 点赞处理
     *
     * @param map
     */
    public void praiseClick(Map map) {
        mBiz.praiseClick(map, this);
    }

    @Override
    public void praiseClickNext(PraiseresultBean info) {
        if (mView != null) {
            mView.praiseClickNext(info);
        }
    }

    /**
     * 获取当前用户所属小区列表
     */
    public void getCommunity() {
        if (mView != null) {
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            mBiz.getCommunity(map, this);
        }
    }

    @Override
    public void getCommunityNext(UserCommunityBean info) {
        if (mView != null) {
            mView.getCommunityNext(info);
        }
    }
}

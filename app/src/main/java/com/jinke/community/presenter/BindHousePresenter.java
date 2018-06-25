package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.AutoBindBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.service.BindHouseBiz;
import com.jinke.community.service.impl.BindHouseImpl;
import com.jinke.community.service.listener.BindHouseListener;
import com.jinke.community.view.BindHouseView;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BindHousePresenter extends BasePresenter<BindHouseView> implements BindHouseListener {
    private Context mContext;
    private BindHouseBiz mBiz;

    public BindHousePresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new BindHouseImpl(mContext);
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    /**
     * 注册用户
     *
     * @param map
     */
    public void getRegisterData(Map<String, String> map) {
        if (mView != null) {
            mBiz.getRegisterData(map, this);
        }
    }

    @Override
    public void getRegisterDataNext(RegisterLoginBean info) {
        if (mView != null) {
            mView.getRegisterDataNext(info);
        }
    }

    /**
     * 绑定房屋
     *
     * @param map
     */
    public void autoBindHouse(Map<String, String> map) {
        if (mView != null) {
            mBiz.autoBindHouse(map, this);
        }
    }


    @Override
    public void autoBindHouseNext(AutoBindBean info) {
        if (mView != null) {
            mView.autoBindHouseNext(info);
        }
    }

    /**
     * 设置默认房屋
     *
     * @param map
     */
    public void setDefaultHouse(Map map) {
        mBiz.setDefaultHouse(map, this);
    }

    @Override
    public void setDefaultHouseNext(SetDefaultHouseBean info) {
        if (mView != null) {
            mView.setDefaultHouseNext(info);
        }
    }
}

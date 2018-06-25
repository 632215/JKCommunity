package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.GuestCountBean;
import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.bean.ReasonBean;
import com.jinke.community.service.impl.OpenDoorPasswordImpl;
import com.jinke.community.service.listener.IOpenDoorPasswordListener;
import com.jinke.community.view.IOpenDoorPasswordView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorPasswordPresenter extends BasePresenter<IOpenDoorPasswordView> implements IOpenDoorPasswordListener {
    private Context mContext;

    OpenDoorPasswordImpl password;

    public OpenDoorPasswordPresenter(Context mContext) {
        this.mContext = mContext;
        password = new OpenDoorPasswordImpl(mContext);
    }

    public void getPurposeListDate(Map<String, String> map) {
        password.getPurposeListDate(map, this);
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }


    @Override
    public void onReasonBean(ReasonBean bean) {
        if (mView != null && bean.getListDate() != null) {
            List<GuestCountBean> listBean = new ArrayList<>();
            for (ReasonBean.ListDateBean list : bean.getListDate()) {
                GuestCountBean guestConuntBean = new GuestCountBean(list.getName(), false, list.getId());
                listBean.add(guestConuntBean);
            }
            if (null != listBean && 0 != listBean.size()) {
                listBean.get(0).setIsshow(true);

            }
            mView.onReasonBean(listBean);
        }
    }


    public void getHouseList(Map<String, String> map) {
        password.getHouseListDate(map, this);
    }

    @Override
    public void getHouseListDateError(String code, String msg) {
        if (mView != null) {
            mView.getHouseListDateError();
        }
    }

    @Override
    public void getHouseListDateNext(HouseListInfo info) {
        if (mView != null) {
            mView.getHouseListDateNext(info);
        }
    }
}

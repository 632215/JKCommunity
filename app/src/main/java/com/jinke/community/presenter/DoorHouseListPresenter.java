package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.service.IDoorHouseListBiz;
import com.jinke.community.service.impl.DoorHouseListImpl;
import com.jinke.community.view.IDoorHouseListView;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public class DoorHouseListPresenter extends BasePresenter<IDoorHouseListView> implements OnRequestListener<HouseListInfo> {
    private Context mContext;
    private IDoorHouseListBiz houseListBiz;

    public DoorHouseListPresenter(Context mContext) {
        this.mContext = mContext;
        houseListBiz = new DoorHouseListImpl(mContext);
    }

    public void getHouseList(Map<String, String> map) {
        houseListBiz.getHouseListDate(map,this);
    }

    @Override
    public void onSuccess(HouseListInfo info) {
        if (mView != null) {
            mView.onSuccess(info);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
        }
    }
}

package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.WorkingSortBean;
import com.jinke.community.service.impl.WorkingImpl;
import com.jinke.community.service.listener.IWorkingListener;
import com.jinke.community.ui.activity.step.PreferencesUtils;
import com.jinke.community.view.IWalkingView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/31.
 */

public class WorkingPresenter extends BasePresenter<IWalkingView> implements IWorkingListener {
    private Context mContext;
    WorkingImpl working;

    public WorkingPresenter(Context mContext) {
        this.mContext = mContext;
        working = new WorkingImpl(mContext);
    }

    public void getWorkingSort() {
        PreferencesUtils preferencesUtils = new PreferencesUtils(mContext);
        int setup = (int) preferencesUtils.getParam("stepCount", 0);
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("count", String.valueOf(setup));
        working.getUpDate(map, this);
    }

    @Override
    public void onWorkingSort(WorkingSortBean bean) {
        if (mView != null) {
            mView.onWorkingSort(bean);
        }

    }

    @Override
    public void onUpdate(WorkingSortBean bean) {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        working.getWorkingSort(map, this);
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }

    }
}

package com.jinke.community.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.AuthorizationBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.AddAuthorizedBiz;
import com.jinke.community.service.impl.AddAuthorizedImpl;
import com.jinke.community.service.listener.AddAuthorizedListener;
import com.jinke.community.view.AddAuthorizedView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/11/21.
 */

public class AddAuthorizedPresenter extends BasePresenter<AddAuthorizedView> implements AddAuthorizedListener {
    private Context mContext;
    private AddAuthorizedBiz mBiz;

    public AddAuthorizedPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new AddAuthorizedImpl(mContext);
    }

    public void updateAuthorized(List<HouseListBean.ListBean.Grants> grantsList, HouseListBean.ListBean listBean, String deleteIds) {
        if (grantsList != null && grantsList.size() > 0) {
            AuthorizationBean authorization = new AuthorizationBean();
            authorization.setHouseId(listBean.getHouse_id());
            List<AuthorizationBean.Grants> authorizationBeans = new ArrayList<>();
            //拼接字符串
            for (HouseListBean.ListBean.Grants bean : grantsList) {
                AuthorizationBean.Grants authorizationBean = new AuthorizationBean.Grants();
                authorizationBean.setGrantId(bean.getGrantId() == 0 ? "" : bean.getGrantId() + "");
                authorizationBean.setGrantName(bean.getGrantName());
                authorizationBean.setGrantPhone(bean.getGrantPhone());
                authorizationBean.setRelation(bean.getRelation());
                authorizationBeans.add(authorizationBean);
            }
            authorization.setDeleteIds(deleteIds);
            authorization.setGrants(authorizationBeans);
            String json = new Gson().toJson(authorization);
            LogUtils.i("json----" + json);
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("parameters", json);
            mBiz.addAuthorization(map, this);
//            mView.showLoading();
        }
    }

    @Override
    public void addAuthorizationNext(HouseListBean info) {
        if (mView != null) {
//            mView.hideLoading();
            mView.addAuthorizationNext(info);
        }
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
//            mView.hideLoading();
            mView.showMsg(msg);
        }
    }
}

package com.jinke.community.presenter;

import android.content.Context;
import android.content.DialogInterface;

import com.google.gson.Gson;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.AuthorizationBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.IAuthorizationBiz;
import com.jinke.community.service.impl.AuthorizationIpml;
import com.jinke.community.service.listener.IAuthorizationListener;
import com.jinke.community.ui.adapter.AuthorAdaptr;
import com.jinke.community.ui.toast.CallPhoneDialog;
import com.jinke.community.view.IAuthorizationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-7.
 */

public class AuthorizationPresenter extends BasePresenter<IAuthorizationView> implements IAuthorizationListener, CallPhoneDialog.OnCancelListener {
    private Context mContext;
    private IAuthorizationBiz authorizationBiz;

    public AuthorizationPresenter(Context mContext) {
        this.mContext = mContext;
        authorizationBiz = new AuthorizationIpml(mContext);
    }

    private AuthorAdaptr mAdapter;
    private HouseListBean.ListBean mListBean;
    private String mDeleteIds;

    public void getJsonData(AuthorAdaptr adapter, HouseListBean.ListBean listBean, String deleteIds) {
        this.mAdapter = adapter;
        this.mListBean = listBean;
        this.mDeleteIds = deleteIds;
        startGetJsonData();

    }

    private void startGetJsonData() {
        if (mView != null) {
            List<HouseListBean.ListBean.Grants> grantsList = mAdapter.getListData();
            for (HouseListBean.ListBean.OwnersBean bean : mListBean.getOwners()) {
                for (HouseListBean.ListBean.Grants grants : grantsList) {
                    if (bean.getPhone().equals(grants.getGrantPhone())) {
                        mView.showMsg("不能给业主授权哦！");
                        return;
                    }
                }
            }
            if (grantsList.size() > 0) {
                HouseListBean.ListBean.Grants grants = grantsList.get(grantsList.size() - 1);
                if (StringUtils.isEmpty(grants.getGrantName())) {
                    mView.showMsg("当前个人权限编辑还没完成哦！");
                    return;
                }
                if (grants.getGrantPhone().length() != 11) {
                    mView.showMsg("您输入的电话号码格式有误!");
                    return;
                }

                if (StringUtils.isEmpty(grants.getRelation())) {
                    mView.showMsg("当前个人权限编辑还没完成哦！");
                    return;
                }

                AuthorizationBean authorization = new AuthorizationBean();
                authorization.setHouseId(mListBean.getHouse_id());
                List<AuthorizationBean.Grants> authorizationBeans = new ArrayList<>();
                if (StringUtils.isEmpty(mDeleteIds)) {
                    //拼接字符串
                    for (HouseListBean.ListBean.Grants bean : grantsList) {
                        AuthorizationBean.Grants authorizationBean = new AuthorizationBean.Grants();
                        authorizationBean.setGrantId(bean.getGrantId() == 0 ? "" : bean.getGrantId() + "");
                        authorizationBean.setGrantName(bean.getGrantName());
                        authorizationBean.setGrantPhone(bean.getGrantPhone());
                        authorizationBean.setRelation(bean.getRelation());
                        authorizationBeans.add(authorizationBean);
                    }
                }
                authorization.setDeleteIds(StringUtils.isEmpty(mDeleteIds) ? "" : mDeleteIds.substring(0, mDeleteIds.length() - 1));
                authorization.setGrants(authorizationBeans);
                String json = new Gson().toJson(authorization);
                LogUtils.i("json----" + json);
                Map<String, String> map = new HashMap<>();
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("parameters", json);
                authorizationBiz.getaddAuthorization(map, this);
                mView.showDialog();
            } else {//删除该房屋下面所有人的权限
                AuthorizationBean authorization = new AuthorizationBean();
                authorization.setDeleteIds(StringUtils.isEmpty(mDeleteIds) ? "" : mDeleteIds.substring(0, mDeleteIds.length() - 1));
                authorization.setHouseId(mListBean.getHouse_id());
                authorization.setGrants(new ArrayList<AuthorizationBean.Grants>());
                String json = new Gson().toJson(authorization);
                Map<String, String> map = new HashMap<>();
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("parameters", json);
//                getaddAuthorization(map);
                authorizationBiz.deleteAuthorization(map, this);
            }
        }
    }

    @Override
    public void onSuccess(HouseListBean info) {
        if (mView != null) {
            mView.onSuccess();
        }
    }

    @Override
    public void deleteOnNext() {
        if (mView != null) {

            mView.deleteOnNext(mContext.getResources().getString(R.string.activity_authorization_delete_success));
        }
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }

    /**
     * 获取房屋列表
     */
    public void getHouseListData() {
        Map map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        authorizationBiz.getHouseListData(map, this);
    }

    @Override
    public void getHouseListDataNext(HouseListBean info) {
        if (mView != null) {
            mView.getHouseListDataNext(info);
        }
    }
}

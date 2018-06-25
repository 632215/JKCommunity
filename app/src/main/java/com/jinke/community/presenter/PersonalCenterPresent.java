package com.jinke.community.presenter;

import android.app.Activity;
import android.content.Intent;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.impl.PersonalCenterImpl;
import com.jinke.community.service.listener.IPersonalCenterListener;
import com.jinke.community.ui.activity.house.MyHouseActivity;
import com.jinke.community.ui.toast.BindHouseDialog;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.IPersonalCenterView;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-16.
 */

public class PersonalCenterPresent extends BasePresenter<IPersonalCenterView> implements IPersonalCenterListener, BindHouseDialog.onCallPhoneListener {
    private Activity mContext;
    private PersonalCenterImpl center;
    private UMShareAPI shareAPI;
    private BindHouseDialog dialog;

    public PersonalCenterPresent(Activity mContext) {
        this.mContext = mContext;
        center = new PersonalCenterImpl(mContext);
        shareAPI = UMShareAPI.get(mContext);
    }

    //获取配置信息
    public void getConfigUrl() {
        Map<String, String> map = new HashMap<>();
        if (MyApplication.getBaseUserBean() == null || StringUtils.isEmpty(MyApplication.getBaseUserBean().getAccessToken()))
            return;
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        center.getConfigUrl(map, this);
    }

    /**
     * 获取配置信息成功回调
     *
     * @param bean
     */
    @Override
    public void onConfigUrl(UrlConfigBean bean) {
        if (mView != null) {
            mView.onConfigUrl(bean);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.showMsg(msg);
        }
    }

    //获取车位信息
    public void getParkingInfo() {
        if (mView != null) {
            mView.showLoading();
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            center.getParkingInfo(map, this);
        }
    }

    /**
     * 获取车位信息回调
     */
    @Override
    public void getParkInfoNext(ParkInfoBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getParkInfoNext(info);
        }
    }

    public void showBindHouseTips() {
        if (dialog == null) {
            dialog = new BindHouseDialog(mContext, this, "");
        }
        dialog.show();
    }

    @Override
    public void onSure(String phone) {
        if (mContext != null) {
            dialog.dismiss();
            mContext.startActivity(new Intent(mContext, MyHouseActivity.class));
        }
    }

    /**
     * 获取房屋列表
     */
    public void getHouseList() {
        if (center != null) {
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            center.getHouseList(map, this);
        }
    }

    /**
     * 获取房屋列表成功回调
     */
    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.getHouseListNext(info);
        }
    }

    @Override
    public void getHouseListError() {
        if (mView != null) {
            mView.getHouseListError();
        }
    }

    @Override
    public void onDefaultDataNext(DefaultHouseBean info) {
        if (mView != null) {
            BaseUserBean userBean = MyApplication.getBaseUserBean();
            userBean.setHouse(StringUtils.isEmpty(info.getAddress()) ? false : true);
            userBean.setIdentity(info.getIdentity());
            userBean.setName(info.getName());
            SharedPreferencesUtils.saveBaseUserBean(mContext, userBean);
            mView.onDefaultDataNext(info);
        }
    }

    //获取预存费用
    public void getIntegral() {
        HouseListBean.ListBean defaultHouseBean = MyApplication.getInstance().getDefaultHouse();
        if (defaultHouseBean != null && defaultHouseBean.getDft_house() == 1 && defaultHouseBean.getHouse_id() != null) {
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("houseId", defaultHouseBean.getHouse_id());
            center.getPrePayList(map, this);
        }
    }

    /**
     * 获取预存费用成功回调
     *
     * @param bean
     */
    @Override
    public void onPrePayList(PrepaidExpensesBean bean) {
        if (mView != null) {
            mView.onPrePayList(bean);
        }
    }

    /**
     * 获取预存费用失败回调
     *
     */
    @Override
    public void getPrePayListError() {
        if (mView != null) {
            mView.getPrePayListError();
        }
    }

    //获取积分信息
    public void getPoint() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        center.getPoint(map, this);
    }

    /**
     * 获取积分信息成功回调
     */
    @Override
    public void getPointInfo(PrepaidExpensesBean bean) {
        if (mView != null) {
            mView.getPointInfo(bean);
        }
    }

    //获取用户信息
    public void getUserInfo() {
        HashMap map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        center.getUserInfo(map, this);
    }

    @Override
    public void getUserInfoNext(UserInfo info) {
        if (mView != null) {
            BaseUserBean userBean = MyApplication.getBaseUserBean();
            userBean.setAvatar(info.getAvatar());
//            userBean.setIdentity(info.getIdentity());
            userBean.setSex(info.getSex());
//            userBean.setName(info.getName());
            userBean.setPhone(info.getPhone());
            userBean.setNickName(info.getNickName());
            userBean.setUid(info.getUid());
            userBean.setIsSuccess(info.getIsSuccess());
            //保存用户基本信息
            SharedPreferencesUtils.saveBaseUserBean(mContext, userBean);
            mView.getUserInfoNext(info);
        }
    }

    //获取用户信息
    public void getMsg() {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("page", "1");
        map.put("rows", 10+"");
        if (mView != null && center != null) {
            center.getMsg(map, this);
        }
    }

    /**
     * 获取用户信息 回调
     * @param info
     */
    @Override
    public void getMsgNext(MsgBean info) {
        if (mView != null && center != null) {
            mView.getMsgNext(info);
        }
    }
}

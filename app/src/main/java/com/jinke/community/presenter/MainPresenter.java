package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.XiMoDriveListBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.service.impl.MainImpl;
import com.jinke.community.service.listener.IMainListener;
import com.jinke.community.ui.activity.step.PreferencesUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.IMainView;
import com.jkapi.entrancelibrary.Account;
import com.jkapi.entrancelibrary.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-7-21.
 */

public class MainPresenter extends BasePresenter<IMainView> implements IMainListener {
    private Context mContext;
    private MainImpl main;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
        main = new MainImpl(mContext);
    }

    public void getUserCenter() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        //获取用户信息
        main.getUserInfo(map, this);
        //获取url配置信息
        main.getUrlConfig(map, this);
        //获取门禁设备（废弃）
//        Map<String, String> maps = new HashMap<>();
//        main.getDeviceListDate(maps, this);
        //获取门禁设备
        getDoorDevice();
    }

    private void getDoorDevice() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        //setBindDoorGuard这个接口：ownerPhone传当前登陆用户的默认房屋的业主电话号码（如果多个就取第一个），如果没有默认房屋就取当前用户的手机号码
        String ownerPhone = "";
        HouseListBean.ListBean houseListBean = MyApplication.getInstance().getDefaultHouse();
        if (houseListBean != null) {
            if (houseListBean.getOwners() != null && !StringUtils.isEmpty(houseListBean.getOwners().get(0).getPhone())) {
                ownerPhone = houseListBean.getOwners().get(0).getPhone();
            }
        } else {
            ownerPhone = MyApplication.getBaseUserBean().getPhone();
        }
        map.put("ownerPhone", ownerPhone);
        main.getBandingControl(map, this);
    }

    /**
     * 绑定门禁设备成功
     * @param infos
     */
    @Override
    public void onBandingControl(XiMoDriveListBean infos) {
        if (mView != null) {
            ArrayList<Equipment> equipmentArrayList = new ArrayList<>();
            if (null != infos.getListDate() && 0 != infos.getListDate().size()) {
                for (XiMoDriveListBean.ListDateBean dateBean : infos.getListDate()) {
                    Equipment equipment = new Equipment();
                    equipment.setDeviceId(dateBean.getDeviceId());
                    equipment.setMAC_Addr(dateBean.getMAC_Addr());
                    equipment.setMAC_AddrIos(dateBean.getMAC_AddrIos());
                    equipment.setDeviceName(dateBean.getDeviceName());
                    equipment.setDeviceNum(dateBean.getDeviceNum());
                    equipment.setDeviceSecret(dateBean.getDeviceSecret());
                    equipment.setDeviceType(dateBean.getDeviceType());
                    equipment.setManufacturerId(dateBean.getManufacturerId());
                    equipment.setOwnerPhone(dateBean.getOwnerPhone());
                    equipmentArrayList.add(equipment);
                }
            }
            Account.saveDevices(mContext, equipmentArrayList);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void onUrlSuccess(UrlConfigBean bean) {
        if (bean != null) {
            SharedPreferencesUtils.saveUrlConfig(mContext, bean);
        }
    }

    @Override
    public void onDeviceList(XiMoDriveListBean infos) {
        if (mView != null) {
            ArrayList<Equipment> equipmentArrayList = new ArrayList<>();
            if (null != infos.getListDate() && 0 != infos.getListDate().size()) {
                for (XiMoDriveListBean.ListDateBean dateBean : infos.getListDate()) {
                    Equipment equipment = new Equipment();
                    equipment.setDeviceId(dateBean.getDeviceId());
                    equipment.setMAC_Addr(dateBean.getMAC_Addr());
                    equipment.setMAC_AddrIos(dateBean.getMAC_AddrIos());
                    equipment.setDeviceName(dateBean.getDeviceName());
                    equipment.setDeviceNum(dateBean.getDeviceNum());
                    equipment.setDeviceSecret(dateBean.getDeviceSecret());
                    equipment.setDeviceType(dateBean.getDeviceType());
                    equipment.setManufacturerId(dateBean.getManufacturerId());
                    equipment.setOwnerPhone(dateBean.getOwnerPhone());
                    equipmentArrayList.add(equipment);
                }
            }
            Account.saveDevices(mContext, equipmentArrayList);
        }
    }

    @Override
    public void onUserInfo(UserInfo info) {
        if (mView != null) {
            //更新本地信息
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
        }
    }

    @Override
    public void onDeriveError(String code, String msg) {
        if (code.equals("2")) {//调取门禁设备列表时，如果门禁列表返回ｃｏｄｅ为２则调取绑定门禁列表
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            //setBindDoorGuard这个接口：ownerPhone传当前登陆用户的默认房屋的业主电话号码（如果多个就取第一个），如果没有默认房屋就取当前用户的手机号码
            String ownerPhone = "";
            HouseListBean.ListBean houseListBean = MyApplication.getInstance().getDefaultHouse();
            if (houseListBean != null) {
                if (houseListBean.getOwners() != null && !StringUtils.isEmpty(houseListBean.getOwners().get(0).getContact())) {
                    ownerPhone = houseListBean.getOwners().get(0).getContact();
                }
            } else {
                ownerPhone = MyApplication.getBaseUserBean().getPhone();
            }
            map.put("ownerPhone", ownerPhone);
            main.getBandingControl(map, this);
        }
    }

    public void getUpDate() {
        PreferencesUtils preferencesUtils = new PreferencesUtils(mContext);
        int setup = (int) preferencesUtils.getParam("stepCount", 0);
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("count", String.valueOf(setup));
        main.getUpDate(map, this);
    }

    /**
     * 获取房屋信息
     */
    public void getHouseList() {
        if (mView != null) {
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            main.getHouseList(map, this);
        }
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.getHouseListNext(info);
        }
    }

    /**
     * 获取红点信息
     */
    public void getRedCircle() {
        if (main != null) {
            Map map = new HashMap();
            if (MyApplication.getBaseUserBean() == null || StringUtils.isEmpty(MyApplication.getBaseUserBean().getAccessToken()))
                return;
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            main.getRedCircle(map, this);
        }
    }


    @Override
    public void getRedCircleNext(RedCircleGroupBean info) {
        if (mView != null) {
            mView.getRedCircleNext(info);
        }
    }
}

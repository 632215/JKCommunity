package com.jinke.community.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.acachebean.WeatherBean;
import com.jinke.community.service.IHouseKeeperBiz;
import com.jinke.community.service.impl.HouseKeeperImpl;
import com.jinke.community.service.listener.IHouseKeeperListener;
import com.jinke.community.ui.activity.house.MyHouseActivity;
import com.jinke.community.ui.toast.BindHouseDialog;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.IHouseKeeperView;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-7-25.
 */

public class HouseKeeperPresenter extends BasePresenter<IHouseKeeperView> implements IHouseKeeperListener, BindHouseDialog.onCallPhoneListener {
    private Context mContext;
    private IHouseKeeperBiz houseKeeperBiz;
    private BindHouseDialog dialog;
    private String communityInfo;

    public HouseKeeperPresenter(Context mContext) {
        this.mContext = mContext;
        houseKeeperBiz = new HouseKeeperImpl(mContext);
    }

    //获取默认信息
    public void getDefaultData() {
        HashMap map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        houseKeeperBiz.getDefaultData(map, this);
    }

    /**
     * 获取默认信息成功回调
     */
    @Override
    public void onDefaultDataNext(DefaultHouseBean bean) {
        if (mView != null) {
            mView.onDefaultHouse(bean);
            BaseUserBean userBean = MyApplication.getBaseUserBean();
            userBean.setHouse(StringUtils.isEmpty(bean.getAddress()) ? false : true);
            userBean.setIdentity(bean.getIdentity());
            userBean.setName(bean.getName());
            SharedPreferencesUtils.saveBaseUserBean(mContext, userBean);
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("cityCode", bean.getCityCode());
            map.put("communityName", bean.getCommunity_id());
            map.put("areaNum", bean.getArea_num());
            SharedPreferencesUtils.clearCommunityId(mContext);
            houseKeeperBiz.getHouseValue(map, this);//获取房屋估价
        }
    }

    @Override
    public void onHouseValue(HouseValueBean bean) {
        if (mView != null) {
            mView.onHouseValue(bean);
        }
    }

    @Override
    public void onNoticeList(NoticeListBean bean) {
        if (mView != null) {
            mView.onNoticeList(bean);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
            switch (code) {
                case "3500":
                    if (!StringUtils.isEmpty(SharedPreferencesUtils.getCommunityId(mContext))) {
                        communityInfo = SharedPreferencesUtils.getCommunityId(mContext);
                        mView.setDefaultData(communityInfo.substring(0, communityInfo.indexOf(",")));
                    } else {
                        mView.getHouseListEmpty();
                    }
                    break;
                case "4007":
                    mView.getHouseListEmpty();
                    break;
            }
        }
    }

    /**
     * 弹框提示绑定房屋
     */
    public void showBandingHouseDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.show();
        } else {
            dialog = new BindHouseDialog(mContext, this, "");
            dialog.show();
        }
    }

    public void setAnimation(ImageView imageView, int anim) {
        if (imageView != null && mContext != null) {
            Animation animation = AnimationUtils.loadAnimation(mContext, anim);
            imageView.startAnimation(animation);
        }
    }

    @Override
    public void onSure(String phone) {
        if (mContext != null) {
            dialog.dismiss();
            mContext.startActivity(new Intent(mContext, MyHouseActivity.class));
        }
    }

    public void praiseClick(Map map) {
        houseKeeperBiz.praiseClick(map, this);
    }

    @Override
    public void praiseClickNext(PraiseresultBean info) {
        if (mView != null) {
            mView.praiseClickNext(info);
        }
    }

    public void getBreakList(Map<String, String> call) {
        houseKeeperBiz.getBreakList(call, this);//获取爆料、公告列表
    }

    /**
     * 获取天气数据
     *
     * @param map
     */
    public void getWeatheInfo(Map<String, String> map) {
        houseKeeperBiz.getWeatheInfo(map, this);//获取天气数据
    }

    @Override
    public void getWeatheInfoNext(WeatherBean bean) {
        if (mView != null) {
            mView.getWeatheInfoNext(bean);
        }
    }

    /**
     * 获取房屋列表
     */
    public void getHouseList() {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        houseKeeperBiz.getHouseList(map, this);
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.getHouseListNext(info);
        }
    }

    public String getCommitId() {
        String communityId = "";
        if (StringUtils.isEmpty(SharedPreferencesUtils.getCommunityId(mContext)) && SharedPreferencesUtils.getDefaultHouseInfo(mContext) != null) {
            communityId = SharedPreferencesUtils.getDefaultHouseInfo(mContext).getCommunity_id();
        } else {
            String communityInfo = SharedPreferencesUtils.getCommunityId(mContext);
            communityId = communityInfo.substring(communityInfo.indexOf(",") + 1);
        }
        return communityId;
    }
}


package com.jinke.community.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.ControlBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.HouseBean;
import com.jinke.community.bean.TempHouseBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.RedCircleGroupBean;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-4.
 */

public class SharedPreferencesUtils {
    static SharedPreferences sharedPreferences;

    /**
     * 保存用户基本信息
     *
     * @param mContext
     * @param userBean
     */
    public static void saveBaseUserBean(Context mContext, BaseUserBean userBean) {
        sharedPreferences = mContext.getSharedPreferences("baseUser", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("openId", userBean.getOpenid());
        editor.putString("accessToken", userBean.getAccessToken());
        editor.putString("avatar", userBean.getAvatar());
        editor.putString("identity", userBean.getIdentity());
        editor.putString("name", userBean.getName());
        editor.putString("phone", userBean.getPhone());
        editor.putInt("uid", userBean.getUid());
        editor.putString("sex", userBean.getSex());
        editor.putString("nickname", userBean.getNickName());
        editor.putString("platform", userBean.getPlatformName());
        editor.putString("servicePhone", userBean.getServicePhone());
        editor.putBoolean("isHouse", userBean.isHouse());
        editor.putBoolean("isShow", userBean.isShow());
        editor.putString("pre_money", userBean.getPre_money());
        editor.putString("point_num", userBean.getPoint_num());
        editor.putString("isSuccess", userBean.getIsSuccess());
        editor.commit();
    }

    /**
     * 获取用户基本信息
     *
     * @param mContext 上下文对象
     * @return
     */
    public static BaseUserBean getBaseUserBean(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("baseUser", Activity.MODE_PRIVATE);
        BaseUserBean bean = new BaseUserBean();
        bean.setOpenid(sharedPreferences.getString("openId", ""));
        bean.setAccessToken(sharedPreferences.getString("accessToken", ""));
        bean.setAvatar(sharedPreferences.getString("avatar", ""));
        bean.setIdentity(sharedPreferences.getString("identity", ""));
        bean.setName(sharedPreferences.getString("name", ""));
        bean.setPhone(sharedPreferences.getString("phone", ""));
        bean.setSex(sharedPreferences.getString("sex", ""));
        bean.setNickName(sharedPreferences.getString("nickname", ""));
        bean.setPlatformName(sharedPreferences.getString("platform", ""));
        bean.setServicePhone(sharedPreferences.getString("servicePhone", ""));
        bean.setUid(sharedPreferences.getInt("uid", 0));
        bean.setHouse(sharedPreferences.getBoolean("isHouse", false));
        bean.setShow(sharedPreferences.getBoolean("isShow", false));
        bean.setPre_money(sharedPreferences.getString("pre_money", "0"));
        bean.setPoint_num(sharedPreferences.getString("point_num", "0"));
        bean.setIsSuccess(sharedPreferences.getString("isSuccess", ""));
        return bean;
    }

    public static void clearBaseUserBean(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("baseUser", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存房屋默认信息
     *
     * @param mContext
     */
    public static void saveDefaultHouse(Context mContext, DefaultHouseBean bean) {
        sharedPreferences = mContext.getSharedPreferences("defaultHouse", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("community_id", bean.getCommunity_id());
        editor.putString("community_name", bean.getCommunity_name());
        editor.putString("address", bean.getAddress());
        editor.putString("house_id", bean.getHouse_id());
        editor.putString("house_name", bean.getHouse_name());
        editor.commit();
    }

    /**
     * 获取默认房屋信息
     *
     * @param mContext
     * @return
     */
    public static DefaultHouseBean getDefaultHouseInfo(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("defaultHouse", Activity.MODE_PRIVATE);
        DefaultHouseBean houseBean = new DefaultHouseBean();
        houseBean.setCommunity_id(sharedPreferences.getString("community_id", ""));
        houseBean.setCommunity_name(sharedPreferences.getString("community_name", ""));
        houseBean.setHouse_id(sharedPreferences.getString("house_id", ""));
        houseBean.setHouse_name(sharedPreferences.getString("house_name", ""));
        houseBean.setAddress(sharedPreferences.getString("address", ""));
        return houseBean;
    }

    public static void clearDefaultHouseBean(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("baseUser", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void saveUrlConfig(Context mContext, UrlConfigBean bean) {
        sharedPreferences = mContext.getSharedPreferences("urlConfig", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("shopCartUrl", bean.getShopCartUrl());
        editor.putString("specUrl", bean.getSpecUrl());
        editor.putString("qaUrl", bean.getQaUrl());
        editor.putString("movieUrl", bean.getMovieUrl());
        editor.putString("ayiHomeUrl", bean.getAyiHomeUrl());
        editor.putString("tourUrl", bean.getTourUrl());
        editor.putString("shopOrderUrl", bean.getShopOrderUrl());
        editor.putString("tokenTypeCookieName", bean.getTokenTypeCookieName());
        editor.putString("ayiOrderUrl", bean.getAyiOrderUrl());
        editor.putString("accessTokenCookieName", bean.getAccessTokenCookieName());
        editor.putString("appAgentContent", bean.getAppAgentContent());
        editor.commit();
    }

    public static UrlConfigBean getUrlConfig(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("urlConfig", Activity.MODE_PRIVATE);
        UrlConfigBean houseBean = new UrlConfigBean();
        houseBean.setShopCartUrl(sharedPreferences.getString("shopCartUrl", ""));
        houseBean.setSpecUrl(sharedPreferences.getString("specUrl", ""));
        houseBean.setQaUrl(sharedPreferences.getString("qaUrl", ""));
        houseBean.setMovieUrl(sharedPreferences.getString("movieUrl", ""));
        houseBean.setAyiHomeUrl(sharedPreferences.getString("ayiHomeUrl", ""));
        houseBean.setTourUrl(sharedPreferences.getString("tourUrl", ""));
        houseBean.setShopOrderUrl(sharedPreferences.getString("shopOrderUrl", ""));
        houseBean.setTokenTypeCookieName(sharedPreferences.getString("tokenTypeCookieName", ""));
        houseBean.setAyiOrderUrl(sharedPreferences.getString("ayiOrderUrl", ""));
        houseBean.setAccessTokenCookieName(sharedPreferences.getString("accessTokenCookieName", ""));
        houseBean.setAppAgentContent(sharedPreferences.getString("appAgentContent", ""));
        return houseBean;
    }

    /**
     * 获取本地门禁token信息
     *
     * @param mContext
     * @return
     */
    public static ControlBean getControlInfo(Context mContext) {
        ControlBean info = new ControlBean();
        sharedPreferences = mContext.getSharedPreferences("tquid", Activity.MODE_PRIVATE);
        info.setTqUid(sharedPreferences.getString("tqUid", ""));
        return info;
    }

    /**
     * 保存支付中心房屋临时变量
     *
     * @param mContext
     * @param houseBean
     */
    public static void saveTempHouseBean(Context mContext, TempHouseBean houseBean) {
        sharedPreferences = mContext.getSharedPreferences("tempHouse", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AccessToken", houseBean.getAccessToken());
        editor.putString("HouseId", houseBean.getHouseId());
        editor.putString("HouseName", houseBean.getHouseName());
        editor.putString("SourceType", houseBean.getSourceType());
        editor.commit();
    }

    /**
     * 保存支付中心房屋临时变量
     *
     * @param mContext
     */
    public static TempHouseBean getTempHouseBean(Context mContext) {
        TempHouseBean houseBean = new TempHouseBean();
        sharedPreferences = mContext.getSharedPreferences("tempHouse", Activity.MODE_PRIVATE);
        houseBean.setAccessToken(sharedPreferences.getString("AccessToken", ""));
        houseBean.setHouseId(sharedPreferences.getString("HouseId", ""));
        houseBean.setHouseName(sharedPreferences.getString("HouseName", ""));
        houseBean.setSourceType(sharedPreferences.getString("SourceType", ""));
        return houseBean;
    }

    public static void clearTempHouseBean(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("tempHouse", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 保存访客视频门禁开关状态
     */
    public static void setVisitorVideoAccess(Context mContext, boolean flag) {
        sharedPreferences = mContext.getSharedPreferences("visitorVideoAccess", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SwitchState", String.valueOf(flag));
        editor.commit();
    }

    public static String getVisitorVideoAccess(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("visitorVideoAccess", Activity.MODE_PRIVATE);
        String flag = sharedPreferences.getString("SwitchState", "");
        return flag;
    }

    /**
     * 设置用户不是第一次登陆*(首页的引导层显示控制)
     */
    public static void setFirst(Context mContext, boolean flag) {
        sharedPreferences = mContext.getSharedPreferences("userFisrt", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IsFirst", String.valueOf(false));
        editor.commit();
    }

    public static String getFirst(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("userFisrt", Activity.MODE_PRIVATE);
        String flag = sharedPreferences.getString("IsFirst", "");
        return flag;
    }

    /**
     * 设置用户房屋列表
     */
    public static void setUseHouseList(Context mContext, List<HouseBean.HouseListBean> houseList) {
        sharedPreferences = mContext.getSharedPreferences("useHouseList", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("houseList", GsonUtil.GsonString(houseList));
        editor.commit();
    }

    public static List<HouseBean.HouseListBean> getUseHouseList(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("useHouseList", Activity.MODE_PRIVATE);
        String houseList = sharedPreferences.getString("houseList", "");
        return GsonUtil.GsonToList(houseList, HouseBean.HouseListBean.class);
    }

    /**
     * 设置用户无房屋时的小区Id  listBean.getName() + "," + listBean.getCommunityId());
     */
    public static void setCommunityId(Context mContext, String communityId) {
        sharedPreferences = mContext.getSharedPreferences("communityId", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("communityId", communityId);
        editor.commit();
    }

    public static String getCommunityId(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("communityId", Activity.MODE_PRIVATE);
        String flag = sharedPreferences.getString("communityId", "");
        return flag;
    }

    //清除小区Id
    public static void clearCommunityId(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("communityId", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 设置界面的红点（包括我的订单、缴费记录、报事记录、呼叫）
     * 管家-呼叫STWARD_CALL，管家-首页MENU_STWARD,我-APP下载推广码MY_APP_CODE,我-资料编辑MY_MOD, 首页—我MENU_MY,个人中心-报事记录 MY_POSTIT_LIST
     *
     * @param mContext
     * @param bean
     */
    public static void setRedCircle(Context mContext, RedCircleGroupBean bean) {
        sharedPreferences = mContext.getSharedPreferences("redCircleGroupBean", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (bean != null && bean.getList() != null)
            for (RedCircleGroupBean.ListBean listBean : bean.getList()) {
                switch (listBean.getPointCode()) {
                    case "STWARD_CALL"://管家-呼叫
                        bean.setHouseKeeperCalloCode(listBean.getPointCode());
                        bean.setHouseKeeperCall(String.valueOf(listBean.getStatus()));
                        break;
                    case "MENU_STWARD"://管家-首页
                        break;
                    case "MY_APP_CODE"://我-APP下载推广码
                        bean.setPersonSpreadCode(listBean.getPointCode());
                        bean.setPersonSpread(String.valueOf(listBean.getStatus()));
                        break;
                    case "MY_MOD"://我-资料编辑
                        break;
                    case "MENU_MY"://首页—我
                        break;
                    case "MY_POSTIT_LIST"://个人中心-报事记录
                        bean.setPersonBreakCode(listBean.getPointCode());
                        bean.setPersonBreak(String.valueOf(listBean.getStatus()));
                        break;
                }
            }
        //我
        editor.putString("person", StringUtils.equals(bean.getPersonOrder(), "1") ||
                StringUtils.equals(bean.getPersonBreak(), "1") ||
                StringUtils.equals(bean.getPersonSpread(), "1") ? "1" : "0");
        editor.putString("personOrder", bean.getPersonOrder());
        editor.putString("personBreak", bean.getPersonBreak());
        editor.putString("personSpread", bean.getPersonSpread());
        editor.putString("personInfoCode", bean.getPersonInfoCode());
        editor.putString("personOrderCode", bean.getPersonOrder());
        editor.putString("personBreakCode", bean.getPersonBreakCode());
        editor.putString("personSpreadCode", bean.getPersonSpreadCode());
        //管家
        editor.putString("houseKeeper", StringUtils.equals(bean.getHouseKeeperCall(), "1") ? "1" : "0");
        editor.putString("houseKeeperCall", bean.getHouseKeeperCall());
        editor.putString("houseKeeperCalloCode", bean.getHouseKeeperCalloCode());

        editor.commit();
    }

    public static RedCircleGroupBean getRedCircle(Context context) {
        sharedPreferences = context.getSharedPreferences("redCircleGroupBean", Activity.MODE_PRIVATE);
        RedCircleGroupBean redCircleGroupBean = new RedCircleGroupBean();
        redCircleGroupBean.setPerson(sharedPreferences.getString("person", ""));
        redCircleGroupBean.setPersonOrder(sharedPreferences.getString("personOrder", ""));
        redCircleGroupBean.setPersonBreak(sharedPreferences.getString("personBreak", ""));
        redCircleGroupBean.setPersonSpread(sharedPreferences.getString("personSpread", ""));
        redCircleGroupBean.setPersonInfoCode(sharedPreferences.getString("personInfoCode", ""));
        redCircleGroupBean.setPersonOrder(sharedPreferences.getString("personOrderCode", ""));
        redCircleGroupBean.setPersonBreakCode(sharedPreferences.getString("personBreakCode", ""));
        redCircleGroupBean.setPersonSpreadCode(sharedPreferences.getString("personSpreadCode", ""));
        redCircleGroupBean.setHouseKeeper(sharedPreferences.getString("houseKeeper", ""));
        redCircleGroupBean.setHouseKeeperCall(sharedPreferences.getString("houseKeeperCall", ""));
        redCircleGroupBean.setHouseKeeperCalloCode(sharedPreferences.getString("houseKeeperCalloCode", ""));
        return redCircleGroupBean;
    }

    public static void clearRedCirclePerson(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("redCircleGroupBean", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 设置用户不是第一次进入报事(报事爆料界面显示控制)
     */
    public static void setFirstReport(Context mContext, String flag) {
        sharedPreferences = mContext.getSharedPreferences("userFisrtReport", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IsFirstReport", String.valueOf(false));
        editor.commit();
    }

    public static String getFirstReport(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("userFisrtReport", Activity.MODE_PRIVATE);
        String flag = sharedPreferences.getString("IsFirstReport", "");
        return flag;
    }

    public static void clearFirstReport(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("userFisrtReport", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IsFirstReport", "");
        editor.commit();
    }

    /**
     * 门禁离线开门计数器(count需要在使用之后++，>9999置1)
     */
    public static int getDoorCounter(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("doorCounter", Activity.MODE_PRIVATE);
        int flag = sharedPreferences.getInt("Counter", 1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int counter = flag;
        flag++;
        if (flag > 9999)
            flag = 1;
        editor.putInt("Counter", flag);
        editor.commit();
        return counter;
    }
}

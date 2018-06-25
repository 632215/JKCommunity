package com.jinke.community.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.ui.widget.CustomRadioButton;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/3/13.
 */

public class RedCircleControlUtil {

    /**
     * 控制radiobutton的红点可见性
     *
     * @param context
     * @param buttons
     */
    public static void setRadioButtonVisiable(Context context, CustomRadioButton... buttons) {
        RedCircleGroupBean bean = SharedPreferencesUtils.getRedCircle(context);
        for (int x = 0; x < buttons.length; x++)
            if (buttons[x] != null)
                switch (x) {
                    case 0://管家
                        buttons[x].setVisiable(StringUtils.equals(bean.getHouseKeeper(), "1") ? true : false);
                        break;
                    case 1://生活
//                    buttons[x].setVisiable(StringUtils.equals(bean.getPerson(), "1") ? true : false);
                        break;
                    case 2://圈子
//                    buttons[x].setVisiable(StringUtils.equals(bean.getPerson(), "1") ? true : false);
                        break;
                    case 3://我
                        buttons[x].setVisiable(StringUtils.equals(bean.getPerson(), "1") ? true : false);
                        break;
                }
    }

    /**
     * 控制“我”界面的红点可见性
     *
     * @param context
     * @param images
     */
    public static void setPersonRedCirlce(Context context, ImageView... images) {
        RedCircleGroupBean bean = SharedPreferencesUtils.getRedCircle(context);
        for (int x = 0; x < images.length; x++) {
            if (images[x] != null)
                if (bean != null) {
                    switch (x) {
                        case 0:
                            images[x].setVisibility(StringUtils.equals(bean.getPersonOrder(), "1") ? View.VISIBLE : View.GONE);//我的订单红点
                            break;
                        case 1:
                            images[x].setVisibility(StringUtils.equals(bean.getPersonBreak(), "1") ? View.VISIBLE : View.GONE);//我的报事红点
                            break;
                        case 2:
                            images[x].setVisibility(StringUtils.equals(bean.getPersonSpread(), "1") ? View.VISIBLE : View.GONE);//推广红点
                            break;
                    }
                }
        }
    }

    /**
     * 控制“管家”界面的红点可见性
     *
     * @param context
     * @param images
     */
    public static void setHouseRedCirlce(Context context, ImageView... images) {
        RedCircleGroupBean bean = SharedPreferencesUtils.getRedCircle(context);
        for (int x = 0; x < images.length; x++) {
            if (images[x] != null)
                if (bean != null) {
                    switch (x) {
                        case 0:
                            images[x].setVisibility(StringUtils.equals(bean.getHouseKeeperCall(), "1") ? View.VISIBLE : View.GONE);//呼叫红点
                            break;
                    }
                }
        }
    }

    /**
     * 更新红点状态
     */
    public static void upDateCicle(Context context, String code) {
        if (StringUtils.isEmpty(code))
            return;
        RedCircleGroupBean bean = SharedPreferencesUtils.getRedCircle(context);
        switch (code) {
            case "STWARD_CALL"://管家-呼叫
                bean.setHouseKeeperCall("0");
                break;
            case "MENU_STWARD"://管家-首页
                break;
            case "MY_APP_CODE"://我-APP下载推广码
                bean.setPersonSpread("0");
                break;
            case "MY_MOD"://我-资料编辑
                break;
            case "MENU_MY"://首页—我
                break;
            case "MY_POSTIT_LIST"://个人中心-报事记录
                bean.setPersonBreak("0");
                break;
        }
        SharedPreferencesUtils.setRedCircle(context, bean);
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("pointCode", code);
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                LogUtils.e("更新成功");
            }

            @Override
            public void onError(String Code, String Msg) {
                LogUtils.e("更新失败");
            }
        };
        HttpMethods.getInstance().upDateCicle(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, MyApplication.getInstance()), map, MyApplication.creatSign(map));
    }
}
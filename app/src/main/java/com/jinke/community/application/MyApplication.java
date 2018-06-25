package com.jinke.community.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.adtech.sys.util.Encrypt;
import com.adtech.sys.util.MD5Util;
import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jinke.community.R;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.ControlBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.config.CrashReportConfig;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.lody.turbodex.TurboDex;
import com.thinmoo.utils.ChangeServerUtil;
import com.thinmoo.utils.ServerContainer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.constant.TencentConfig;

import static com.doormaster.vphone.inter.DMVPhoneModel.initDMVPhoneSDK;

/**
 * Created by root on 17-6-13.
 */

public class MyApplication extends MultiDexApplication {

    private static MyApplication application;
    ACache aCache;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化sdk
        initXiMo();
        application = this;
        //友盟集成
        MobclickAgent.setDebugMode(false);
        MobclickAgent.setCatchUncaughtExceptions(true);
        Fresco.initialize(this);
        UMShareAPI.get(this);
        //注册微信appId
        AppConfig.initPlatFormConfig();
        //注册腾讯应用分析
        TencentConfig.initTencent(this, "AZV1N1Z6SA3M", "官网");

        CrashReportConfig.checkUpdate(this, "b735beb94d", true);
        aCache = ACache.get(this);
        AnalyUtils.addAnaly(1000);
//        极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //埋点开关
        AnalyUtils.startCount = false;
        Utils.init(this);
    }

    private void initXiMo() {
        initDMVPhoneSDK(this, getString(R.string.app_name), true, false);
        ChangeServerUtil.getInstance().setAppServer(ServerContainer.CHONGQING_JINKE_APP_SERVER);
        ChangeServerUtil.getInstance().setVideoServer(ServerContainer.THINMOO_VIDEO_SERVER);
    }

    //默认房屋信息
    public HouseListBean.ListBean getDefaultHouse() {
        HouseListBean.ListBean defaultHouseBean = null;
        HouseListBean houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean != null && houseListBean.getList() != null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (bean.getDft_house() == 1) {
                    defaultHouseBean = bean;
                }
            }
        } else {
            defaultHouseBean = null;
        }
        return defaultHouseBean;
    }

    /**
     * 获取tqUid
     *
     * @return
     */
    public ControlBean getControlInfo() {
        ControlBean token = SharedPreferencesUtils.getControlInfo(getInstance());
        BaseUserBean userInfo = MyApplication.getBaseUserBean();
        //Tquid =大社区uid+大社区应用ID
        token.setTqUid(userInfo.getUid() + AppConfig.OPEN_APPID);
        return token;
    }

    public static MyApplication getInstance() {
        return application;
    }

    public static BaseUserBean getBaseUserBean() {
        return SharedPreferencesUtils.getBaseUserBean(application);
    }

    public UrlConfigBean getUrlConfigBean() {
        return SharedPreferencesUtils.getUrlConfig(application);
    }

    @Override
    protected void attachBaseContext(Context base) {
        TurboDex.enableTurboDex();
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String creatSign(Map<String, String> map) {
        SortedMap<Object, Object> sortedMap = new TreeMap<>();
        Set es = map.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry sign = (Map.Entry) it.next();
            Object k = sign.getKey();
            Object v = sign.getValue();
            sortedMap.put(k, v);
        }
        String key = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String tokenCommonlyUtils = Encrypt.md5(key.substring(14, 25));
        String sign = MD5Util.createSign(tokenCommonlyUtils, "UTF-8", sortedMap);
        return sign;
    }
}

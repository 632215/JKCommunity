package com.jinke.community.config;

import android.content.Context;

import com.jinke.community.R;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ThemeBean;
import com.jinke.community.http.main.HttpMethods;
import com.tencent.stat.StatService;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-7-21.
 */

public class AppConfig {
    //门禁开门计数器   >9999需置1
    public static int doorCount = 1;

    public final static int[] image = {R.mipmap.gif4, R.mipmap.gif5, R.mipmap.gif6};
    public final static String OPEN_APPID = "tqf2baaffb4fe940b7ba5876fd7929bec1";

    public final static String WEIXIN_APPID = "wx90868d2ade65417c";
    public final static String WEIXIN_SECRET = "b47bcc32a8b04a518053e958059fdfc6";
    public final static String aliPayWitholding = "alipays://platformapi/startapp?appId=20000067&url=";

    public final static String QQ_APPID = "101364347";
    public final static String QQ_SECRET = "42588ec004c22ff8c37c47134b7c22c1";

    //车辆管理系统
    public final static String SECRETKEY = "1208b73ec6d744bd8c3192b580b8779c";

    // 微信返回广播
    public static final String WX_RESP_ACTION_AUTH = "wx_resp_action_auth";
    public static final String WX_RESP_ACTION_SHARE_TEXT = "wx_resp_action_share_text";
    public static final String WX_RESP_ACTION_SHARE_IMAGE = "wx_resp_action_share_image";
    public static final String WX_RESP_ACTION_SHARE_WEBPAGE = "wx_resp_action_share_webpage";
    public static final String WX_RESP_ACTION_PAY = "wx_resp_action_pay";

    //固定H5的连接地址
    public static final String URL_PAYMENT_NOTES = "staticsources/protocol/payment.html";//缴费注意事项
    public static final String URL_WITHHOLDING_MANAGEMENT = "staticsources/protocol/payment.html";//代扣管理说明
    public static final String URL_QUESTION = "staticsources/protocol/housequestion.html";//绑定房屋遇到困难/常见问题
    public static final String URL_BREAK_EXPLAIN = "staticsources/protocol/brokeNewsState.html";//爆料服务声明
    public static final String URL_USER_PROTOL = "staticsources/protocol/registered.html";//用户协议

    //主题图片跟换地址
    public final static List<ThemeBean> themelist = new ArrayList<>();
    public static final String DEV_THEME_URL = "https://api-development.tq-service.com/v3/tqapp/jk_community/theme/getIcon/";//主题测试地址
    public static final String THEME_URL = "https://api.tq-service.com/v3/tqapp/jk_community/theme/getIcon/";//主题正式地址

    static {
        //1  APP基础切换按钮
        themelist.add(new ThemeBean("MAINHOUSEKEEPER.png", "menu_steward_2x.png"));//main管家  0
        themelist.add(new ThemeBean("MAINHOUSEKEEPER_PRESSED.png", "menu_steward_select_2x.png"));//main管家按压  1
        themelist.add(new ThemeBean("MAINHOUSELIFE.png", "menu_life_2x.png"));//main生活  2
        themelist.add(new ThemeBean("MAINHOUSELIFE_PRESSED.png", "menu_life_select_2x.png"));//main生活按压  3
        themelist.add(new ThemeBean("MAINHOUSECIRCLE.png", "menu_circle_2x.png"));//main圈子  4
        themelist.add(new ThemeBean("MAINHOUSECIRCLE_PRESSED.png", "menu_circle_select_2x.png"));//main圈子按压  5
        themelist.add(new ThemeBean("MAINHOUSEPERSON.png", "menu_my_2x.png"));//main我  6
        themelist.add(new ThemeBean("MAINHOUSEPERSON_PRESSED.png", "menu_my_select_2x.png"));//main我按压  7

        //2  管家界面
        themelist.add(new ThemeBean("MAINHOUSE_BG.png", "index_top_2x.png"));//main管家背景  8
        themelist.add(new ThemeBean("MAINHOUSE_PAYMENT.png", "index_payment_2x.png"));//main管家——费用  9
        themelist.add(new ThemeBean("MAINHOUSE_DOOR.png", "index_open_2x.png"));//main管家——门禁  10
        themelist.add(new ThemeBean("MAINHOUSE_CALL.png", "index_call_2x.png"));//main管家——呼叫  11
        themelist.add(new ThemeBean("MAINHOUSE_BREAK.png", "index_broke_2x.png"));//main管家——爆料  12

        //3  个人中心界面
        themelist.add(new ThemeBean("MAINPERSON_BG.png", "my_bg_2x.png"));//main我背景  13
        themelist.add(new ThemeBean("MAINPERSON_BALANCE.png", "my_pre_2x.png"));//main我预存  14
        themelist.add(new ThemeBean("MAINPERSON_INTEGRATE.png", "my_integral_2x.png"));//main我积分  15
        themelist.add(new ThemeBean("MAINPERSON_CARD.png", "my_card_2x.png"));//main我卡卷  16
    }

    //系统服务号
    public final static String SERVICEPHONE = "4008461818";

    //在线客服
    public final static String ONLINESERVICE = "http://www.tq-service.com/yunkefu/helper/visitor/join?accessToken=";

    //保存图片地址
    public final static String PICTURE_ADDRESS = "/storage/emulated/0/Android/data/com.jinke.community/cache/luban_disk_cache";
    public final static String PICTURE_FILE_ADDRESS = "/storage/emulated/0/Android/data/com.jinke.community/files";

    public static void initPlatFormConfig() {
        PlatformConfig.setWeixin(WEIXIN_APPID, WEIXIN_SECRET);
        PlatformConfig.setQQZone(QQ_APPID, QQ_SECRET);
    }

    public static void trackCustomEvent(Context mContext, String event_id, String msg) {
        //统计页面点击事件
        StatService.trackCustomEvent(mContext, event_id, msg);
    }
}

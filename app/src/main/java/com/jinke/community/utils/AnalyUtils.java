package com.jinke.community.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.adtech.sys.util.Encrypt;
import com.baidu.mobstat.StatService;
import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.AppUtils;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/3/28.
 */

public class AnalyUtils {
    //页面统计编码
    public final static Map<Integer, String> pageMap = new HashMap();
    public static boolean startCount = false;

    static {
        pageMap.put(1000, "OPEN_APP");//打开APP
        pageMap.put(1001, "GRENT_LOGIN");//授权登录
        pageMap.put(1002, "PHONE_REG");//手机号注册
        pageMap.put(1003, "HOUSE_BIND");//房屋绑定
        pageMap.put(1004, "STEWARD_PAY");//管家缴费
        pageMap.put(1005, "WAIT_PAY_QUERY");//代缴费查询
        pageMap.put(1006, "PRE_PAY");//预存
        pageMap.put(1007, "CAR_RENEWAL");//车位续期
        pageMap.put(1008, "START_PAY");//进入支付
        pageMap.put(1009, "PAY_SUCCESS");//支付成功
        pageMap.put(10010, "PAY_FAILURE");//支付失败
        pageMap.put(10011, "STEWARD_OPEN_DOOR");//管家开门
        pageMap.put(10012, "OPEN_KEYPAD_DOOR");//一键开门
        pageMap.put(10013, "GUEST_OPEN_DOOR");//访客通行
        pageMap.put(10014, "OPEN_DOOR_SUCCESS");//开门成功
        pageMap.put(10015, "CREATE_SECURITY");//生成密码
        pageMap.put(10016, "MESSAGE_SEND");//短信发送
        pageMap.put(10017, "WX_OR_QQ_SEND");//"微信/QQ发送
        //未写
        pageMap.put(10018, "STEWARD_BROKEN_NEWS");//管家爆料
        //
        pageMap.put(10019, "POST_IT_REPAIR");//报事保修
        pageMap.put(10020, "LITTE_GIRL_POST_IT");//小金妹爆料
        pageMap.put(10021, "POST_IT_LOG");//爆料记录
        pageMap.put(10022, "POST_IT_PLAZA");//爆料广场
        pageMap.put(10023, "STEWARD_PAGE_NOTIFY");//管家通知
        pageMap.put(10024, "NOTIFY_AND_NOTICE");//通知公告
        pageMap.put(10025, "SELECT_PROJECT");//选择小区
        pageMap.put(10026, "LIFE_AND_CIRCLE");//生活圈子
        //v2.0.5 new
        pageMap.put(10027, "QQ_GRENT_LOGIN");//"QQ授权登录"
        pageMap.put(10028, "WX_GRENT_LOGIN");//WX授权登录
        pageMap.put(10029, "SET_DEFAULT_HOUSE");//设置默认房屋
        pageMap.put(10030, "WAIT_PAY_START");//"代缴费-立即缴费"
        pageMap.put(10031, "PER_PAY_START");//预存-立即预存
        pageMap.put(10032, "CAR_PAY_START");//车位续费-立即充值
        pageMap.put(10033, "FAILURE_PAY_RESTART");//"支付失败-重新发起支付"
        pageMap.put(10034, "STEWARD_MORE");//管家-查看更多
        pageMap.put(10035, "STEWARD_BELOW_MORE");//管家-底部查看更多
        pageMap.put(10036, "LITTE_GIRL_I_NEED_POSTIT");//"小金妹爆料台-我要爆料
        pageMap.put(10037, "MY_POSTIT");//我的爆料-爆料台
        pageMap.put(10038, "POSTIT_SUBMIT");//报事提交
        pageMap.put(10039, "HOUSE_TIME");//日期
        pageMap.put(10040, "STEWARD_HOUSE");//管家-房屋
        pageMap.put(10041, "MY_HOUSE");//我-房屋
        pageMap.put(10042, "MY_HOUSE_ADD");//我的房屋-添加房屋
        pageMap.put(10043, "MY_HOUSE_GRANT");//我的房屋-授权
        pageMap.put(10044, "ADD_GRANT");//授权管理-添加授权
        pageMap.put(10045, "GRANT_SAVE");//被授权人-保存
        //v2.1 new
        pageMap.put(10046, "MENU_MY");//主菜单——我
        pageMap.put(10047, "MENU_STEWARD");//主菜单——管家
        pageMap.put(10048, "MENU_LIFE");//主菜单——生活
        pageMap.put(10049, "MENU_CIRCLE");//主菜单——圈子
        pageMap.put(10050, "QR_CODE");//二维码扫一扫
        pageMap.put(10051, "APP_D_CODE");//APP推广码
        pageMap.put(10052, "MY_CAR");//我的车辆
        pageMap.put(10053, "CHARM");//魅力家
        pageMap.put(10054, "WALKING_COMP");//健步大比拼
        pageMap.put(10055, "STEWARD_INDEX");//管家页-主页
    }

    /**
     * 数据统计埋点
     */
    public static void addAnaly(int operatype) {
        if (startCount) {
//        获取手机唯一信息
            Map<String, String> map = new HashMap();
            String androidID = Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
            String id = androidID + Build.SERIAL;
            if (MyApplication.getBaseUserBean() != null) {
                map.put("userId", String.valueOf(MyApplication.getBaseUserBean().getUid()));
            }
            if (MyApplication.getInstance().getDefaultHouse() != null) {
                map.put("house_id", MyApplication.getInstance().getDefaultHouse().getHouse_id());
            }
            map.put("pageName", String.valueOf(operatype));
            map.put("operatype", String.valueOf(pageMap.get(operatype)));
            map.put("appVersion", AppUtils.getAppVersionName(MyApplication.getInstance()));
            map.put("machineCode", Encrypt.md5(id));
            map.put("systemOS", "Android");
            getCommunityID(map);
            SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
                @Override
                public void onNext(EmptyObjectBean info) {
                    LogUtils.e("埋点成功");
                }

                @Override
                public void onError(String Code, String Msg) {
                    LogUtils.e("埋点失败");
                }
            };
            HttpMethods.getInstance().addAnaly(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, MyApplication.getInstance()), map, MyApplication.creatSign(map));
        }
    }

    /**
     * 数据统计埋点,在房屋信息有变动的界面
     */
    public static void addAnaly(int operatype, String house_id) {
        if (startCount) {
//        获取手机唯一信息
            Map map = new HashMap();
            String androidID = Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
            String id = androidID + Build.SERIAL;
            if (MyApplication.getBaseUserBean() != null) {
                map.put("userId", String.valueOf(MyApplication.getBaseUserBean().getUid()));
            }
            if (!StringUtils.isEmpty(house_id)) {
                map.put("house_id", house_id);
            }
            map.put("pageName", String.valueOf(operatype));
            map.put("operatype", String.valueOf(pageMap.get(operatype)));
            map.put("appVersion", AppUtils.getAppVersionName(MyApplication.getInstance()));
            map.put("machineCode", Encrypt.md5(id));
            map.put("systemOS", "Android");
            getCommunityID(map);
            SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
                @Override
                public void onNext(EmptyObjectBean info) {
                    LogUtils.e("埋点成功");
                }

                @Override
                public void onError(String Code, String Msg) {
                    LogUtils.e("埋点失败");
                }
            };
            HttpMethods.getInstance().addAnaly(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, MyApplication.getInstance()), map, MyApplication.creatSign(map));
        }
    }

    /**
     * 数据统计埋点(生活)
     */
    public static void addLifeAnaly(int operatype, String lifeId) {
        if (startCount) {
//        获取手机唯一信息
            Map<String, String> map = new HashMap();
            if (MyApplication.getBaseUserBean() != null) {
                map.put("userId", String.valueOf(MyApplication.getBaseUserBean().getUid()));
            }
            if (MyApplication.getInstance().getDefaultHouse() != null) {
                map.put("house_id", MyApplication.getInstance().getDefaultHouse().getHouse_id());
            }
            map.put("pageName", String.valueOf(operatype));
            map.put("operatype", String.valueOf(pageMap.get(operatype)));
            map.put("appVersion", AppUtils.getAppVersionName(MyApplication.getInstance()));
            map.put("machineCode", AndroidUtils.getUnId());
            map.put("systemOS", "Android");
            map.put("lifeId", lifeId);
            getCommunityID(map);
            SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
                @Override
                public void onNext(EmptyObjectBean info) {
                    LogUtils.e("埋点成功");
                }

                @Override
                public void onError(String Code, String Msg) {
                    LogUtils.e("埋点失败");
                }
            };
            HttpMethods.getInstance().addAnaly(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, MyApplication.getInstance()), map, MyApplication.creatSign(map));
        }
    }

    private static void getCommunityID(Map<String, String> map) {
        //先判断是都是无房屋绑定进入app的用户，再去判断当前房屋所在社区的ID。
        if (StringUtils.isEmpty(SharedPreferencesUtils.getCommunityId(MyApplication.getInstance()))) {
            ACache aCache = ACache.get(MyApplication.getInstance());
            if (aCache != null) {
                HouseListBean houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
                if (houseListBean != null && houseListBean.getList() != null)
                    for (HouseListBean.ListBean bean : houseListBean.getList()) {
                        if (bean.getDft_house() == 1 && !StringUtils.isEmpty(bean.getCommunity_id())) {
                            map.put("community_id", bean.getCommunity_id());
                        }
                    }
            }
        } else {
            String communityInfo = SharedPreferencesUtils.getCommunityId(MyApplication.getInstance());
            map.put("community_id", communityInfo.substring(communityInfo.indexOf(",") + 1));
        }
    }

    public static void setBAnalyResume(Context context, String name) {
        try {
            StatService.onResume((Activity) context);
            StatService.onPageStart(context, name);
        } catch (Exception e) {
        }
    }

    public static void setBAnalyPause(Context context, String name) {
        try {
            StatService.onPause((Activity) context);
            StatService.onPageEnd(context, name);
        } catch (Exception e) {
        }
    }
}

package com.jinke.community.jgPush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.JPushBean;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.impl.PropertyHistoryImpl;
import com.jinke.community.service.listener.IPropertyHistoryListener;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.ui.activity.house.MyHouseActivity;
import com.jinke.community.ui.activity.broken.NewPropertyDetailsActivity;
import com.jinke.community.ui.activity.broken.PropertyDetailsActivity;
import com.jinke.community.ui.activity.payment.PaymentDetailsActivity;
import com.jinke.community.ui.activity.web.WebActivity;
import com.lidroid.xutils.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * 一般消息的接收者
 * <p>
 * Created by Administrator on 2018/1/17.
 */

public class CommunityMsgReceiver extends BroadcastReceiver implements IPropertyHistoryListener {
    private static final String TAG = "JPush";
    private Context mContext;
    private JPushBean jPushBean;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Bundle bundle = intent.getExtras();
        //获取内容
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtils.d("[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtils.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.d("[MyReceiver] 用户点击打开了通知");
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (!StringUtils.isEmpty(json)) {
                JPushBean jPushBean = new Gson().fromJson(json, JPushBean.class);
                this.jPushBean = jPushBean;
                switch (jPushBean.getType()) {//type 1 APP内部界面跳转   type 2 URL跳转
                    case "1":
                        switch (jPushBean.getPage_next()) { //Page_next 0 报事详情  Page_next 1 我的房屋列表 Page_next 2 代扣详情
                            case "0":
                                //现有报事都为试点小区
                                if (MyApplication.getInstance().getDefaultHouse() != null)
                                    mContext.startActivity(new Intent(mContext, NewPropertyDetailsActivity.class).putExtra("keepId", jPushBean.getPostId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                else
                                    context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                                HouseListBean.ListBean listBean = MyApplication.getInstance().getDefaultHouse();
//                                if (listBean != null) {
//                                    Map map = new HashMap();
//                                    map.put("communityId", listBean.getCommunity_id());
//                                    new PropertyHistoryImpl(context).getConfig(map, this);
//                                }
                                break;
                            case "1":
                                context.startActivity(new Intent(context, MyHouseActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                break;
                            case "2":
                                PaymentRecordBean.ListBean bean = new PaymentRecordBean.ListBean();
                                if (StringUtils.isEmpty(jPushBean.getPay_id()) || StringUtils.isEmpty(jPushBean.getPayType()))
                                    return;
                                bean.setPay_id(jPushBean.getPay_id());
                                bean.setPay_name(jPushBean.getPayType());
                                context.startActivity(new Intent(context, PaymentDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("bean", bean));
                                break;
                        }
                        break;

                    case "2":
                        if (!StringUtils.isEmpty(jPushBean.getUrl())) {
                            intent = new Intent(context, WebActivity.class);
                            String url = "";
                            if (jPushBean.getUrl().toString().trim().startsWith("www") && !jPushBean.getUrl().toString().trim().contains("http"))
                                url = "http://" + jPushBean.getUrl().toString().trim();
                            else
                                url = jPushBean.getUrl().toString().trim();
                            if (!StringUtils.isEmpty(url)) {
                                intent.putExtra("url", url);
                                intent.putExtra("title", bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }
                        break;

                    default:
                        break;
                }
            } else {
                context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtils.d("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtils.w("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtils.d("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据

    private static String printBundle(Bundle bundle) {
        return String.valueOf(bundle);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
    }

    @Override
    public void onError(String code, String msg) {
    }

    @Override
    public void getKeepPostItListNext(PropertyBean info) {
    }

    @Override
    public void getConfigNext(CommunityBean info) {
        for (CommunityBean.ListBean bean : info.getList())
            if (StringUtils.equals("property_broken_test", bean.getAuthority_code())) {
                if (info != null && StringUtils.equals("1", bean.getStatus())) {
                    if (MyApplication.getInstance().getDefaultHouse() != null)
                        mContext.startActivity(new Intent(mContext, NewPropertyDetailsActivity.class).putExtra("keepId", jPushBean.getPostId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    if (MyApplication.getInstance().getDefaultHouse() != null)
                        mContext.startActivity(new Intent(mContext, PropertyDetailsActivity.class).putExtra("postId", jPushBean.getPostId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
    }

    @Override
    public void getConfigError() {
        if (MyApplication.getInstance().getDefaultHouse() != null)
            mContext.startActivity(new Intent(mContext, PropertyDetailsActivity.class).putExtra("postId", jPushBean.getPostId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}

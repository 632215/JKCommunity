package com.jinke.community.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ProgressBar;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.UrlConfigBean;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 29/12/16.
 */

public class UpProgressBar extends Handler {
    private Context mContext;
    ProgressBar bar;

    public UpProgressBar(Context mContext, @NonNull ProgressBar bar) {
        this.mContext = mContext.getApplicationContext();
        this.bar = bar;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        bar.setProgress((int) msg.obj);
    }

    public void updata(int number) {
        if (number >= 100) {
            bar.setVisibility(View.GONE);
        } else {
            Message message = new Message();
            message.obj = number;
            sendMessage(message);
        }
    }

    /**
     * Sync Cookie
     */
    public static void syncCookie(Context mContext, String url) {
        try {
            LogUtils.i(".jinke-service.com");
            CookieSyncManager.createInstance(mContext);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if (oldCookie != null) {
                LogUtils.i(oldCookie);
            }
            UrlConfigBean urlConfigBean = MyApplication.getInstance().getUrlConfigBean();

            if (urlConfigBean != null) {
                if (StringUtils.isEmpty(urlConfigBean.getAccessTokenCookieName())) {
                    ToastUtils.showLong(mContext, "url参数配置为空！");
                    return;
                }
                if (StringUtils.isEmpty(MyApplication.getBaseUserBean().getAccessToken())) {
                    ToastUtils.showLong(mContext, "用户登录唯一标识为空！");
                    return;
                }
                String cookic = urlConfigBean.getAccessTokenCookieName() + "=" + MyApplication.getBaseUserBean().getAccessToken();
                cookieManager.setCookie(url, cookic);
                if (MyApplication.getBaseUserBean().getPlatformName().equals("WEIXIN")) {
                    cookieManager.setCookie(url, urlConfigBean.getTokenTypeCookieName() + "=" + "wechat");
                } else {
                    if (StringUtils.isEmpty(MyApplication.getBaseUserBean().getPlatformName()))
                        cookieManager.setCookie(url, urlConfigBean.getTokenTypeCookieName() + "=QQ");
                    else
                        cookieManager.setCookie(url, urlConfigBean.getTokenTypeCookieName() + "=" + MyApplication.getBaseUserBean().getPlatformName());
                }
                if (Build.VERSION.SDK_INT < 21) {
                    CookieSyncManager.getInstance().sync();
                } else {
                    CookieManager.getInstance().flush();
                }
            }
            String newCookie = cookieManager.getCookie(url);
            LogUtils.i("newCookie-----" + newCookie);
            if (newCookie != null) {
                LogUtils.i(newCookie);
            }
        } catch (Exception e) {
            LogUtils.i(e.toString());
        }
    }
}


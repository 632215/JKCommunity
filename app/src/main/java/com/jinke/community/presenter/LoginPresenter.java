package com.jinke.community.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.ShareLoginBean;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.service.impl.ShareLoginImpl;
import com.jinke.community.service.listener.IRequestListener;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.LoginView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-22.
 */

public class LoginPresenter extends BasePresenter<LoginView> implements UMAuthListener, IRequestListener<UserLoginBean> {
    private Context mContext;
    private ShareLoginBean shareLoginBean;
    private ShareLoginImpl shareLogin;

    public LoginPresenter(Context mContext) {
        this.mContext = mContext;
        shareLogin = new ShareLoginImpl();
    }

    /**
     * QQ登录
     *
     * @param activity  当前activity
     * @param mShareAPI 分享实例
     * @param platform  分享的平台
     */
    public void shareLogin(final Activity activity, final UMShareAPI mShareAPI, final SHARE_MEDIA platform) {
        switch (platform) {
            case QQ:
                mShareAPI.getPlatformInfo(activity, platform, this);
                break;
            case WEIXIN:
                mShareAPI.doOauthVerify(activity, platform, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        login(platform, map);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        ToastUtils.showShort(mContext, "登录失败！");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                    }
                });
        }
    }

    @Override
    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
        login(platform, data);
    }

    private void login(SHARE_MEDIA platform, Map<String, String> data) {
        shareLoginBean = new ShareLoginBean();
        BaseUserBean userBean = MyApplication.getBaseUserBean();
        for (String key : data.keySet()) {
            LogUtils.e("xxxxxx key = " + key + "    value= " + data.get(key));
            if (platform.equals(SHARE_MEDIA.QQ)) {
                shareLoginBean.setUnionid(data.get("uid"));
                shareLoginBean.setGender(data.get("gender").equals("男") ? "1" : "2");
                shareLoginBean.setTokenType("qq");
            } else {
                shareLoginBean.setUnionid(data.get("unionid"));
                shareLoginBean.setGender(data.get("gender"));
                shareLoginBean.setTokenType("wechat");
            }
            shareLoginBean.setAccess_token(data.get("access_token"));
            shareLoginBean.setCity(data.get("city"));
            shareLoginBean.setCountry(data.get("country"));
            shareLoginBean.setExpires_in(data.get("expires_in"));

            shareLoginBean.setProfile_image_url(data.get("profile_image_url"));
            shareLoginBean.setProvince(data.get("province"));
            shareLoginBean.setRefresh_token(data.get("refresh_token"));
            shareLoginBean.setScreen_name(data.get("screen_name"));
            shareLoginBean.setOpenid(data.get("openid"));
            userBean.setOpenid(data.get("openid"));
            SharedPreferencesUtils.saveBaseUserBean(mContext, userBean);
        }
        AnalyUtils.addAnaly(StringUtils.equals("QQ", platform.toString().trim()) ? 10027 : 10028);
        Map<String, String> map = new HashMap<>();
        map.put("source", "App");
        if (StringUtils.isEmpty(shareLoginBean.getOpenid()) || StringUtils.isEmpty(shareLoginBean.getUnionid()) || StringUtils.isEmpty(shareLoginBean.getTokenType())) {
            ToastUtils.showShort(mContext, "获取授权信息失败！");
            return;
        }
        map.put("token", shareLoginBean.getOpenid());
        map.put("exToken", shareLoginBean.getUnionid());
        map.put("tokenType", shareLoginBean.getTokenType());
        shareLogin.getUserLogin(mContext, map, this);
        if (mView != null) {
            mView.showDialog();
        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        Toast.makeText(mContext, "get fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(SHARE_MEDIA platform, int action) {
    }

    @Override
    public void onSuccessListener(UserLoginBean bean) {
        if (mView != null) {
            mView.loginSuccess(bean, shareLoginBean);
        }
    }

    @Override
    public void onErrorListener(String Code, String Msg) {
        mView.showMsg(Msg);
    }
}

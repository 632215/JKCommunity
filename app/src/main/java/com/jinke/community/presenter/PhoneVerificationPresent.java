package com.jinke.community.presenter;

import android.support.v4.app.FragmentActivity;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.service.IVerificationPhone;
import com.jinke.community.service.impl.VerificationImpl;
import com.jinke.community.service.listener.IVerificationListener;
import com.jinke.community.view.PhoneVerificationView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-1.
 */

public class PhoneVerificationPresent extends BasePresenter<PhoneVerificationView> implements IVerificationListener {
    private FragmentActivity mContext;
    private IVerificationPhone verificationPhone;
    private UMShareAPI shareAPI;
    public PhoneVerificationPresent(FragmentActivity mContext) {
        this.mContext = mContext;
        shareAPI = UMShareAPI.get(mContext);
        verificationPhone = new VerificationImpl(mContext);
    }

    /**
     * 获取验证码
     *
     * @param map
     */
    public void getVerificationCode(Map<String, String> map) {
        if (mView != null) {
            verificationPhone.getVerificationPhone(map, this);
            mView.showLoading();
        }
    }

    @Override
    public void onCaptchaCode(UserLoginBean bean) {
        if (mView != null) {
            mView.hideLoading();
        }
    }


    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
            mView.hideLoading();
        }
    }

    public void loginBack(){
        BaseUserBean userBean= MyApplication.getBaseUserBean();
        shareAPI.deleteOauth(mContext, userBean.getPlatformName().equals("QQ") ? SHARE_MEDIA.QQ : SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (mView!=null){
                mView.finishActivity();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.showShort(mContext, "解除绑定失败！");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.showShort(mContext, "取消解除绑定！");
        }
    };

    /**
     * 注册
     * @param map
     */
    public void getRegisterData(Map<String, String> map) {
        if(verificationPhone!=null){
            mView.showLoading();
            verificationPhone.getRegisterData(map,this);
        }
    }

    @Override
    public void getRegisterDataNext(RegisterLoginBean info) {
       if (mView!=null){
           mView.hideLoading();
           mView.getRegisterDataNext(info);
       }
    }
}

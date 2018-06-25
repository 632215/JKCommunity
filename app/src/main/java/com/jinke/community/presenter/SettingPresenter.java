package com.jinke.community.presenter;

import android.app.Activity;
import android.content.Context;

import com.doormaster.vphone.inter.DMVPhoneModel;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.CustomerPhoneBean;
import com.jinke.community.service.SettingBiz;
import com.jinke.community.service.impl.SettingImpl;
import com.jinke.community.service.listener.SettingListener;
import com.jinke.community.ui.toast.CallPhoneDialog;
import com.jinke.community.utils.DbUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.SettingView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/11/20.
 */

public class SettingPresenter extends BasePresenter<SettingView> implements SettingListener, CallPhoneDialog.onCallPhoneListener {
    private Context mContext;
    private SettingBiz mBiz;
    private UMShareAPI shareAPI;
    private CallPhoneDialog logintOutDialog;

    public SettingPresenter(Activity mContext) {
        this.mContext = mContext;
        mBiz = new SettingImpl(mContext);
        shareAPI = UMShareAPI.get(mContext);
    }

    public void getLoginOut() {
        logintOutDialog = new CallPhoneDialog(mContext, this, "");
        logintOutDialog.show();
        logintOutDialog.setContent("请确定是否要退出当前登录！");
    }

    @Override
    public void onSure(String phone) {
        logintOutDialog.dismiss();
        DMVPhoneModel.exit();
        BaseUserBean userBean = MyApplication.getBaseUserBean();
        //解除ｑｑ，ｗｅｃｈａｔ登录绑定
        shareAPI.deleteOauth((Activity) mContext, userBean.getPlatformName().equals("QQ") ? SHARE_MEDIA.QQ : SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (mView != null) {
                mView.getLoginOutNext();
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
     * 获取电话
     *
     * @param map
     */
    public void getCustomerPhone(Map<String, String> map) {
        if (mView != null) {
            mBiz.getCustomerPhone(map, this);
            mView.showLoading();
        }
    }

    @Override
    public void getCustomerPhoneNext(CustomerPhoneBean bean) {
        if (mView != null) {
            mView.hideLoading();
            mView.getCustomerPhoneNext(bean);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.showErrorMsg(msg);
        }
    }
}

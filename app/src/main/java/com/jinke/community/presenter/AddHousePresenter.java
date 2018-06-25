package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.service.impl.AddHouseImpl;
import com.jinke.community.service.listener.IAddHouseListener;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.AddHouseView;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public class AddHousePresenter extends BasePresenter<AddHouseView> implements IAddHouseListener {

    private Context mContext;
    private AddHouseImpl addHouse;

    public AddHousePresenter(Context mContext) {
        this.mContext = mContext;
        addHouse = new AddHouseImpl(mContext);
    }

    /**
     * 绑定房屋
     *
     * @param map
     */
    public void getAddHouseData(Map<String, String> map) {
        if (mView != null) {
            mView.showDialog();
            addHouse.addHouse(map, this);
        }
    }

    /**
     * 获取验证码
     *
     * @param map
     */
    public void getVerificationCode(Map<String, String> map) {
        addHouse.getVerificationPhone(map, this);
        if (mView != null) {
            mView.showDialog();
        }
    }

    /**
     * 校验验证码
     *
     * @param map
     */
    public void getCheckCaptcha(Map<String, String> map) {
        if (mView != null) {
            addHouse.checkCaptcha(map, this);
            mView.showDialog();
        }
    }

    @Override
    public void onErrorMsg(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
            mView.hideDialog();
        }
    }

    @Override
    public void onCaptchaCode(UserLoginBean bean) {
        if (mView != null) {
            mView.captchaCode(bean.getCaptcha());
        }
    }

    @Override
    public void onSuccess(String msg) {
        if (mView != null) {
            AnalyUtils.addAnaly(1003);
            mView.showMsg(msg);
            mView.hideDialog();
            mView.finishActivity();
        }
    }

    @Override
    public void checkCaptcha(UserLoginBean bean) {
        if (mView != null) {
            mView.checkCaptchaCode("校验成功");
        }
    }
}

package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.RegisterBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.ShareLoginBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.PhoneVerificationPresent;
import com.jinke.community.ui.activity.house.BindHouseActivity;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.CountDownTimerUtils;
import com.jinke.community.utils.MyTextWatcher;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.PhoneVerificationView;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-1.
 */

public class PhoneVerificationActivity extends BaseActivity<PhoneVerificationView, PhoneVerificationPresent> implements PhoneVerificationView {
    @Bind(R.id.verification_phone_number)
    EditText phoneNumber;
    @Bind(R.id.verification_send)
    TextView sendCode;
    @Bind(R.id.verification_sms_code)
    EditText smsCode;
    @Bind(R.id.verification_next)
    TextView txNext;
    @Bind(R.id.image_check)
    ImageView imageCheck;
    ShareLoginBean bean = null;
    private String userAgreementState = "true";
    private RegisterBean registerBean;

    @Override
    public PhoneVerificationPresent initPresenter() {
        return new PhoneVerificationPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_phoneverification;
    }

    @Override
    protected void initView() {
        setTitle(R.string.verification_title);
        showBackwardView("", true);
        bean = (ShareLoginBean) getIntent().getSerializableExtra("shareBean");
        addTextWatch();
    }

    //给两个输入框添加监听，进行按钮的显示控制
    private void addTextWatch() {
        phoneNumber.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyTextWatcherListener() {
            @Override
            public void onTextChanged() {
                if (!StringUtils.isEmpty(phoneNumber.getText().toString().trim())
                        && phoneNumber.getText().toString().trim().length() == 11) {
                    sendCode.setBackgroundResource(R.mipmap.icon_verification_code);
                    sendCode.setEnabled(true);
                } else {
                    sendCode.setBackgroundResource(R.mipmap.icon_verification_code_wait);
                    sendCode.setEnabled(false);
                }
            }
        }));
        smsCode.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyTextWatcherListener() {
            @Override
            public void onTextChanged() {
                if (!StringUtils.isEmpty(phoneNumber.getText().toString().trim())
                        && phoneNumber.getText().toString().trim().length() == 11
                        && !StringUtils.isEmpty(smsCode.getText().toString().trim())
                        && smsCode.getText().toString().trim().length() == 6) {
                    txNext.setBackgroundResource(R.mipmap.icon_loadlayout_network_err_refresh);
                    txNext.setEnabled(true);
                } else {
                    txNext.setBackgroundResource(R.mipmap.icon_next_gray);
                    txNext.setEnabled(false);
                }
            }
        }));
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        presenter.loginBack();
    }

    Map<String, String> verification = new HashMap<>();

    @OnClick({R.id.verification_send, R.id.verification_next, R.id.tx_login_deal,
            R.id.image_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_login_deal:
                AppConfig.trackCustomEvent(this, "tx_login_deal_click", "注册－阅读用户协议");
//                startActivity(new Intent(this, UserAgreementActivity.class));
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_USER_PROTOL)
                        .putExtra("title", getString(R.string.activity_login_protocol))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

            case R.id.verification_send:
                AppConfig.trackCustomEvent(this, "verification_send_click", "注册－发送验证码");
                if (phoneNumber.getText().toString().trim().length() == 11) {
                    CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(PhoneVerificationActivity.this, sendCode, 60000, 100);
                    countDownTimerUtils.start();
                    verification.put("phone", phoneNumber.getText().toString().trim());
                    if (bean != null) {
                        verification.put("token", bean.getOpenid());
                    }
                    presenter.getVerificationCode(verification);
                } else {
                    ToastUtils.showShort(PhoneVerificationActivity.this, getResources().getString(R.string.verification_phone_error_msg));
                }
                break;

            case R.id.verification_next:
                if (!userAgreementState.equals("true")) {
                    ToastUtils.showShort(this, getString(R.string.activity_phone_verification_user_agreement));
                    return;
                }
                AppConfig.trackCustomEvent(this, "verification_next_click", "注册");
                if (phoneNumber.getText().toString().trim().length() != 11) {
                    ToastUtils.showShort(PhoneVerificationActivity.this, getResources().getString(R.string.verification_phone_error_msg));
                    return;
                }

                if (smsCode.getText().toString().trim().length() < 4) {
                    ToastUtils.showShort(PhoneVerificationActivity.this, "验证码输入不合法");
                    return;
                }
                registerBean = new RegisterBean();
                registerBean.setPhone(phoneNumber.getText().toString().trim());
                registerBean.setSmsCode(smsCode.getText().toString().trim());
                if (bean != null) {
                    registerBean.setAvatar(bean.getProfile_image_url());
                    registerBean.setNickName(bean.getScreen_name());
                    registerBean.setSex(bean.getGender());
                    registerBean.setTokenType(bean.getTokenType());
                    registerBean.setExToken(bean.getUnionid());
                    registerBean.setToken(bean.getOpenid());
                }
                registerBean.setSource("App");
                register();
                AnalyUtils.addAnaly(1002);
                break;

            case R.id.image_check:
                switch (userAgreementState) {
                    case "true":
                        imageCheck.setImageResource(R.mipmap.icon_register_un_selected);
                        userAgreementState = "false";
                        break;

                    case "false":
                        imageCheck.setImageResource(R.mipmap.icon_register_selected);
                        userAgreementState = "true";
                        break;
                }
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        if (registerBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put("phone", registerBean.getPhone());
            map.put("code", registerBean.getSmsCode());
            map.put("avatar", registerBean.getAvatar());
            map.put("nickName", StringUtils.isEmpty(registerBean.getNickName()) ? TextUtils.changTelNum(phoneNumber.getText().toString().trim() + "用户") :registerBean.getNickName());
            map.put("sex", registerBean.getSex());
            map.put("tokenType", registerBean.getTokenType());
            map.put("exToken", registerBean.getExToken());
            map.put("token", registerBean.getToken());
            map.put("source", "App");
            presenter.getRegisterData(map);
        }
    }

    @Override
    public void getRegisterDataNext(RegisterLoginBean info) {
        if (info != null && info.getIsLogin() != null) {
            JPushInterface.setAlias(this, 1, info.getAccessToken());//注册极光别名
            BaseUserBean userBean = MyApplication.getBaseUserBean();
            userBean.setAccessToken(info.getAccessToken());
            userBean.setPhone(registerBean.getPhone());
            if (StringUtils.equals(info.getIsLogin(), "false")) {
                startActivity(new Intent(this, BindHouseActivity.class).putExtra("RegisterLoginBean", info));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            SharedPreferencesUtils.saveBaseUserBean(this, userBean);
            finish();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog(null);
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(PhoneVerificationActivity.this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(1002);
        StatService.onResume(PhoneVerificationActivity.this);
        StatService.trackBeginPage(PhoneVerificationActivity.this, "验证手机号");
        AnalyUtils.setBAnalyResume(this, "验证手机号");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PhoneVerificationActivity.this);
        StatService.trackEndPage(PhoneVerificationActivity.this, "验证手机号");
        AnalyUtils.setBAnalyPause(this, "验证手机号");
    }
}

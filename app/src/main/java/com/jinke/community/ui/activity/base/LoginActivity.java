package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.ShareLoginBean;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.LoginPresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AppManager;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.LoginView;
import com.tencent.stat.StatService;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-7-22.
 */

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.cb_protocol)
    CheckBox cbProtocol;

    private UMShareAPI mShareAPI = null;

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    private String platform = "WEIXIN";

    @Override
    protected void initView() {
        mShareAPI = UMShareAPI.get(LoginActivity.this);
    }

    @OnClick({R.id.rl_qq, R.id.rl_wechat, R.id.img_authentication, R.id.tx_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_qq://QQ登录
                AnalyUtils.addAnaly(1001);
                AnalyUtils.addAnaly(10027);
                checkCheckProtocol(R.id.rl_qq);
                break;

            case R.id.rl_wechat://微信登录
                AnalyUtils.addAnaly(1001);
                AnalyUtils.addAnaly(10028);
                checkCheckProtocol(R.id.rl_wechat);
                break;

            case R.id.img_authentication://身份认证
                checkCheckProtocol(R.id.img_authentication);
                break;

            case R.id.tx_protocol://用户协议
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_USER_PROTOL)
                        .putExtra("title", getString(R.string.activity_login_protocol))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }

    /**
     * 检查用户协议是否勾选
     *
     * @param id
     */
    private void checkCheckProtocol(int id) {
        if (!cbProtocol.isChecked()) {
            ToastUtils.showLong(this, "请阅读用户协议");
            return;
        } else {
            switch (id) {
                case R.id.rl_qq:
                    AppConfig.trackCustomEvent(this, "login_qq_click", "QQ登录");
                    platform = "QQ";
                    presenter.shareLogin(LoginActivity.this, mShareAPI, SHARE_MEDIA.QQ);
                    break;
                case R.id.rl_wechat://微信登录
                    AppConfig.trackCustomEvent(this, "login_wechat_click", "WECHAT登录");
                    platform = "WEIXIN";
                    presenter.shareLogin(LoginActivity.this, mShareAPI, SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.img_authentication://身份认证
                    startActivity(new Intent(this, PhoneVerificationActivity.class));
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginSuccess(UserLoginBean bean, ShareLoginBean shareLoginBean) {
        hideDialog();
        Intent intent;
        BaseUserBean baseUserBean = MyApplication.getBaseUserBean();
        baseUserBean.setPlatformName(platform);
        baseUserBean.setHouse(bean.isHouse());
        baseUserBean.setAvatar(shareLoginBean.getProfile_image_url());
        if (StringUtils.isEmpty(bean.getAccessToken())) {
            intent = new Intent(this, PhoneVerificationActivity.class);
            intent.putExtra("shareBean", shareLoginBean);
        } else {
            intent = new Intent(this, MainActivity.class);
            SharedPreferencesUtils.clearCommunityId(this);
            baseUserBean.setShow(false);
            baseUserBean.setAccessToken(bean.getAccessToken());
            JPushInterface.setAlias(this, 1, bean.getAccessToken());//注册极光别名
        }
        SharedPreferencesUtils.saveBaseUserBean(LoginActivity.this, baseUserBean);
        startActivity(intent);
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(LoginActivity.this, msg);
        hideDialog();
    }

    @Override
    public void showDialog() {
        showProgressDialog(null);
    }

    @Override
    public void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cbProtocol.setOnCheckedChangeListener(this);
        StatService.onResume(LoginActivity.this);
        StatService.trackBeginPage(LoginActivity.this, "用户登录");
        AnalyUtils.setBAnalyResume(this, "用户登录");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(LoginActivity.this);
        StatService.trackEndPage(LoginActivity.this, "用户登录");
        AnalyUtils.setBAnalyPause(this, "用户登录");
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort(this, getString(R.string.Press_again_to_exit_the_program));
                mExitTime = System.currentTimeMillis();
            } else {
                AppManager.AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        DrawableUtils.setCheckBoxDrawableLeft(this, cbProtocol, b == true ? R.mipmap.icon_activity_break_new_selected : R.mipmap.icon_activity_break_new_un_select);
    }
}

package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.AddHousePresenter;
import com.jinke.community.ui.activity.base.SelectCommunityActivity;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.CountDownTimerUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.view.AddHouseView;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * 添加房屋
 * Created by root on 17-8-1.
 */

public class AddHouseActivity extends BaseActivity<AddHouseView, AddHousePresenter> implements AddHouseView {
    @Bind(R.id.ed_input_name)
    EditText edInputName;
    @Bind(R.id.ed_input_phone)
    EditText edInputPhone;
    @Bind(R.id.ed_input_verification_code)
    EditText VerificationCode;
    @Bind(R.id.tx_getVerification)
    TextView GetVerification;

    @Override
    public AddHousePresenter initPresenter() {
        return new AddHousePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_house;
    }

    @Override
    protected void initView() {
        setTitle("添加房屋");
        showBackwardView(R.string.empty, true);
        addTextWatcher();
        //设置授权用户姓名输入限制
        edInputName.addTextChangedListener(new EmTextWatch(edInputName, this));
    }

    private void addTextWatcher() {
        edInputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtils.isEmpty(edInputName.getText().toString().trim())
                        && !StringUtils.isEmpty(editable.toString().trim())
                        && editable.toString().trim().length() == 11) {
                    GetVerification.setBackgroundResource(R.mipmap.icon_verification_code);
                    GetVerification.setEnabled(true);
                } else {
                    GetVerification.setBackgroundResource(R.mipmap.icon_verification_code_wait);
                    GetVerification.setEnabled(false);
                }
            }
        });
        edInputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtils.isEmpty(edInputName.getText().toString().trim())
                        && !StringUtils.isEmpty(edInputPhone.getText().toString().trim())
                        && edInputPhone.getText().toString().trim().length() == 11) {
                    GetVerification.setBackgroundResource(R.mipmap.icon_verification_code);
                    GetVerification.setEnabled(true);
                } else {
                    GetVerification.setBackgroundResource(R.mipmap.icon_verification_code_wait);
                    GetVerification.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.tx_getVerification, R.id.image_sure, R.id.tx_banding_house})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_getVerification:
                checkBaseInput();
                CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(AddHouseActivity.this, GetVerification, 60000, 100);
                countDownTimerUtils.start();
                Map<String, String> map = new HashMap<>();
                map.put("token", MyApplication.getBaseUserBean().getOpenid());
                map.put("phone", edInputPhone.getText().toString().trim());
                presenter.getVerificationCode(map);
                break;

            case R.id.tx_banding_house:  //遇到问题
                Intent payment = new Intent(this, WebActivity.class);
                payment.putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_QUESTION);
                payment.putExtra("title", getString(R.string.add_house_url));
                startActivity(payment);
                break;

            case R.id.image_sure:
                checkBaseInput();
                if (StringUtils.isEmpty(VerificationCode.getText().toString().trim())) {
                    ToastUtils.showShort(AddHouseActivity.this, getString(R.string.add_house_verification_empty));
                    return;
                }
                Map<String, String> verificationMap = new HashMap<>();
                verificationMap.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                verificationMap.put("ownerPhone", edInputPhone.getText().toString().trim());
                verificationMap.put("code", VerificationCode.getText().toString().trim());
                presenter.getAddHouseData(verificationMap);
                break;
        }
    }

    //检查输入的数据
    private void checkBaseInput() {
        if (StringUtils.isEmpty(edInputName.getText().toString().trim())) {
            ToastUtils.showShort(AddHouseActivity.this, getString(R.string.add_house_name_empty));
            return;
        }
        if (StringUtils.isEmpty(edInputPhone.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入电话号码！");
            return;
        }
        if (StringUtils.isEmpty(edInputPhone.getText().toString().trim())) {
            ToastUtils.showShort(AddHouseActivity.this, getString(R.string.add_house_phone_empty));
            return;
        }
    }

    /**
     * 2.0.3版本废弃（验证码校验回调）
     *
     * @param msg
     */
    @Override
    public void checkCaptchaCode(String msg) {
        showMsg(msg);
        Map<String, String> addHouseMap = new HashMap<>();
        addHouseMap.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        addHouseMap.put("ownerPhone", edInputPhone.getText().toString().trim());
        addHouseMap.put("ownerName", edInputName.getText().toString().trim());
        presenter.getAddHouseData(addHouseMap);
    }

    @Override
    public void showDialog() {
        showProgressDialog("加载中...");
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void captchaCode(String code) {
        hideDialog();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void finishActivity() {
        if (SelectCommunityActivity.selectCommunityInstance != null) {
            SelectCommunityActivity.selectCommunityInstance.finish();
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(AddHouseActivity.this);
        StatService.trackBeginPage(AddHouseActivity.this, "添加房屋");
        AnalyUtils.setBAnalyResume(this, "添加房屋");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AddHouseActivity.this);
        StatService.trackEndPage(AddHouseActivity.this, "添加房屋");
        AnalyUtils.setBAnalyPause(this, "添加房屋");
    }
}

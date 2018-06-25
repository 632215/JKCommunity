package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.base.util.ToastUtil;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.StateBean;
import com.jinke.community.presenter.AuthenticationTipsPresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.view.AuthenticationTipsView;
import com.tencent.stat.StatService;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * 身份验证提示
 * Created by Administrator on 2018/5/29.
 */

public class AuthenticationTipsActivity extends BaseActivity<AuthenticationTipsView, AuthenticationTipsPresenter> implements AuthenticationTipsView {
    @Bind(R.id.tx_question)
    TextView txQuestion;
    private String houseId = null;
    private String houseName = null;
    private String houseState = "";//房屋审核状态 0审核失败，1审核成功，2审核中，3没审核(可以上传信息)
    private StateBean info = null;

    @Override
    public AuthenticationTipsPresenter initPresenter() {
        return new AuthenticationTipsPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tips_authentication;
    }

    @Override
    protected void initView() {
        showBackwardView(R.string.empty, true);
        setTitle(R.string.activity_authentication_title);
        houseId = getIntent().getStringExtra("houseId");
        houseName = getIntent().getStringExtra("houseName");
        presenter.getState(houseId);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tx_book, R.id.tx_authenticaltion, R.id.tx_area, R.id.tx_fee, R.id.tx_manager, R.id.tx_key, R.id.tx_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_book:
                openWeb("业主手册");
                break;

            case R.id.tx_authenticaltion:
                if (info != null) {
                    houseState = info.getStatus();
                    if (!StringUtils.isEmpty(houseState)
                            && (StringUtils.equals("Y", houseState)
                            || StringUtils.equals("N", houseState)
                            || StringUtils.equals("D", houseState))) {
                        startActivity(new Intent(this, CheckActivity.class)
                                .putExtra("houseState", houseState)
                                .putExtra("houseId", houseId)
                                .putExtra("houseName", houseName)
                                .putExtra("remark", info.getRemark()));
                    } else {
                        startActivity(new Intent(this, AuthenticationActivity.class)
                                .putExtra("houseId", houseId)
                                .putExtra("houseName", houseName)
                                .putExtra("houseState", houseState));
                    }
                }
                break;

            case R.id.tx_area:
                break;

            case R.id.tx_fee:
                break;

            case R.id.tx_manager:
                break;

            case R.id.tx_key:
                break;

            case R.id.tx_question:
                openWeb("问卷调查");
                break;
        }
    }

    private void openWeb(String title) {
        String url = null;
        if (info != null) {
            switch (title) {
                case "问卷调查":
                    url = info.getCertifiedUrl();
                    if (!StringUtils.isEmpty(url))
                        url = url + "&accessToken=" + MyApplication.getBaseUserBean().getAccessToken();
                    break;
                case "业主手册":
                    url = info.getManualUrl();
                    break;
            }
        }
        if (!StringUtils.isEmpty(url)) {
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("url", url)
                    .putExtra("title", title)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void getStateNext(StateBean info) {
        this.info = info;
        if (info != null) {
            DrawableUtils.setDrawableTop(this, txQuestion, StringUtils.isEmpty(info.getCertifiedUrl()) ? R.mipmap.icon_authentication_question_gray :
                    R.mipmap.icon_authentication_question);
            txQuestion.setTextColor(getResources().getColor(R.color.color_text_second_title));
            txQuestion.setClickable(StringUtils.isEmpty(info.getCertifiedUrl()) ? false : true);
        }
    }

    @Override
    public void getStateError(String msg) {
        ToastUtils.showShort(this, msg);
    }

    @Override
    public void showLoading() {
        showProgressDialog("");
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(AuthenticationTipsActivity.this);
        StatService.trackBeginPage(AuthenticationTipsActivity.this, "身份验证提示");
        AnalyUtils.setBAnalyResume(this, "身份验证提示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AuthenticationTipsActivity.this);
        StatService.trackEndPage(AuthenticationTipsActivity.this, "身份验证提示");
        AnalyUtils.setBAnalyPause(this, "身份验证提示");
    }
}

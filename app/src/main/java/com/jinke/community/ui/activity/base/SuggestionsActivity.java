package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.EditText;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.SuggestionsPresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.view.ISuggestionsView;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-4.
 */

public class SuggestionsActivity extends BaseActivity<ISuggestionsView, SuggestionsPresenter> implements ISuggestionsView {
    @Bind(R.id.ed_suggest)
    EditText suggestContent;

    @Override
    public SuggestionsPresenter initPresenter() {
        return new SuggestionsPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_suggestions;
    }

    @Override
    protected void initView() {
        setTitle(R.string.feed_back_title);
        showBackwardView("", true);
        showForwardView("提交", true);
        suggestContent.addTextChangedListener(new EmTextWatch(suggestContent, this));
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        AppConfig.trackCustomEvent(this, "Suggestions_click", "添加意见反馈");
        if (StringUtils.isEmpty(suggestContent.getText().toString().trim())) {
            ToastUtils.showShort(this, "请填写您的意见反馈，我们会尽快处理");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("content", suggestContent.getText().toString().trim());
        presenter.getAddSuggest(map);
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void onSuccess(EmptyObjectBean bean) {
        ToastUtils.showShort(this, "提交成功");
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog(null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(SuggestionsActivity.this);
        StatService.trackBeginPage(SuggestionsActivity.this, "意见反馈");
        AnalyUtils.setBAnalyResume(this, "意见反馈");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(SuggestionsActivity.this);
        StatService.trackEndPage(SuggestionsActivity.this, "意见反馈");
        AnalyUtils.setBAnalyPause(this, "意见反馈");
    }

    @Override
    public void hindLoading() {
        hideDialog();
    }
}

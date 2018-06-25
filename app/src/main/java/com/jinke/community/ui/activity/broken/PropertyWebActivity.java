package com.jinke.community.ui.activity.broken;

import android.view.View;
import android.webkit.WebView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.PostItNoticeDetailBean;
import com.jinke.community.presenter.PropertyWebPresenter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IPropertyWebView;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-15.
 * 公告详情
 */

public class PropertyWebActivity extends BaseActivity<IPropertyWebView, PropertyWebPresenter> implements IPropertyWebView {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    @Override
    public PropertyWebPresenter initPresenter() {
        return new PropertyWebPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_property_web;
    }

    @Override
    protected void initView() {
        showBackwardView("", true);
        String noticeId = getIntent().getStringExtra("noticeId");
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("noticeId", noticeId);
        presenter.getPostItNoticeDetail(map);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }


    @Override
    public void onSuccess(PostItNoticeDetailBean Bean) {
        setTitle(Bean.getTitle());
        loadingLayout.setStatus(LoadingLayout.Success);
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        webView.loadDataWithBaseURL(null, Bean.getContentHtml(), "text/html", "utf-8", null);
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PropertyWebActivity.this);
        StatService.trackBeginPage(PropertyWebActivity.this, "公告详情");
        AnalyUtils.setBAnalyResume(this, "公告详情");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PropertyWebActivity.this);
        StatService.trackEndPage(PropertyWebActivity.this, "公告详情");
        AnalyUtils.setBAnalyPause(this, "公告详情");
    }
}

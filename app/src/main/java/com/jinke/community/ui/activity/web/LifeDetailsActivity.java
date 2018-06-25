package com.jinke.community.ui.activity.web;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.presenter.IWebPresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.UpProgressBar;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.stat.StatService;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-16.
 */

public class LifeDetailsActivity extends BaseActivity {
    private UpProgressBar upProgressBar;
    private String url = "http://wx-dev.jinke-service.com/appbridge/";
    @Bind(R.id.myProgressBar)
    ProgressBar myProgressBar;
    @Bind(R.id.bridge_WebView)
    BridgeWebView mWebView;
    private IWXAPI iwxapi;

    @Override
    public IWebPresenter initPresenter() {
        return new IWebPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        setTitle(getIntent().getStringExtra("title"));

        showBackwardView("", true);
        upProgressBar = new UpProgressBar(LifeDetailsActivity.this, myProgressBar);
        mWebView.getSettings().setUserAgentString("navigator.userAgentJinkeCommunityApp");
        mWebView.getSettings().setJavaScriptEnabled(true);
        UpProgressBar.syncCookie(LifeDetailsActivity.this, ".tq-service.com");
        mWebView.getSettings().setBlockNetworkImage(false);
        initWebView();
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
//        if (mWebView != null) {//点击返回按钮
//            if (mWebView.canGoBack()) {
//                mWebView.goBack();
//            } else {
//                finish();
//            }
//        }
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                } else {
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initWebView() {
        mWebView.setDefaultHandler(new MyWebViewCallBack());
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                upProgressBar.updata(newProgress);
            }
        });
        mWebView.loadUrl(url);

        mWebView.registerHandler("tel", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ToastUtils.showShort(LifeDetailsActivity.this,"调起了");
            }
        });
    }

    class MyWebViewCallBack extends DefaultHandler{
        @Override
        public void handler(String data, CallBackFunction function) {
            super.handler(data, function);
            ToastUtils.showShort(LifeDetailsActivity.this,"调起了");
        }
    }

    public class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
    }

    @Override
    protected void showBackwardView(int backwardResid, boolean show) {
        super.showBackwardView(backwardResid, show);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(LifeDetailsActivity.this);
        StatService.trackBeginPage(LifeDetailsActivity.this, "Web微信展示");
        AnalyUtils.setBAnalyResume(this, "Web微信展示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(LifeDetailsActivity.this);
        StatService.trackEndPage(LifeDetailsActivity.this, "Web微信展示");
        AnalyUtils.setBAnalyPause(this, "Web微信展示");
    }
}

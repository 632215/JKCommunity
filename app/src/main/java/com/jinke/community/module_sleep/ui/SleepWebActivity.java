package com.jinke.community.module_sleep.ui;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.LocationBean;
import com.jinke.community.ui.activity.base.ScanActivity;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.HadlerCallBack;
import com.jinke.community.utils.LocationUtils;
import com.jinke.community.utils.UpProgressBar;
import com.tencent.stat.StatService;

import butterknife.Bind;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-8.
 */

public class SleepWebActivity extends BaseActivity implements LocationUtils.onLocationInfoListener {
    private UpProgressBar upProgressBar;
    private String url = "https://api.cqjiaonan.com/web.php?s=/Demo/demo";
    @Bind(R.id.myProgressBar)
    ProgressBar myProgressBar;
    @Bind(R.id.bridge_WebView)
    BridgeWebView mWebView;

    private int Rcode = 1000;
    private CallBackFunction jsScanFunction;//js扫码回调函数
    private CallBackFunction jsLocationFunction;//js位置信息回调函数
    private BaseUserBean user;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        user = MyApplication.getBaseUserBean();
        url = getIntent().getStringExtra("url");
        setTitle(getIntent().getStringExtra("title"));
        showBackwardView("", true);
        upProgressBar = new UpProgressBar(SleepWebActivity.this, myProgressBar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        initWebView();
    }

    private void initWebView() {
        mWebView.setDefaultHandler(new HadlerCallBack(SleepWebActivity.this));
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                upProgressBar.updata(newProgress);
            }
        });
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl(url);
        //前端调用js注册好的让前端去实现的scan函数
        mWebView.registerHandler("scan", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ToastUtils.showShort(SleepWebActivity.this, data);
//                function.onCallBack("sssssssssss");
                jsScanFunction = function;
                startActivityForResult(new Intent(SleepWebActivity.this, ScanActivity.class)
                        .putExtra("isReturn", "1"), Rcode);
            }
        });

        //前端调用js注册好的让前端去实现的getLocation函数
        mWebView.registerHandler("getLocation", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                jsLocationFunction = function;
                PermissionGen.with(SleepWebActivity.this)
                        .addRequestCode(105)
                        .permissions(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                        .request();
            }
        });

        //前端调用js注册好的Js写好的函数

        mWebView.callHandler("deliverUserInfo", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                ToastUtils.showShort(SleepWebActivity.this, "js收到用户信息");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Rcode) {
            jsScanFunction.onCallBack("11245");
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        if (mWebView != null) {//点击返回按钮
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
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

    @PermissionSuccess(requestCode = 105)
    public void loactionSuccess() {
        LocationUtils locationUtils = new LocationUtils(this);
        locationUtils.setOnLocationInfoListener(this);
        LogUtils.i("权限获取成功，正在定位!");
    }

    @PermissionFail(requestCode = 105)
    public void loactionFail() {
        ToastUtils.showShort(this, "权限获取失败，请前往应用权限管理中，进行授权!");
    }

    /**
     * 位置信息成功 回调
     *
     * @param locationBean
     */
    @Override
    public void locationBackInfo(LocationBean locationBean) {
        jsLocationFunction.onCallBack(new Gson().toJson(locationBean));
    }

    /**
     * 位置信息失败 回调
     */
    @Override
    public void locationBackFailed(int errorCode) {
        LocationBean locationBean = new LocationBean();
        locationBean.setErrorCode("1000");
        jsLocationFunction.onCallBack(new Gson().toJson(locationBean));
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(SleepWebActivity.this);
        StatService.trackBeginPage(SleepWebActivity.this, "Web微信展示");
        AnalyUtils.setBAnalyResume(this, "Web微信展示");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(SleepWebActivity.this);
        StatService.trackEndPage(SleepWebActivity.this, "Web微信展示");
        AnalyUtils.setBAnalyPause(this, "Web微信展示");
    }
}

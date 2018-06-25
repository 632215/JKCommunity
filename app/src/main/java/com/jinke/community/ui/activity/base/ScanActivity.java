package com.jinke.community.ui.activity.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.view.View;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.module_sleep.ui.SleepWebActivity;
import com.jinke.community.utils.CameraUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import rx.functions.Action1;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ScanActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener {
    @Bind(R.id.qr_view)
    QRCodeReaderView qrView;
    private boolean flag = false;
    private String isReturn = "0";//判断是否返回数据，在睡眠仓模块使用

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initView() {
        setTitle("扫一扫");
        showBackwardView("", true);
        if (!StringUtils.isEmpty(getIntent().getStringExtra("isReturn")))
            isReturn = getIntent().getStringExtra("isReturn");
    }

    private void initQrView() {
        qrView.setOnQRCodeReadListener(this);
        qrView.setQRDecodingEnabled(true);
        qrView.setAutofocusInterval(2000L);
        qrView.setTorchEnabled(true);
        qrView.setFrontCamera();
        qrView.setBackCamera();
        qrView.startCamera();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (flag == false) {
            try {
                flag = true;
                if (StringUtils.equals("1", isReturn)) {
                    Intent intent = new Intent(this, SleepWebActivity.class).putExtra("Rcode", text);
                    setResult(1000, intent);
                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(text);
                    intent.setData(content_url);
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(intent);
                }
                finish();
            } catch (Exception e) {
                ToastUtils.showShort(this, "启用浏览器错误！");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPer();
    }

    //检查权限
    @SuppressLint("WrongConstant")
    private void checkPer() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean && CameraUtil.checkCameraPermission())
                            initQrView();
                        else {
                            ToastUtils.showLong(ScanActivity.this, "暂无相机使用权限，请前往设置开启权限！");
                            finish();
                        }
                    }
                });
    }
}

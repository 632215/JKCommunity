package www.jinke.com.charmhome.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.PointF;
import android.hardware.Camera;
import android.view.View;

import com.base.util.ToastUtil;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;
import www.jinke.com.charmhome.Base.BaseActivity;
import www.jinke.com.charmhome.R;
import www.jinke.com.charmhome.presenter.lock.ScanningLockCodePresenter;

/**
 * Created by root on 17-12-14.
 */

public class ScannerCodeActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView qrCodeReaderView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scanner_code;
    }

    @Override
    protected void initView() {
        setTitleText("扫描二维码");
        qrCodeReaderView.setOnQRCodeReadListener(this);
        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);
        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);
        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);
        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();
        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    private void checkPermission() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean && checkCameraPermission())
                            qrCodeReaderView.startCamera();
                        else {
                            ToastUtil.showToastLong("暂无相机使用权限，请前往设置开启权限！");
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void findViewById() {
        qrCodeReaderView = findViewById(R.id.qrdecoderview);
    }

    @Override
    protected void onBackView(View view) {
        super.onBackView(view);
        finish();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        LogUtils.i("+text:" + text);
        Intent mIntent = new Intent();
        mIntent.putExtra("result", text);
        // 设置结果，并进行传送
        setResult(ScanningLockCodePresenter.REQUEST_CODE, mIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    //判断相机是否可以使用
    public boolean checkCameraPermission() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
}

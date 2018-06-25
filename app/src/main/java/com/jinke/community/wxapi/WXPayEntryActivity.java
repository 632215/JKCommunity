package com.jinke.community.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jinke.community.R;
import com.jinke.community.bean.PayBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.ui.activity.payment.PaymentResultActivity;
import com.lidroid.xutils.util.LogUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_pay);
        api = WXAPIFactory.createWXAPI(this, AppConfig.WEIXIN_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(com.tencent.mm.opensdk.modelbase.BaseReq baseReq) {
        LogUtils.i("32s:" + baseReq.transaction);
    }

    @Override
    public void onResp(com.tencent.mm.opensdk.modelbase.BaseResp baseResp) {
        LogUtils.i("32s:" + "errStr:" + baseResp.errStr + "_____errCode:" + baseResp.errCode);
        PayBean bean = new PayBean();
        switch (baseResp.errCode) {
            case 0://成功	展示成功页面
                bean.setState("1");
                break;

            case -1://错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                bean.setState("0");
                break;

            case -2://用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
                bean.setState("0");
                break;
        }
        Intent intent = new Intent(this, PaymentResultActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
        finish();
    }
}

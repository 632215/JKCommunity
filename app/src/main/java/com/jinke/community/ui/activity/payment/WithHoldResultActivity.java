package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.config.AppConfig;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.utils.AppManager;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/5.
 */

public class WithHoldResultActivity extends BaseActivity {
    @Bind(R.id.image_result_image)
    ImageView imageResult;
    @Bind(R.id.tx_result_tips)
    TextView txResultTips;
    @Bind(R.id.tx_payment_forward)
    TextView txPaymentForward;
    @Bind(R.id.tx_payment_back)
    TextView txPaymentBack;
    @Bind(R.id.tip_text)
    TextView tipText;
    @Bind(R.id.phone_text)
    TextView phoneText;

    private String dataString;
    private boolean result = false;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withhold_result;
    }

    @Override
    protected void initView() {
        setTitle("签约");
        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri uri = intent.getData();
        if (scheme != null) {
            Log.e("32s", scheme);
        }
        if (uri != null) {
            dataString = intent.getDataString();
            if (!StringUtils.isEmpty(dataString) && dataString.contains("is_success=T")) {
                imageResult.setImageResource(R.mipmap.icon_withhold_open_success);
                txResultTips.setText("签约成功");
                txPaymentForward.setText("代扣管理");
                txPaymentBack.setText("返回管家");
                result = true;
            } else {
                imageResult.setImageResource(R.mipmap.icon_withhold_open_failed);
                txResultTips.setText("签约失败");
                txPaymentForward.setText("重新开通");
                txPaymentBack.setText("返回管家");
                result = false;
            }
        }
    }

    @OnClick({R.id.tx_payment_back, R.id.tx_payment_forward, R.id.phone_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_payment_back:
                AppConfig.trackCustomEvent(this, "tx_payment_back_click", "签约结果－回到首页");
                AppManager.finishPending();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.tx_payment_forward:
                AppManager.finishPending();
                AppConfig.trackCustomEvent(this, "tx_payment_forward_click", "签约结果－重新支付");
                if (result){
                    startActivity(new Intent(this,WithholdingManagementActivity.class));
                    finish();
                }else {
                    finish();
                }
                break;

            case R.id.phone_text:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "4008461818");
                intent.setData(data);
                startActivity(intent);
                break;
        }
    }
}

package com.jinke.community.ui.toast;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.acachebean.CallCenterBean;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-18.
 */

public class HouseKeeperPhoneDialog extends Dialog implements View.OnClickListener {
    Context context;
    TextView txCallHotLine, txCallHousekeeper, cancel;
    private LinearLayout llHotLine, llHousekeeper;

    private PhoneDialoglListener listener;
    private CallCenterBean bean;

    public HouseKeeperPhoneDialog(@NonNull Context context, CallCenterBean bean, PhoneDialoglListener listener) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.listener = listener;
        this.bean = bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_housekeeper_phone);
        llHotLine = findViewById(R.id.ll_hot_line);
        txCallHotLine = findViewById(R.id.tx_call_hot_line);
        llHousekeeper =  findViewById(R.id.ll_housekeeper);
        txCallHousekeeper = findViewById(R.id.tx_call_housekeeper);
        cancel = findViewById(R.id.tx_dialog_cancel);
        initData();
    }

    private void initData() {
        llHotLine.setVisibility(StringUtils.isEmpty(bean.getServicePhone()) ? View.GONE : View.VISIBLE);
        if (bean.getServicePhone() != null && !StringUtils.isEmpty(bean.getServicePhone())) {
            txCallHotLine.setText(bean.getServicePhone());
        }
        if (bean.getIsKeeper() == 1) {
            if (bean.getKeeperName() != null && !StringUtils.isEmpty(bean.getKeeperName())) {
                txCallHousekeeper.setText(bean.getKeeperName());
                llHousekeeper.setVisibility(View.VISIBLE);
            }
        } else {
            llHousekeeper.setVisibility(View.GONE);

        }
        llHotLine.setOnClickListener(this);
        cancel.setOnClickListener(this);
        llHousekeeper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hot_line:
                if (bean != null && bean.getServicePhone() != null && !StringUtils.isEmpty(bean.getServicePhone()))
                    listener.onCall(bean.getServicePhone());
                break;
            case R.id.ll_housekeeper:
                if (bean != null && bean.getKeeperPhone() != null && !StringUtils.isEmpty(bean.getKeeperPhone()))
                    listener.onCall(bean.getKeeperPhone());
                break;
            case R.id.tx_dialog_cancel:
                dismiss();
                break;
        }
    }

    public interface PhoneDialoglListener {
        void onCall(String phone);
    }
}

package com.jinke.community.ui.toast;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by Administrator on 2017/11/6.
 */

public class PayTipsDialog extends Dialog implements View.OnClickListener {
    Context context;
    TextView txTitle,  txCancle;

    private String content;

    public PayTipsDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        this.context = context;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_tips);
        txTitle = (TextView) findViewById(R.id.tx_title);
        txCancle = (TextView) findViewById(R.id.tx_cancel);
        initData();
        txCancle.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_cancel:
                dismiss();
                break;
        }
    }

}

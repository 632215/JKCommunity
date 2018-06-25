package com.jinke.community.ui.toast;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by Administrator on 2017/11/6.
 */

public class NetWorkErrDialog extends Dialog implements View.OnClickListener {
    Context context;
    TextView txTitle, txCancle;
    private NetWorkErrDialogListener listener;
    private String content;

    public void setListener(NetWorkErrDialogListener listener) {
        this.listener = listener;
    }

    public NetWorkErrDialog(@NonNull Context context) {
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
        setContentView(R.layout.dialog_network_err);
        txCancle = (TextView) findViewById(R.id.tx_refresh);
        initData();
        txCancle.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_refresh:
                dismiss();
                listener.refresh();
                break;
        }
    }

    public interface NetWorkErrDialogListener {
        void refresh();
    }
}

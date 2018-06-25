package com.jinke.community.ui.toast;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by root on 17-8-14.
 */

public class BindHouseDialog extends AlertDialog implements View.OnClickListener {
    private TextView title;
    private TextView content;
    private TextView cancel;
    private TextView Sure;
    private TextView verticalline;
    private onCallPhoneListener listener;
    private String phone;
    private Context context;

    public BindHouseDialog(Context context, onCallPhoneListener listener, String phone) {
        super(context, R.style.DialogTheme);
        this.listener = listener;
        this.phone = phone;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_band_house);
        title = findViewById(R.id.tx_call_phone_title);
        content = findViewById(R.id.tx_call_phone_content);
        cancel = findViewById(R.id.tx_call_phone_cancel);
        Sure = findViewById(R.id.tx_call_phone_sure);
        verticalline = findViewById(R.id.tx_vertical_line);
        cancel.setOnClickListener(this);
        Sure.setOnClickListener(this);
    }

    public void setTitle(String tx) {
        if (title != null) {
            title.setText(tx);
        }
    }

    public void setContent(String msg) {
        if (content != null) {
            content.setText(Html.fromHtml(msg));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_call_phone_sure:
                listener.onSure(phone);
                break;
            case R.id.tx_call_phone_cancel:
                dismiss();
                break;
        }
    }

    public void setCancleVisibility(int visibility) {
        cancel.setVisibility(visibility);
        verticalline.setVisibility(visibility);
    }

    public interface onCallPhoneListener {
        void onSure(String phone);
    }
}

package com.jinke.community.ui.toast;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jinke.community.R;

import org.w3c.dom.Text;

/**
 * Created by root on 17-8-14.
 */

public class CallPhoneDialog extends AlertDialog implements View.OnClickListener {
    private TextView title;
    private TextView content;
    private TextView cancel;
    private TextView Sure;
    private TextView verticalline;
    private onCallPhoneListener listener;
    private String phone;
    private Context context;

    public CallPhoneDialog(Context context, onCallPhoneListener listener, String phone) {
        super(context, R.style.DialogTheme);
        this.listener = listener;
        this.phone = phone;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call_phone);
        title = (TextView) findViewById(R.id.tx_call_phone_title);
        content = (TextView) findViewById(R.id.tx_call_phone_content);
        cancel = (TextView) findViewById(R.id.tx_call_phone_cancel);
        Sure = (TextView) findViewById(R.id.tx_call_phone_sure);
        verticalline = (TextView) findViewById(R.id.tx_vertical_line);
        cancel.setOnClickListener(this);
        Sure.setOnClickListener(this);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.7); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
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

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

public class UniversalDialog extends Dialog implements View.OnClickListener {
    Context context;
    TextView txTitle, txContent, txSure;

    private UniversalDialog.onUniversalDialogListener listener;
    private String content;
    private String title="说明";

    public UniversalDialog(@NonNull Context context, String content, UniversalDialog.onUniversalDialogListener listener) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.listener = listener;
        this.content = content;
    }

    public UniversalDialog(@NonNull Context context, UniversalDialog.onUniversalDialogListener listener) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.listener = listener;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTxTitle(String content) {
        this.title = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_universal);
        txTitle = (TextView) findViewById(R.id.tx_title);
        txContent = (TextView) findViewById(R.id.tx_content);
        txSure = (TextView) findViewById(R.id.tx_sure);
        initData();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.7); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    private void initData() {
        txContent.setText(content);
        txTitle.setText(title);
        txSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_sure:
                listener.onCall("");
                dismiss();
                break;
        }
    }

    public interface onUniversalDialogListener {
        void onCall(String phone);
    }

    public void setTxTitleShow(int show) {
        if (txTitle != null) {
            txTitle.setVisibility(show);
        }
    }

}

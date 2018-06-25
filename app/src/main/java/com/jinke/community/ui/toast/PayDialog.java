package com.jinke.community.ui.toast;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jinke.community.R;


/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 13.01.15 at 14:22
 */
public class PayDialog extends AlertDialog {

    public PayDialog(Context context) {
        this(context, R.style.SpotsDialogDefault);
    }

    public PayDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pay, null);
        setContentView(loadingView);
    }

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.9f;
        window.setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}


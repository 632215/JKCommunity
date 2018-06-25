package com.jinke.community.ui.toast.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jinke.community.R;


/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 13.01.15 at 14:22
 */
public class SpotsDialog extends AlertDialog {

    private ImageView mLoadingImageView;

    public SpotsDialog(Context context) {
        this(context, R.style.SpotsDialogDefault);
    }

    public SpotsDialog(Context context, int theme) {
        super(context, theme);
    }


    AnimationDrawable animationDrawable;
    DotsTextView dotsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        mLoadingImageView = (ImageView) loadingView.findViewById(R.id.image_xiaobai);
        dotsTextView = (DotsTextView) loadingView.findViewById(R.id.dots);
        mLoadingImageView.setImageResource(R.drawable.loading_anim);
        setContentView(loadingView);
    }


    private AnimationDrawable mLoadingAnimationDrawable;

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.9f;
        window.setAttributes(lp);
        //注意将动画的启动放置在Handler中.否则只可看到第一张图片
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingAnimationDrawable = (AnimationDrawable) mLoadingImageView.getDrawable();
                mLoadingAnimationDrawable.start();
            }
        }, 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //结束帧动画
        mLoadingAnimationDrawable = (AnimationDrawable) mLoadingImageView.getDrawable();
        mLoadingAnimationDrawable.stop();
    }
}


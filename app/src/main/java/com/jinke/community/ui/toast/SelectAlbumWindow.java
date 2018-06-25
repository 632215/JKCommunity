package com.jinke.community.ui.toast;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by root on 17-8-6.
 */

public class SelectAlbumWindow extends PopupWindow implements View.OnClickListener {
    private TextView camera;
    private TextView album;
    private LinearLayout itemRootview;
    private TextView cancel;
    private LayoutInflater inflater;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout shareRoot;
    private OnSelectAlbumWindowListener listener;

    public void setOnSelectAlbumWindowListener(OnSelectAlbumWindowListener listener) {
        this.listener = listener;
    }

    public SelectAlbumWindow(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View rootView = inflater.inflate(R.layout.window_select_album, null);
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        shareRoot = rootView.findViewById(R.id.share_root);
        cancel =  rootView.findViewById(R.id.tx_cancel);
        camera = rootView.findViewById(R.id.tx_camera);
        album = rootView.findViewById(R.id.tx_album);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        shareRoot.setOnClickListener(this);
        cancel.setOnClickListener(this);
        camera.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    public void setCancelVisiable(int visiable) {
        cancel.setVisibility(visiable);
    }

    public void setCamera(String msg) {
        camera.setText(msg);
    }

    public void setAlbum(String msg) {
        album.setText(msg);
    }

    public void dismitPopWindow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showPopWindow(View v) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_camera:
                listener.relationship("1");
                dismitPopWindow();
                break;

            case R.id.tx_album:
                listener.relationship("2");
                dismitPopWindow();
                break;

            case R.id.share_root:
                dismitPopWindow();
                break;

            case R.id.tx_cancel:
                dismitPopWindow();
                break;
        }
    }

    public interface OnSelectAlbumWindowListener {
        void relationship(String relationship);
    }
}

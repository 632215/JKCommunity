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

public class ShareMethodWindow extends PopupWindow implements View.OnClickListener {
    private TextView txWechart;
    private TextView txFriend;
    private TextView txQq;
    private TextView txZone;
    private LinearLayout itemRootview;
    private TextView cancel;
    private LayoutInflater inflater;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout shareRoot;
    private OnMethodSelectedListener listener;

    public void setListener(OnMethodSelectedListener listener) {
        this.listener = listener;
    }

    public ShareMethodWindow(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View rootView = inflater.inflate(R.layout.window_share_method, null);
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        shareRoot = rootView.findViewById(R.id.share_root);
        cancel = rootView.findViewById(R.id.tx_cancel);
        txWechart = rootView.findViewById(R.id.tx_wechart);
        txFriend = rootView.findViewById(R.id.tx_friend);
        txQq = rootView.findViewById(R.id.tx_qq);
        txZone = rootView.findViewById(R.id.tx_zone);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        shareRoot.setOnClickListener(this);
        cancel.setOnClickListener(this);
        txWechart.setOnClickListener(this);
        txFriend.setOnClickListener(this);
        txQq.setOnClickListener(this);
        txZone.setOnClickListener(this);
    }

    public void setCancelVisiable(int visiable) {
        cancel.setVisibility(visiable);
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
            case R.id.tx_wechart:
                listener.method("1");
                dismitPopWindow();
                break;

            case R.id.tx_friend:
                listener.method("2");
                dismitPopWindow();
                break;

            case R.id.tx_qq:
                listener.method("3");
                dismitPopWindow();
                break;

            case R.id.tx_zone:
                listener.method("4");
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

    public interface OnMethodSelectedListener {
        void method(String relationship);
    }
}

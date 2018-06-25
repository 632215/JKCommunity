package com.jinke.community.ui.toast;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by root on 23/11/16.
 */

public class DeleteRelationWindow extends PopupWindow implements View.OnClickListener {
    private TextView cancel;
    private TextView sure;
    private RelativeLayout rlRoot;

    private LayoutInflater inflater;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private OnSureListener listener;
    //用以记录关系
    private String relation = "";
    private String relationCode = "";

    public void setListener(OnSureListener listener) {
        this.listener = listener;
    }

    public DeleteRelationWindow(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View rootView = inflater.inflate(R.layout.window_delete_relation, null);
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlRoot = (RelativeLayout) rootView.findViewById(R.id.rl_root);
        cancel = (TextView) rootView.findViewById(R.id.tx_cancel);
        sure = (TextView) rootView.findViewById(R.id.tx_sure);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        rlRoot.setOnClickListener(this);
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
            case R.id.share_root:
                dismitPopWindow();
                break;

            case R.id.tx_cancel:
                dismitPopWindow();
                break;

            case R.id.tx_sure:
                listener.sureDelete();
                dismitPopWindow();
                break;
        }
    }

    public interface OnSureListener {
        void sureDelete();
    }
}

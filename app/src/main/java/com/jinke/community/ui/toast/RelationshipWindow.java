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
 * Created by root on 23/11/16.
 */

public class RelationshipWindow extends PopupWindow implements View.OnClickListener {
    private TextView family;//家人 "2"
    private TextView friend;//朋友 "3"
    private TextView Leasinghouseholds;//租赁户"4"
    private TextView owner;//业主"1"
    private TextView cancel;
    private TextView sure;

    private LayoutInflater inflater;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private OnRelationshipWindowListener listener;
    //用以记录关系
    private String relation = "";
    private String relationCode = "";

    public void setOnRelationshipWindowListener(OnRelationshipWindowListener listener) {
        this.listener = listener;
    }

    public RelationshipWindow(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View rootView = inflater.inflate(R.layout.window_relationship, null);

        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        shareRoot = (RelativeLayout) rootView.findViewById(R.id.share_root);
        cancel = (TextView) rootView.findViewById(R.id.tx_cancel);
        sure = (TextView) rootView.findViewById(R.id.tx_sure);

        family = (TextView) rootView.findViewById(R.id.tx_family);
        friend = (TextView) rootView.findViewById(R.id.tx_friend);
        Leasinghouseholds = (TextView) rootView.findViewById(R.id.tx_Tenants);
        owner = (TextView) rootView.findViewById(R.id.tx_owner);

        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        family.setOnClickListener(this);
        friend.setOnClickListener(this);
        Leasinghouseholds.setOnClickListener(this);
        owner.setOnClickListener(this);
        //初始化默认选择项
        changeSelectedUi(friend);
        relation = "朋友";
        relationCode = "3";
    }


    public void setOwnerVisibility(int VISIBLE) {
        owner.setVisibility(VISIBLE);
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
            case R.id.tx_family:
                changeRecord("家人", "2");
                break;

            case R.id.tx_friend:
                changeRecord("朋友", "3");
                break;

            case R.id.tx_Tenants:
                changeRecord("租赁户", "4");
                break;

            case R.id.tx_owner:
                changeRecord("业主", "1");
                break;

            case R.id.share_root:
                dismitPopWindow();
                break;

            case R.id.tx_cancel:
                dismitPopWindow();
                break;

            case R.id.tx_sure:
                listener.relationship(relation, relationCode);
                dismitPopWindow();
                break;
        }
    }

    private void changeRecord(String relation, String relationCode) {
        this.relation = relation;
        this.relationCode = relationCode;
        switch (relationCode) {
            case "1":
                changeSelectedUi(owner);
                changeUnSelectedUi(Leasinghouseholds);
                changeUnSelectedUi(friend);
                changeUnSelectedUi(family);
                break;
            case "2":
                changeSelectedUi(family);
                changeUnSelectedUi(Leasinghouseholds);
                changeUnSelectedUi(friend);
                changeUnSelectedUi(owner);
                break;
            case "3":
                changeSelectedUi(friend);
                changeUnSelectedUi(Leasinghouseholds);
                changeUnSelectedUi(owner);
                changeUnSelectedUi(family);
                break;
            case "4":
                changeSelectedUi(Leasinghouseholds);
                changeUnSelectedUi(owner);
                changeUnSelectedUi(friend);
                changeUnSelectedUi(family);
                break;
        }
    }

    private void changeUnSelectedUi(TextView view) {
        view.setBackgroundResource(R.color.white);
        view.setTextColor(mContext.getResources().getColor(R.color.activity_authoried_color2));
    }

    private void changeSelectedUi(TextView view) {
        view.setBackgroundResource(R.mipmap.icon_activity_authorized_relation__selected_bg);
        view.setTextColor(mContext.getResources().getColor(R.color.activity_authoried_color1));
    }

    public interface OnRelationshipWindowListener {
        void relationship(String title, String relationship);
    }
}

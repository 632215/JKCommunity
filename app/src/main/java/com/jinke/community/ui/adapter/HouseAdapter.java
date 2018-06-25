package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.ui.activity.house.AuthenticationTipsActivity;
import com.jinke.community.ui.activity.house.AuthorizationActivity;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.utils.TextUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-7-31.
 */

public class HouseAdapter extends CommonAdapter<HouseListBean.ListBean> {
    private Context mContext;

    public HouseAdapter(@NonNull Context context, int layoutResId, @NonNull List<HouseListBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final HouseListBean.ListBean bean, int position) {
        RelativeLayout rlRoot = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_root);
        TextView title = (TextView) baseViewHolder.getViewByViewId(R.id.tx_house_title);
        TextView owner = (TextView) baseViewHolder.getViewByViewId(R.id.tx_house_owen);
        RelativeLayout rlAuthorizedState = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_authorized_state);
        TextView setting = (TextView) baseViewHolder.getViewByViewId(R.id.tx_house_setting);
        TextView txAuthorized = (TextView) baseViewHolder.getViewByViewId(R.id.tx_authorized);
        RelativeLayout rlGetHouse = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_get_house);
        TextView txGetHouse = (TextView) baseViewHolder.getViewByViewId(R.id.tx_get_house);

        title.setText(bean.getCommunity_name() + bean.getHouse_name());
        String ownerString = "";
        for (HouseListBean.ListBean.OwnersBean ownerBean : bean.getOwners()) {
            ownerString += ownerBean.getOwnerName() + "  " + TextUtils.changTelNum(ownerBean.getPhone()) + "   ";
        }
        owner.setText(ownerString);

        switch (bean.getIsgrant()) {//是否可以授权,1-可以，0-不可以
            case 0:
                rlAuthorizedState.setVisibility(View.GONE);
                break;
            case 1:
                rlAuthorizedState.setVisibility(View.VISIBLE);
                rlAuthorizedState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean != null)
                            AnalyUtils.addAnaly(10043, bean.getHouse_id());
                        else
                            AnalyUtils.addAnaly(10043);
                        Intent intent = new Intent(mContext, AuthorizationActivity.class);
                        intent.putExtra("bean", bean);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }

        //判断是否是当前默认房屋，设置字体颜色和背景颜色
        if (bean != null) {
            rlRoot.setBackgroundResource(bean.getDft_house() == 1 ? R.mipmap.icon_house_list_bg_red : R.mipmap.icon_house_list_bg_pick);
            title.setTextColor(bean.getDft_house() == 1 ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.activity_authoried_color1));
            owner.setTextColor(bean.getDft_house() == 1 ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.activity_authoried_color1));
            setting.setTextColor(bean.getDft_house() == 1 ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.activity_authoried_color1));
            txGetHouse.setTextColor(bean.getDft_house() == 1 ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.activity_authoried_color1));
            setDrawableLeftTxt(mContext, setting, bean.getDft_house() == 1 ? R.mipmap.icon_house_choose_bg_red : R.mipmap.icon_house_choose_bg_pick);

            txAuthorized.setTextColor(bean.getDft_house() == 1 ? mContext.getResources().getColor(R.color.activity_authoried_color1) : mContext.getResources().getColor(R.color.white));
            DrawableUtils.setDrawableLeft(mContext, txAuthorized, bean.getDft_house() == 1 ? R.mipmap.icon_activity_myhouse_authoried_red : R.mipmap.icon_activity_myhouse_authoried_white);
            rlAuthorizedState.setBackgroundResource(bean.getDft_house() == 1 ? R.mipmap.icon_authorize_button_pick_bg : R.mipmap.icon_authorize_button_red_bg);
        }
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.stateDefault(bean);
            }
        });

        //判断房屋是否接房  未接房——接房指引   已接房——授权
        if (StringUtils.equals("已售", bean.getHouseStatus())) {
            rlAuthorizedState.setVisibility(View.GONE);
            setting.setVisibility(View.GONE);
            rlGetHouse.setVisibility(View.VISIBLE);
            rlGetHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, AuthenticationTipsActivity.class)
                            .putExtra("houseId", bean.getHouse_id())
                            .putExtra("houseName", bean.getHouse_name())
                    );
                }
            });
        } else {
            rlGetHouse.setVisibility(View.GONE);
            setting.setVisibility(View.VISIBLE);
        }
    }

    public void setOnDefaultHouseListener(OnDefaultHouseListener listener) {
        this.listener = listener;
    }

    public OnDefaultHouseListener listener;

    public interface OnDefaultHouseListener {
        void stateDefault(HouseListBean.ListBean bean);
    }

    public void setDrawableLeftTxt(Context mContext, TextView txt, int res) {
        Drawable drawable = mContext.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        txt.setCompoundDrawables(drawable, null, null, null);
    }
}

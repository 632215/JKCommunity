package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.impl.PropertyHistoryImpl;
import com.jinke.community.service.listener.IPropertyHistoryListener;
import com.jinke.community.ui.activity.base.MsgDeliveryActivity;
import com.jinke.community.ui.activity.broken.NewPropertyDetailsActivity;
import com.jinke.community.ui.activity.broken.PropertyDetailsActivity;
import com.jinke.community.ui.activity.house.MyHouseActivity;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by Administrator on 2017/8/9.
 */

public class MsgCenterAdapter extends RecyclerView.Adapter<MsgCenterAdapter.ViewHolder> implements IPropertyHistoryListener {
    private List<MsgBean.ListBean> mList = null;
    private Context mContext;
    private MsgCenterAdapterListener listener;
    private MsgBean.ListBean postBean = null;


    public MsgCenterAdapter(List<MsgBean.ListBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public MsgCenterAdapter(List<MsgBean.ListBean> mList, Context mContext, MsgCenterAdapterListener listener) {
        this.mList = mList;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setData(List<MsgBean.ListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setListener(MsgCenterAdapterListener listener) {
        this.listener = listener;
    }

    public List getData() {
        return mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_msg_center
                , parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MsgBean.ListBean bean = mList.get(position);
        if (bean != null) {
            holder.rlRoot.setAlpha(StringUtils.equals("1", bean.getIsRead()) ? 1 : 0.5f);
            changeImg(holder, bean);
            holder.txType.setText(bean.getTitle());
            holder.txContent.setText(bean.getMessage());
            holder.txTime.setText(TextUtils.intToString(String.valueOf(bean.getCreateTime()), "HH:mm"));
            addLlistener(holder, bean, position);
            if (position == mList.size() - 1)
                holder.rlRoot.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            else
                holder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.shape_parting_bottom_10dp));
        }
    }

    //image的图标
    private void changeImg(ViewHolder holder, MsgBean.ListBean bean) {
        switch (bean.getType()) {
            case "POSTMAN_POST": //POSTMAN_POST 快递员将包裹投递入柜
                changeImg(R.mipmap.icon_msg_center_wait, holder.imgType);
                break;

            case "GETTER_TAKE": //收件人取走包裹
            case "POSTMAN_TAKE": //快递员将包裹取回
            case "ADMIN_TAKE": //柜子管理员(值守)将包裹取回
                changeImg(R.mipmap.icon_msg_center_out, holder.imgType);
                break;

            case "POSTIT": //报事提醒
                changeImg(R.mipmap.icon_msg_center_prpterty, holder.imgType);
                break;

            case "HOUSE_BIND": //房屋解绑提醒
                changeImg(R.mipmap.icon_msg_center_house, holder.imgType);
                break;

//                case "DK_PAY": //代扣提醒
//                    changeImg(R.mipmap.icon_msg_center_out, holder.imgType);
//                    break;

            case "HOUSEING"://接房
                changeImg(R.mipmap.icon_msg_center_prpterty, holder.imgType);
                break;

        }
    }

    //item点击事件
    private void addLlistener(ViewHolder holder, final MsgBean.ListBean bean, final int position) {
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.equals("1", bean.getIsRead()))// 0已读 1 未读
                    listener.upDateMsg(position);
                switch (bean.getType()) {
                    case "POSTMAN_POST": //POSTMAN_POST 快递员将包裹投递入柜
                    case "GETTER_TAKE": //收件人取走包裹
                    case "POSTMAN_TAKE": //快递员将包裹取回
                    case "ADMIN_TAKE": //柜子管理员(值守)将包裹取回
                        mContext.startActivity(new Intent(mContext, MsgDeliveryActivity.class).putExtra("bean", bean));
                        break;

                    case "POSTIT": //报事提醒
                        postBean = bean;
                        //现有报事都为试点小区
//                        HouseListBean.ListBean listBean = MyApplication.getInstance().getDefaultHouse();
//                        if (listBean != null) {
//                            Map map = new HashMap();
//                            map.put("communityId", listBean.getCommunity_id());
//                            new PropertyHistoryImpl(mContext).getConfig(map, MsgCenterAdapter.this);
//                        }
                        mContext.startActivity(new Intent(mContext, NewPropertyDetailsActivity.class).putExtra("keepId", postBean.getInfoId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case "HOUSE_BIND": //房屋解绑提醒
                        mContext.startActivity(new Intent(mContext, MyHouseActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

//                case "DK_PAY": //代扣提醒
//                    break;

                    case "HOUSEING"://接房
                        if (bean != null && !StringUtils.isEmpty(bean.getUrl()))
                            mContext.startActivity(new Intent(mContext, WebActivity.class)
                                    .putExtra("url", bean.getUrl() + "&accessToken=" + MyApplication.getBaseUserBean().getAccessToken())
                                    .putExtra("title", "问卷调查")
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                }
            }
        });
    }

    private void changeImg(int imgID, ImageView imgType) {
        Glide.with(mContext).load(imgID).into(imgType);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txType;
        private TextView txContent;
        private TextView txTime;
        private ImageView imgType;
        private RelativeLayout rlRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            txType = itemView.findViewById(R.id.tx_type);
            txContent = itemView.findViewById(R.id.tx_content);
            txTime = itemView.findViewById(R.id.tx_time);
            imgType = itemView.findViewById(R.id.img_type);
            rlRoot = itemView.findViewById(R.id.rl_root);
        }
    }

    public interface MsgCenterAdapterListener {
        void upDateMsg(int positon);
    }

    @Override
    public void onError(String code, String msg) {
    }

    @Override
    public void getKeepPostItListNext(PropertyBean info) {
    }

    @Override
    public void getConfigNext(CommunityBean info) {
        for (CommunityBean.ListBean bean : info.getList())
            if (StringUtils.equals("property_broken_test", bean.getAuthority_code())) {
                if (info != null && StringUtils.equals("1", bean.getStatus())) {
                    if (MyApplication.getInstance().getDefaultHouse() != null)
                        mContext.startActivity(new Intent(mContext, NewPropertyDetailsActivity.class).putExtra("keepId", postBean.getInfoId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    if (MyApplication.getInstance().getDefaultHouse() != null)
                        mContext.startActivity(new Intent(mContext, PropertyDetailsActivity.class).putExtra("postId", postBean.getInfoId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
    }

    @Override
    public void getConfigError() {
    }
}

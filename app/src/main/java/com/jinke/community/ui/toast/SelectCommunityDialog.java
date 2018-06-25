package com.jinke.community.ui.toast;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.ui.adapter.CommunityAdapter;
import com.jinke.community.ui.adapter.SelectCommunityAdapter;
import com.jinke.community.ui.adapter.SelectHouseAdapter;
import com.jinke.community.utils.ACache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.commont.NetWorksUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-10.
 */

public class SelectCommunityDialog extends AlertDialog implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context mContext;
    private ListView listView;
    private TextView cancel, sure, title;
    private List<UserCommunityBean.ListBean> communityList;
    private CommunityAdapter adapter;
    private String titleString;
    private UserCommunityBean.ListBean bean;
    SelectCommunityListener listener;

    public void setCommunityList(List<UserCommunityBean.ListBean> communityList) {
        this.communityList = communityList;
    }

    public SelectCommunityDialog(Context context, SelectCommunityListener listener) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.listener = listener;
    }

    public SelectCommunityDialog(Context context, SelectCommunityListener listener, String titleString) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.listener = listener;
        this.titleString = titleString;
    }

    public void setListener(SelectCommunityListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_select_house);
        listView = (ListView) findViewById(R.id.listView);
        cancel = (TextView) findViewById(R.id.tx_dialog_cancel);
        sure = (TextView) findViewById(R.id.tx_dialog_sure);
        title = (TextView) findViewById(R.id.tx_select_house_title);
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        if (!StringUtils.isEmpty(titleString))
            title.setText(titleString);
        if (communityList == null) {
            communityList = new ArrayList<>();
        }
        adapter = new CommunityAdapter(mContext, R.layout.item_select_house_dialog, communityList);
        listView.setAdapter(adapter);
        adapter.setDataList(communityList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_dialog_cancel:
                dismiss();
                break;
            case R.id.tx_dialog_sure:
                if (listener != null) {
                    if (bean != null) {
                        listener.selectCommunity(bean);
                        dismiss();
                    } else {
                        ToastUtils.showShort(mContext, "请选择社区！");
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (UserCommunityBean.ListBean bean : communityList) {
            bean.setSelect(false);
        }
        communityList.get(position).setSelect(true);
        adapter.setDataList(communityList);
        bean = communityList.get(position);
    }


    public interface SelectCommunityListener {
        void selectCommunity(UserCommunityBean.ListBean bean);
    }
}

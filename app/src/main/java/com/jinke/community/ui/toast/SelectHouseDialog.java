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
import com.jinke.community.bean.PublicHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
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

public class SelectHouseDialog extends AlertDialog implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context mContext;
    private ListView listView;
    private TextView cancel, sure;
    private SelectHouseAdapter adapter;
    //    private List<PublicHouseBean.ListBean> list = new ArrayList<>();
    //    private PublicHouseBean.ListBean listBean = null;
    private HouseListBean houseListBean;//缓存的房屋列表信息
    private HouseListBean.ListBean bean; //保存点击的item
    private List<HouseListBean.ListBean> list = new ArrayList<>();
    private ACache aCache;

    public SelectHouseDialog(Context context, onSelectHouseListener listener) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_select_house);
        aCache = ACache.get(mContext);
        listView = (ListView) findViewById(R.id.listView);
        cancel = (TextView) findViewById(R.id.tx_dialog_cancel);
        sure = (TextView) findViewById(R.id.tx_dialog_sure);

        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        adapter = new SelectHouseAdapter(mContext, R.layout.item_select_house_dialog, list);
        listView.setAdapter(adapter);

        if (houseListBean == null && NetWorksUtils.isConnected(mContext)) {
            getHouseList();
        } else {
            list = houseListBean.getList();
            adapter.setDataList(list);
        }
    }

    /**
     * 获取房屋列表
     */
    public void getHouseList() {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                list = info.getList();
                adapter.setDataList(list);
                aCache.put("HouseListBean", info, ACache.TIME_DAY);
            }

            @Override
            public void onError(String Code, String Msg) {
                if (!StringUtils.isEmpty(Msg))
                    ToastUtils.showLong(mContext, Msg);
            }
        };
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
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
                        listener.selectHouse(bean);
                        dismiss();
                    } else {
                        ToastUtils.showShort(mContext, "请选择房屋");
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (HouseListBean.ListBean bean : list) {
            bean.setSelect(false);
        }
        list.get(position).setSelect(true);
        adapter.setDataList(list);
        bean = houseListBean.getList().get(position);
    }

    onSelectHouseListener listener;

    public interface onSelectHouseListener {
        void selectHouse(HouseListBean.ListBean bean);
    }
}

package com.jinke.community.ui.activity.broken;

import android.graphics.Rect;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.presenter.BrokenDetailsPresent;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.adapter.BrokenDetailsAdapter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IBrokenDetailsView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-11.
 */

public class BrokenDetailsActivity extends BaseActivity<IBrokenDetailsView, BrokenDetailsPresent> implements IBrokenDetailsView, AdapterView.OnItemClickListener {
    @Bind(R.id.item_details_image)
    SimpleDraweeView itemDetailsImage;
    @Bind(R.id.item_details_title)
    TextView itemTitle;
    @Bind(R.id.item_details_time)
    TextView itemDetailsTime;
    @Bind(R.id.property_state)
    TextView propertyState;
    @Bind(R.id.tx_broken_content)
    TextView txBrokenContent;
    @Bind(R.id.gv_broken_list)
    GridView gvBrokenList;
    BrokenDetailsAdapter adapter;
    List<String> list = new ArrayList<>();
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    Map<String, String> map = new HashMap<>();

    @Override
    public BrokenDetailsPresent initPresenter() {
        return new BrokenDetailsPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_broken_details;
    }

    @Override
    protected void initView() {
        setTitle("爆料详情");
        showBackwardView("", true);
        adapter = new BrokenDetailsAdapter(this, R.layout.item_broken_details, list);
        gvBrokenList.setAdapter(adapter);
        String brokeNewsId = getIntent().getStringExtra("brokeNewsId");
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("brokeNewsId", brokeNewsId);
        presenter.getBrokeNewsDetail(map);
        gvBrokenList.setOnItemClickListener(this);
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onSuccess(BrokenDetailsBean bean) {
        list = bean.getPicUrls();
        adapter.setDataList(list);
        txBrokenContent.setText(bean.getContent());
        itemTitle.setText(bean.getContactName());
        propertyState.setText(bean.getStatus());
        itemDetailsTime.setText(bean.getCreateTime());
        Glide.with(this).load(bean.getAvatar())
                .error(R.drawable.icon_fail_pic)
                .placeholder(R.drawable.icon_fail_pic).crossFade(1000)
                .into(itemDetailsImage);

        setState(bean.getStatus(), propertyState);
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    public void onPropertyInfo(BrokenDetailsBean bean) {
    }

    @Override
    public void getKeeperDetailNext(KeepPropertyBean info) {
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    public void setState(String state, TextView textView) {
        switch (state) {

            case "0":
                textView.setText("待处理");
                break;

            case "1":
                textView.setText("处理中");
                break;

            case "2":
                textView.setText("已处理");
                break;

            case "99":
                textView.setText("已关闭");
                break;
        }
    }

    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        computeBoundsBackward();
        PhotoActivity.startActivity(BrokenDetailsActivity.this, mThumbViewInfoList, position);
    }

    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward() {
        mThumbViewInfoList.clear();
        for (int i = 0; i < list.size(); i++) {
            View itemView = gvBrokenList.getChildAt(i);
            ThumbViewInfo thumbViewInfo = new ThumbViewInfo(list.get(i));
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.simpleImage);
                thumbView.getGlobalVisibleRect(bounds);
                thumbViewInfo.setBounds(bounds);
            }
            mThumbViewInfoList.add(thumbViewInfo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(BrokenDetailsActivity.this);
        StatService.trackBeginPage(BrokenDetailsActivity.this, "爆料详情");
        AnalyUtils.setBAnalyResume(this, "爆料详情");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BrokenDetailsActivity.this);
        StatService.trackEndPage(BrokenDetailsActivity.this, "爆料详情");
        AnalyUtils.setBAnalyPause(this, "爆料详情");
    }
}

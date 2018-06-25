package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.BrokenDetailsPresent;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.adapter.BrokenDetailsAdapter;
import com.jinke.community.ui.adapter.SatisfactionAdapter;
import com.jinke.community.ui.toast.HouseKeeperPhoneDialog;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AndroidUtils;
import com.jinke.community.view.IBrokenDetailsView;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-6.
 * 我的报事（报事详情）
 */

public class PropertyDetailsActivity extends BaseActivity<IBrokenDetailsView, BrokenDetailsPresent> implements IBrokenDetailsView, HouseKeeperPhoneDialog.PhoneDialoglListener {
    @Bind(R.id.tx_state)
    TextView txState;
    @Bind(R.id.image_state)
    ImageView imageState;
    @Bind(R.id.tx_time)
    TextView txTime;
    @Bind(R.id.tx_id)
    TextView txId;
    @Bind(R.id.tx_content)
    TextView txContext;
    @Bind(R.id.fill_grid_view)
    FillGridView picGridView;
    @Bind(R.id.rl_access)
    RelativeLayout rlAccess;
    @Bind(R.id.grid_view)
    FillGridView satisfactionGridView;
    @Bind(R.id.tx_service_satisfaction)
    TextView txatisfaction;
    @Bind(R.id.tx_access)
    TextView txAccess;
    @Bind(R.id.tx_house)
    TextView txHouse;

    private PropertyBean.ListBean bean = null;
    BrokenDetailsAdapter adapter;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
    private SatisfactionAdapter satisfactionAdapter;
    private List<String> satisfactionList = new ArrayList<>();
    private HouseKeeperPhoneDialog houseKeeperPhoneDialog;
    private String postId = null;

    @Override
    public BrokenDetailsPresent initPresenter() {
        return new BrokenDetailsPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_property_details;
    }

    @Override
    protected void initView() {
        setTitle("我的报事");
        showBackwardView("", true);
        postId = getIntent().getStringExtra("postId");
        getPostDetails();
    }

    /**
     * 获取报事列表
     */
    private void getPostDetails() {
        if (!StringUtils.isEmpty(postId)) {
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("postId", postId);
            showProgressDialog("true");
            presenter.getKeeperDetail(map);
        }
    }

    @Override
    public void getKeeperDetailNext(KeepPropertyBean info) {
        hideDialog();
        initData(info);
    }

    private void initData(final KeepPropertyBean info) {
        if (info != null) {
            switch (info.getStatus()) {
                case 1:
                    imageState.setImageResource(R.mipmap.icon_activity_break_history2);
                    txState.setTextColor(getResources().getColor(R.color.activity_broken_dynamic_color5));
                    txState.setText("待处理");
                    break;
                case 2:
                    imageState.setImageResource(R.mipmap.icon_activity_break_history1);
                    txState.setTextColor(getResources().getColor(R.color.activity_broken_dynamic_color6));
                    txState.setText("处理中");
                    break;
                case 3:
                    imageState.setImageResource(R.mipmap.icon_activity_break_history4);
                    txState.setTextColor(getResources().getColor(R.color.activity_broken_dynamic_color7));
                    txState.setText("已完结");
                    break;
                case 4:
                    imageState.setImageResource(R.mipmap.icon_activity_break_history3);
                    txState.setTextColor(getResources().getColor(R.color.activity_broken_dynamic_color8));
                    txState.setText("已评价");
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date(Long.parseLong(String.valueOf(info.getCreateTime())) * 1000);
            txTime.setText(sdf.format(date));
            txId.setText(info.getKeepId().toString().trim());
            txContext.setText(info.getContent());
            txHouse.setText(info.getHouseName());
            adapter = new BrokenDetailsAdapter(this, R.layout.item_broken_details, info.getImage());

            picGridView.setAdapter(adapter);
            if (info.getImage().size() <= 0)
                picGridView.setVisibility(View.GONE);
            picGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    computeBoundsBackward(info.getImage(), picGridView);
                    PhotoActivity.startActivity(PropertyDetailsActivity.this, mThumbViewInfoList, position);
                }
            });
            if (info.getPostComment() != null && info.getPostComment().size() > 0) {
                rlAccess.setVisibility(View.VISIBLE);
                if (info.getPostComment().get(0).getScore() != 0)
                    for (int x = 0; x < 5; x++) {
                        satisfactionList.add(x <= info.getPostComment().get(0).getScore() - 1 ? "1" : "0");
                    }
                txatisfaction.setText(info.getPostComment().get(0).getScore() == 0 ? getResources().getString(R.string.activity_comment_satisfaction1)
                        : info.getPostComment().get(0).getScore() == 1 ? getString(R.string.activity_comment_satisfaction2)
                        : info.getPostComment().get(0).getScore() == 2 ? getString(R.string.activity_comment_satisfaction3)
                        : info.getPostComment().get(0).getScore() == 3 ? getString(R.string.activity_comment_satisfaction4)
                        : getString(R.string.activity_comment_satisfaction5));
                satisfactionAdapter = new SatisfactionAdapter(this, R.layout.item_satisfaction, satisfactionList);
                satisfactionGridView.setAdapter(satisfactionAdapter);
                txAccess.setText(info.getPostComment().get(0).getContent());
            }
        }
    }

    @OnClick({R.id.tx_housekeeper})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_housekeeper:
                ACache aCache = ACache.get(this);
                CallCenterBean bean = (CallCenterBean) aCache.getAsObject("CallCenterBean");
                AndroidUtils.callPhone(this, bean != null && !StringUtils.isEmpty(bean.getServicePhone()) ? bean.getServicePhone()
                        : AppConfig.SERVICEPHONE);
                break;
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onSuccess(BrokenDetailsBean bean) {
    }

    @Override
    public void onPropertyInfo(BrokenDetailsBean bean) {
    }

    @Override
    public void showMsg(String msg) {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PropertyDetailsActivity.this);
        StatService.trackBeginPage(PropertyDetailsActivity.this, "报事详情");
        AnalyUtils.setBAnalyResume(this, "报事详情");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PropertyDetailsActivity.this);
        StatService.trackEndPage(PropertyDetailsActivity.this, "报事详情");
        AnalyUtils.setBAnalyPause(this, "报事详情");
    }

    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     *
     * @param picUrl
     * @param itemGridView
     */
    private void computeBoundsBackward(List<String> picUrl, FillGridView itemGridView) {
        mThumbViewInfoList.clear();
        for (int i = 0; i < picUrl.size(); i++) {
            View itemView = itemGridView.getChildAt(i);
            ThumbViewInfo thumbViewInfo = new ThumbViewInfo(picUrl.get(i));
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
    public void onCall(String phone) {
        houseKeeperPhoneDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }
}

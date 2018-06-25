package com.jinke.community.ui.activity.control;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.FillGridViewBean;
import com.jinke.community.presenter.VisitorsPassPresenter;
import com.jinke.community.ui.adapter.VisitPurposeAdapter;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.VisitorsPassView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/15.
 * 访客通行
 */

public class VisitorsPassActivity extends BaseActivity<VisitorsPassView, VisitorsPassPresenter> {
    @Bind(R.id.fillGridView_purpose)
    FillGridView fillGridViewPurpose;
    @Bind(R.id.fillGridView_date)
    FillGridView fillGridViewDate;
    @Bind(R.id.tx_house_selected)
    TextView houseSelected;
    @Bind(R.id.tx_w_date)
    TextView date;
    @Bind(R.id.tx_w_times)
    TextView times;
    private VisitPurposeAdapter visitPurposeAdapter, visitDateAdapter;
    private List<FillGridViewBean> visitPurposeList = new ArrayList<>();
    private List<FillGridViewBean> visitDateList = new ArrayList<>();

    @Override
    public VisitorsPassPresenter initPresenter() {
        return new VisitorsPassPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_visitors_pass;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_visitors_pass_title));
        showBackwardView(R.string.empty, true);
        setTitleColor(getResources().getColor(R.color.white));
        showBackwardViewIco(R.mipmap.icon_activity_access_arrow_back);
        setTitleBarBgColor(R.color.activity_access_control_title_bg);
        initPurpose();
        initDate();
        initData();
    }

    private void initData() {
        TextUtils.setText(houseSelected, "#41BAE8", "洋房区12-12");
        date.setText("截止本日 23：59：59");
        times.setText("仅限一次");
    }

    private void initDate() {
        visitDateAdapter = new VisitPurposeAdapter(this, visitDateList);
        fillGridViewDate.setAdapter(visitDateAdapter);
        visitDateList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_today), true));
        visitDateList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_tomorrow), false));
        visitDateAdapter.setData(visitDateList);
        fillGridViewDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (FillGridViewBean bean : visitDateList) {
                    bean.setSelecter(false);
                }
                visitDateList.get(position).setSelecter(true);
                visitDateAdapter.setData(visitDateList);
            }
        });
    }

    private void initPurpose() {
        visitPurposeAdapter = new VisitPurposeAdapter(this, visitPurposeList);
        fillGridViewPurpose.setAdapter(visitPurposeAdapter);
        visitPurposeList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_intermediary_showings), true));
        visitPurposeList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_move_off), false));
        visitPurposeList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_delivery), false));
        visitPurposeList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_decoration_release), false));
        visitPurposeList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_housekeeping), false));
        visitPurposeList.add(new FillGridViewBean(getResources().getString(R.string.activity_visitors_pass_friends_visit), false));
        visitPurposeAdapter.setData(visitPurposeList);
        fillGridViewPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (FillGridViewBean bean : visitPurposeList) {
                    bean.setSelecter(false);
                }
                visitPurposeList.get(position).setSelecter(true);
                visitPurposeAdapter.setData(visitPurposeList);
            }
        });
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tx_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_sure:
                startActivity(new Intent(this, ReleasePasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(VisitorsPassActivity.this);
        StatService.trackBeginPage(VisitorsPassActivity.this, "门禁－访客通行");
        AnalyUtils.setBAnalyResume(this, "门禁－访客通行");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(VisitorsPassActivity.this);
        StatService.trackEndPage(VisitorsPassActivity.this, "门禁－访客通行");
        AnalyUtils.setBAnalyPause(this, "门禁－访客通行");
    }
}

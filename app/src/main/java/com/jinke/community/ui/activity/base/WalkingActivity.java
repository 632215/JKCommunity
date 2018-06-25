package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.WorkingSortBean;
import com.jinke.community.presenter.WorkingPresenter;
import com.jinke.community.ui.activity.step.PreferencesUtils;
import com.jinke.community.ui.activity.step.StepData;
import com.jinke.community.ui.adapter.WalkingRangeAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.autotextview.AutofitTextView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IWalkingView;
import com.tencent.stat.StatService;
import com.ypy.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * 健步大比拼界面
 * Created by Administrator on 2017/7/31.
 */

public class WalkingActivity extends BaseActivity<IWalkingView, WorkingPresenter> implements IWalkingView {
    @Bind(R.id.walking_fillListView)
    FillListView walking_fillListView;
    @Bind(R.id.walking_num_txw)
    AutofitTextView walkingNum;
    @Bind(R.id.today_walkingnum_text)
    TextView todayWalkingnumText;
    @Bind(R.id.walking_head_image)
    SimpleDraweeView walkingHeadImage;
    @Bind(R.id.image_head)
    SimpleDraweeView imageHead;
    @Bind(R.id.tx_walking_name)
    TextView txWalkingName;
    @Bind(R.id.tx_walking_range)
    TextView txWalkingRange;
    private List<WorkingSortBean.ListBean> rangeList;
    WalkingRangeAdapter walkingRangeAdapter;

    @Override
    public WorkingPresenter initPresenter() {
        return new WorkingPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_walking;
    }

    @Override
    protected void initView() {
        setTitle(R.string.circle_walking_competition_events);
        showBackwardView(R.string.empty, true);
        EventBus.getDefault().register(this);
        PreferencesUtils preferencesUtils = new PreferencesUtils(this);
        int setup = (int) preferencesUtils.getParam("stepCount", 0);
        txWalkingRange.setText(String.valueOf(setup));
        rangeList = new ArrayList<>();
        walkingRangeAdapter = new WalkingRangeAdapter(this, rangeList);
        walking_fillListView.setAdapter(walkingRangeAdapter);
        showProgressDialog(String.valueOf(true));
        presenter.getWorkingSort();
    }


    public void onEventMainThread(StepData setup) {
        if (txWalkingRange != null) {
            txWalkingRange.setText(setup.getStep());
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onWorkingSort(WorkingSortBean bean) {
        hideDialog();
        walkingRangeAdapter.setData(bean.getList());
        walkingHeadImage.setImageURI(bean.getCurUserStep().getAvatar());
        imageHead.setImageURI(bean.getCurUserStep().getAvatar());
        txWalkingName.setText(bean.getCurUserStep().getNickName());
        walkingNum.setText(bean.getCurUserStep().getRank() + "");
        todayWalkingnumText.setText("您在金科\n" + "全国" + bean.getCountOwner() + "业主中排名");
    }

    @Override
    public void showMsg(String msg) {
        hideDialog();
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(WalkingActivity.this);
        StatService.trackBeginPage(WalkingActivity.this, "统计步数");
        AnalyUtils.setBAnalyResume(this, "统计步数");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(WalkingActivity.this);
        StatService.trackEndPage(WalkingActivity.this, "统计步数");
        AnalyUtils.setBAnalyPause(this, "统计步数");
    }
}


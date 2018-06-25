package com.jinke.community.ui.activity.payment;

import android.view.View;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.presenter.PaymentNotesPresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.PaymentNotesView;
import com.tencent.stat.StatService;

/**
 * Created by Administrator on 2017/8/2.
 * 缴费注意事项
 */

public class PaymentNotesActivity extends BaseActivity<PaymentNotesView,PaymentNotesPresenter> {
    @Override
    public PaymentNotesPresenter initPresenter() {
        return new PaymentNotesPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payment_notes;
    }

    @Override
    protected void initView() {
        setTitle(R.string.payment_notes);
        showBackwardView(R.string.empty,true);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PaymentNotesActivity.this);
        StatService.trackBeginPage(PaymentNotesActivity.this, "缴费－缴费注意事项");
        AnalyUtils.setBAnalyResume(this, "缴费－缴费注意事项");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PaymentNotesActivity.this);
        StatService.trackEndPage(PaymentNotesActivity.this, "缴费－缴费注意事项");
        AnalyUtils.setBAnalyPause(this, "缴费－缴费注意事项");
    }
}

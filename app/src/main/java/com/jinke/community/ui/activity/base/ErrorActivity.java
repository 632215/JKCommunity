package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.utils.AnalyUtils;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ErrorActivity extends BaseActivity {
    @Bind(R.id.tx_error)
    TextView txError;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_error;
    }

    @Override
    protected void initView() {
        txError.setText(getIntent().getStringExtra("error"));
    }

    @OnClick(R.id.img_return)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this,"错误");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this,"错误");
    }
}

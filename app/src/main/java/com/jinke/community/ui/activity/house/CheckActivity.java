package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.utils.AnalyUtils;
import com.tencent.stat.StatService;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * 身份审核状态
 * Created by Administrator on 2018/5/29.
 */

public class CheckActivity extends BaseActivity {
    @Bind(R.id.img_check_state)
    ImageView imgCheckState;
    @Bind(R.id.tx_check_state)
    TextView txCheckState;
    @Bind(R.id.tx_sure)
    TextView txSure;
    private String houseState = "";//N 审核失败activity_check_fail   Y 审核成功 activity_check_success    D审核中activity_checking
    private String houseId = null;//房屋号
    private String houseName = null;//房屋名字
    private String remark = null;//失败的原因

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check;
    }

    @Override
    protected void initView() {
        showBackwardView(R.string.empty, true);
        houseState = getIntent().getStringExtra("houseState");
        houseId = getIntent().getStringExtra("houseId");
        houseName = getIntent().getStringExtra("houseName");
        remark = getIntent().getStringExtra("remark");
        setTitle(R.string.activity_check_title);
        txCheckState.setText(StringUtils.equals("N", houseState) ? R.string.activity_check_fail :
                StringUtils.equals("Y", houseState) ? R.string.activity_check_success : R.string.activity_checking);
        txSure.setText(StringUtils.equals("N", houseState) ? getResources().getString(R.string.activity_button_fail) :
                StringUtils.equals("Y", houseState) ? "返回" : "返回");
        if (StringUtils.equals("N", houseState))
            txCheckState.setText(remark);
        Glide.with(this)
                .load(StringUtils.equals("N", houseState) ? R.mipmap.icon_check_fai :
                        StringUtils.equals("Y", houseState) ? R.mipmap.icon_chech_success : R.mipmap.icon_checking)
                .placeholder(R.mipmap.icon_checking)
                .error(R.mipmap.icon_checking)
                .into(imgCheckState);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tx_sure})
    public void onClick(View view) {
        switch (houseState) {
            case "N":
                startActivity(new Intent(this, AuthenticationActivity.class)
                        .putExtra("houseId", houseId)
                        .putExtra("houseName", houseName)
                        .putExtra("houseState", houseState));
                finish();
                break;
            case "Y":
            case "D":
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(CheckActivity.this);
        StatService.trackBeginPage(CheckActivity.this, "身份审核状态");
        AnalyUtils.setBAnalyResume(this, "身份审核状态");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onResume(CheckActivity.this);
        StatService.trackBeginPage(CheckActivity.this, "身份审核状态");
        AnalyUtils.setBAnalyPause(this, "身份审核状态");
    }
}

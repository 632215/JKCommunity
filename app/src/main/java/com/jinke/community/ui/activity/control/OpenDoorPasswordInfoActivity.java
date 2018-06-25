package com.jinke.community.ui.activity.control;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.PasswordInfo;
import com.jinke.community.presenter.OpenDoorPasswordInfoPresent;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IOpenDoorPasswordInfoView;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorPasswordInfoActivity extends BaseActivity<IOpenDoorPasswordInfoView, OpenDoorPasswordInfoPresent> implements IOpenDoorPasswordInfoView {
    @Bind(R.id.open_door_info_password)
    TextView openDoorInfoPassword;
    @Bind(R.id.open_door_info_location)
    TextView openDoorInfoLocation;
    @Bind(R.id.open_door_info)
    RelativeLayout openDoorInfo;
    @Bind(R.id.open_door_info_address_hint)
    TextView openDoorInfoAddressHint;
    @Bind(R.id.open_door_info_address)
    TextView openDoorInfoAddress;
    @Bind(R.id.open_door_info_aim_hint)
    TextView openDoorInfoAimHint;
    @Bind(R.id.open_door_info_aim)
    TextView openDoorInfoAim;
    @Bind(R.id.open_door_info_valid_time_hint)
    TextView openDoorInfoValidTimeHint;
    @Bind(R.id.open_door_info_valid_time)
    TextView openDoorInfoValidTime;
    @Bind(R.id.open_door_info_times_hint)
    TextView openDoorInfoTimesHint;
    @Bind(R.id.open_door_info_times)
    TextView openDoorInfoTimes;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.tx_sms)
    TextView txSms;
    @Bind(R.id.tx_wechart)
    TextView txWechart;
    @Bind(R.id.tx_qq)
    TextView txQq;

    private String TIME_ID;//时间ID
    private String houseId;//房屋id
    private String purpose;//目的
    private String purposeString;//目的
    private String endTime;//截止时间 yyyyMMddHHmm
    private String type = "0";//限制时间类型 0 ：单次密码 1：截止日期前永久密码
    private PasswordInfo passwordInfo;
    private String openDoorInfoAddressString;
    private String showEndTime = "";

    @Override
    public OpenDoorPasswordInfoPresent initPresenter() {
        return new OpenDoorPasswordInfoPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_door_password_info;
    }

    @Override
    protected void initView() {
        setTitle("通行码");
        showBackwardView("", true);
        initData();
    }

    private void initData() {
        if (null != getIntent().getBundleExtra("passwordInfoBundle")) {
            Bundle passwordInfoBundle = getIntent().getBundleExtra("passwordInfoBundle");
            openDoorInfoAddressString = passwordInfoBundle.getString("HOSE_NAME");
            openDoorInfoAddress.setText(openDoorInfoAddressString);
            houseId = passwordInfoBundle.getString("HOSE_ID");
            purposeString = passwordInfoBundle.getString("REASON");
            openDoorInfoAim.setText(passwordInfoBundle.getString("REASON"));
            purpose = passwordInfoBundle.getString("REASON_ID");
            openDoorInfoValidTime.setText(passwordInfoBundle.getString("TIME"));
            TIME_ID = passwordInfoBundle.getString("TIME_ID");
            openDoorInfoTimes.setText(passwordInfoBundle.getString("DEGREE"));
        }
        getPassWord();
    }

    public void getPassWord() {
        Map<String, String> map = new HashMap<>();
        map.put("tq_uId", MyApplication.getInstance().getControlInfo().getTqUid());
        map.put("houseId", houseId);
        map.put("purpose", purpose);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        endTime = "2".equals(TIME_ID) ? formatter.format(getMonthByIndex(curDate, 1)) : formatter.format(curDate);
        map.put("endTime", endTime + "2359");
        map.put("type", "1");
        presenter.getPassWord(map);
    }


    @OnClick({R.id.tx_sms, R.id.tx_wechart, R.id.tx_qq})
    public void onClick(View view) {
        String sendMessage = getSendMsg();
        if (sendMessage == null) {
            ToastUtils.showShort(this, "验证校验失败，请重新获取");
            return;
        }
        switch (view.getId()) {
            case R.id.tx_sms://短信
                AnalyUtils.addAnaly(10016);
                presenter.sendMessage(sendMessage);
                break;

            case R.id.tx_wechart://微信
                AnalyUtils.addAnaly(10017);
                presenter.shareWeChat(passwordInfo, sendMessage, openDoorInfoAddressString, purposeString, showEndTime, "WEIXIN");
                break;
            case R.id.tx_qq://QQ
                AnalyUtils.addAnaly(10017);
                presenter.shareWeChat(passwordInfo, sendMessage, openDoorInfoAddressString, purposeString, showEndTime, "QQ");
                break;
        }
    }

    public String getSendMsg() {
        String sendMessage = "";
        if (null != passwordInfo && null != passwordInfo.listDate) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            showEndTime = "2".equals(TIME_ID) ? formatter.format(getMonthByIndex(curDate, 1)) : formatter.format(curDate);
            if (passwordInfo.listDate != null && passwordInfo.listDate.size() > 0) {
                sendMessage += openDoorInfoAddressString + "。通行码:" + passwordInfo.listDate.get(0).getPassWord() + ",";
            }
            sendMessage += "有效时间：" + openDoorInfoValidTime.getText().toString().trim();
        }
        return sendMessage;
    }

    public static Date getMonthByIndex(Date bizDate, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bizDate);
        calendar.add(Calendar.DAY_OF_MONTH, i);
        return calendar.getTime();
    }

    @Override
    public void onPasswordInfo(PasswordInfo info) {
        passwordInfo = info;
        loadingLayout.setStatus(LoadingLayout.Success);
        if (info.listDate != null && info.listDate.size() > 0) {
            txSms.setAlpha(StringUtils.equals("密码生成失败", info.listDate.get(0).getPassWord()) ? (float) 0.5 : 1);
            txWechart.setAlpha((StringUtils.equals("密码生成失败", info.listDate.get(0).getPassWord()) ? (float) 0.5 : 1));
            txQq.setAlpha(StringUtils.equals("密码生成失败", info.listDate.get(0).getPassWord()) ? (float) 0.5 : 1);
            txSms.setClickable(StringUtils.equals("密码生成失败", info.listDate.get(0).getPassWord()) ? false : true);
            txWechart.setClickable(StringUtils.equals("密码生成失败", info.listDate.get(0).getPassWord()) ? false : true);
            txQq.setClickable(StringUtils.equals("密码生成失败", info.listDate.get(0).getPassWord()) ? false : true);
            if (1 == passwordInfo.listDate.size()) {
                openDoorInfoLocation.setText(passwordInfo.listDate.get(0).getDoorName());
                openDoorInfoPassword.setText(passwordInfo.listDate.get(0).getPassWord());
                openDoorInfoLocation.setVisibility(View.GONE);
            } else if (1 < passwordInfo.listDate.size()) {
                openDoorInfoLocation.setText(passwordInfo.listDate.get(0).getDoorName());
                openDoorInfoPassword.setText(passwordInfo.listDate.get(0).getPassWord());
                openDoorInfoLocation.setVisibility(View.GONE);
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(OpenDoorPasswordInfoActivity.this);
        StatService.trackBeginPage(OpenDoorPasswordInfoActivity.this, "门禁－密码详情页面");
        AnalyUtils.setBAnalyResume(this, "门禁－密码详情页面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(OpenDoorPasswordInfoActivity.this);
        StatService.trackEndPage(OpenDoorPasswordInfoActivity.this, "门禁－密码详情页面");
        AnalyUtils.setBAnalyPause(this, "门禁－密码详情页面");
    }
}

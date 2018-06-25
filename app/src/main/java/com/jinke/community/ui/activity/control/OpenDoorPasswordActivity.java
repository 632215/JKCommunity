package com.jinke.community.ui.activity.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.GuestCountBean;
import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.OpenDoorPasswordPresenter;
import com.jinke.community.ui.adapter.GuestCountAdapter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.ui.widget.ScrollViewGridView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.IOpenDoorPasswordView;
import com.tencent.stat.StatService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-21.
 * 访客通行
 */

public class OpenDoorPasswordActivity extends BaseActivity<IOpenDoorPasswordView, OpenDoorPasswordPresenter> implements AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener, IOpenDoorPasswordView {

    @Bind(R.id.open_door_choice_house_hint)
    TextView openDoorChoiceHouseHint;
    @Bind(R.id.open_door_choice_house)
    TextView openDoorChoiceHouse;
    @Bind(R.id.open_door_next)
    RelativeLayout openDoorNext;
    @Bind(R.id.open_door_selecter_house)
    LinearLayout openDoorSelecterHouse;
    @Bind(R.id.open_door_choice)
    ScrollViewGridView openDoorChoice;
    @Bind(R.id.open_door_time_today)
    RadioButton openDoorTimeToday;
    @Bind(R.id.open_door_time_tomorrow)
    RadioButton openDoorTimeTomorrow;
    @Bind(R.id.open_door_time)
    RadioGroup openDoorTime;
    @Bind(R.id.open_door_effective_date_hint)
    TextView openDoorEffectiveDateHint;
    @Bind(R.id.open_door_effective_date)
    TextView openDoorEffectiveDate;
    @Bind(R.id.open_door_effective_degree_hint)
    TextView openDoorEffectiveDegreeHint;
    @Bind(R.id.open_door_effective_degree)
    TextView openDoorEffectiveDegree;
    @Bind(R.id.open_door_choice_sure)
    TextView openDoorChoiceSure;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    public final static int REQUEST_CODE = 9211;
    public final static String DOOR_LIST_BUNDLE = "door_list_bundle";
    public final static String DOOR_LIST_BUNDLE_DATE = "door_list_bundle_date";
    public final static String DOOR_LIST_BUNDLE_DATE_NAME = "door_list_bundle_date_name";

    private List<GuestCountBean> guestConuntBeen;
    private GuestCountAdapter guestCountAdapter;

    private String HOSE_NAME;
    private String HOSE_ID;
    private String REASON;
    private String REASON_ID;
    private String TIME;
    private String TIME_ID;
    private String DEGREE = "一次";


    @Override
    public OpenDoorPasswordPresenter initPresenter() {
        return new OpenDoorPasswordPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_door_password;
    }

    @Override
    protected void initView() {
        setTitle("访客通行");
        showBackwardView("", true);
        openDoorTime.setOnCheckedChangeListener(this);


        Calendar curDate = Calendar.getInstance();
        TIME = curDate.get(Calendar.YEAR) + "年" +
                (curDate.get(Calendar.MONTH) + 1) + "月" +
                curDate.get(Calendar.DAY_OF_MONTH) + "日" + "今天23:59";
        TIME_ID = "1";

        Map<String, String> map = new HashMap<>();
        presenter.getPurposeListDate(map);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.open_door_choice_sure, R.id.open_door_choice_house, R.id.open_door_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_door_choice_sure:
                if (null != HOSE_NAME && !"".equals(HOSE_NAME) &&
                        null != HOSE_ID && !"".equals(HOSE_ID) &&
                        null != REASON && !"".equals(REASON) &&
                        null != REASON_ID && !"".equals(REASON_ID) &&
                        null != TIME && !"".equals(TIME) &&
                        null != TIME_ID && !"".equals(TIME_ID) &&
                        null != DEGREE && !"".equals(DEGREE)) {
                    Intent passwordInfoIntent = new Intent(OpenDoorPasswordActivity.this, OpenDoorPasswordInfoActivity.class);
                    Bundle passwordInfoBundle = new Bundle();
                    passwordInfoBundle.putString("HOSE_NAME", HOSE_NAME);
                    passwordInfoBundle.putString("HOSE_ID", HOSE_ID);
                    passwordInfoBundle.putString("REASON", REASON);
                    passwordInfoBundle.putString("REASON_ID", REASON_ID);
                    passwordInfoBundle.putString("TIME", TIME);
                    passwordInfoBundle.putString("TIME_ID", TIME_ID);
                    passwordInfoBundle.putString("DEGREE", DEGREE);
                    passwordInfoIntent.putExtra("passwordInfoBundle", passwordInfoBundle);
                    AnalyUtils.addAnaly(10015);
                    startActivity(passwordInfoIntent);
                    finish();
                } else {
                    ToastUtils.showLong(OpenDoorPasswordActivity.this, "请完善信息");
                }
                break;

            case R.id.open_door_choice_house:
            case R.id.open_door_next:
                Map<String, String> map = new HashMap<>();
                presenter.getHouseList(map);
                break;
        }
    }

    @Override
    public void getHouseListDateNext(HouseListInfo info) {
        if (info != null && info.getListDate() != null) {
            if (info.getListDate().size() > 0) {
                AppConfig.trackCustomEvent(this, "open_door_next_click", "门禁－生成访客密码");
                Intent doorListIntent = new Intent(OpenDoorPasswordActivity.this, DoorHouseListActivity.class);
                startActivityForResult(doorListIntent, REQUEST_CODE);
            } else {
                ToastUtils.showShort(this, "当前房屋尚未绑定门禁！");
            }
        }
    }

    @Override
    public void getHouseListDateError() {
        ToastUtils.showShort(this, "当前房屋尚未绑定门禁！");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                final Bundle bundle = data.getBundleExtra(DOOR_LIST_BUNDLE);
                if (null != bundle) {
                    if (null != bundle.getString(DOOR_LIST_BUNDLE_DATE) && !"success".equals(bundle.getString(DOOR_LIST_BUNDLE_DATE))) {
                        HOSE_NAME = bundle.getString(DOOR_LIST_BUNDLE_DATE_NAME);
                        HOSE_ID = bundle.getString(DOOR_LIST_BUNDLE_DATE);
                        openDoorChoiceHouse.setText(HOSE_NAME);
                    }
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Calendar curDate = Calendar.getInstance();
        switch (checkedId) {
            case R.id.open_door_time_today:
                TIME = curDate.get(Calendar.YEAR) + "年" +
                        (curDate.get(Calendar.MONTH) + 1) + "月" +
                        curDate.get(Calendar.DAY_OF_MONTH) + "日" + "今天23:59";
                TIME_ID = "1";
                openDoorEffectiveDate.setText(TIME);
                break;

            case R.id.open_door_time_tomorrow:
                TIME = curDate.get(Calendar.YEAR) + "年" +
                        (curDate.get(Calendar.MONTH) + 1) + "月" +
                        (curDate.get(Calendar.DAY_OF_MONTH) + 1) + "日" + "明天23:59";
                TIME_ID = "2";
                openDoorEffectiveDate.setText(TIME);
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(this, msg);
        loadingLayout.setStatus(LoadingLayout.No_Network);
    }

    @Override
    public void onReasonBean(final List<GuestCountBean> listBean) {
        this.guestConuntBeen = listBean;
        loadingLayout.setStatus(LoadingLayout.Success);
        if (guestConuntBeen.size() > 0) {
            REASON = guestConuntBeen.get(0).getName();
            REASON_ID = guestConuntBeen.get(0).getId();
        }

        guestCountAdapter = new GuestCountAdapter(OpenDoorPasswordActivity.this, listBean);
        openDoorChoice.setAdapter(guestCountAdapter);
        openDoorChoice.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (GuestCountBean bean : guestConuntBeen) {
            bean.setIsshow(false);
        }
        guestConuntBeen.get(position).setIsshow(true);
        REASON = guestConuntBeen.get(position).getName();
        REASON_ID = guestConuntBeen.get(position).getId();
        guestCountAdapter.setData(guestConuntBeen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(10013);
        StatService.onResume(OpenDoorPasswordActivity.this);
        StatService.trackBeginPage(OpenDoorPasswordActivity.this, "门禁－访客通行");
        AnalyUtils.setBAnalyResume(this, "门禁－访客通行");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(OpenDoorPasswordActivity.this);
        StatService.trackEndPage(OpenDoorPasswordActivity.this, "门禁－访客通行");
        AnalyUtils.setBAnalyPause(this, "门禁－访客通行");
    }
}

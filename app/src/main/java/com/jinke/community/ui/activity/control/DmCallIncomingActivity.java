package com.jinke.community.ui.activity.control;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.doormaster.vphone.config.DMCallState;
import com.doormaster.vphone.inter.DMModelCallBack;
import com.doormaster.vphone.inter.DMVPhoneModel;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.utils.AnalyUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class DmCallIncomingActivity extends BaseActivity {
    @Bind(R.id.video_call_head)
    ImageView videoCallHead;
    @Bind(R.id.video_call_title)
    TextView callTitle;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dm_call_incoming;
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);
//        callTitle.setText(DMVPhoneModel.getDisplayName(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        DMVPhoneModel.addCallStateListener(mListener);
        AnalyUtils.setBAnalyResume(this, "通话界面");
    }

    @Override
    protected void onPause() {
        DMVPhoneModel.removeCallStateListener(mListener);
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "通话界面");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            decline();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.video_call_connect, R.id.video_call_hang})
    public void callIncomingOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.video_call_connect:
                answer();
                break;
            case R.id.video_call_hang:
                decline();
                break;
        }
    }


    private void decline() {
        DMVPhoneModel.refuseCall();
        finish();
    }

    private void answer() {
        DMVPhoneModel.answerCall();
        Intent intent = new Intent(DmCallIncomingActivity.this, YJCallActivity.class);
        startActivity(intent);
        finish();
    }

    private DMModelCallBack.DMCallStateListener mListener = new DMModelCallBack.DMCallStateListener() {
        @Override
        public void callState(DMCallState state, String message) {
            if (DMCallState.CallEnd == state) {
                finish();
            }
            if (state == DMCallState.StreamsRunning) {
                DMVPhoneModel.enableSpeaker(DMVPhoneModel.isSpeakerEnable());
            }
        }

    };

}


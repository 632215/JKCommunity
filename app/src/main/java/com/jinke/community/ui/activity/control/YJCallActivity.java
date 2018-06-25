package com.jinke.community.ui.activity.control;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doormaster.vphone.config.DMCallState;
import com.doormaster.vphone.inter.DMModelCallBack;
import com.doormaster.vphone.inter.DMPhoneMsgListener;
import com.doormaster.vphone.inter.DMVPhoneModel;
import com.jinke.community.R;
import com.jinke.community.utils.AnalyUtils;
import com.lidroid.xutils.util.LogUtils;

public class YJCallActivity extends Activity implements View.OnClickListener {

    private static String TAG = "DmCallActivity";

    private LinearLayout mTalkingLayout = null;

    private TextView textViewCallState = null;
    private TextView textViewCountDown = null;

    private SurfaceView videoView;
    private SurfaceView videoCapture;


    private ImageView videoCome;
    private ImageView videoForbid;
    private TextView callTitle;
    private TextView callTime;
    private boolean isSpeakerEnabled = true;
    private Dialog lodingDialog;
    private String showStr = "00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_yjcall);

        DMVPhoneModel.addCallStateListener(callStateListener);

        DMVPhoneModel.addMsgListener(new DMPhoneMsgListener() {
            @Override
            public void messageReceived(String msg) {
                Log.d(TAG, "msg=" + msg);
                Toast.makeText(YJCallActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void dtmfMsgReceived(int dtmf) {
                Toast.makeText(YJCallActivity.this, String.valueOf(dtmf), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void controlMsgReceived(int command, String msg) {
                Log.d(TAG, "msg=" + msg);
                Toast.makeText(YJCallActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


        videoView = (SurfaceView) findViewById(R.id.video_surface);
        videoCapture = (SurfaceView) findViewById(R.id.video_capture_surface);
        videoCome = (ImageView) findViewById(R.id.video_call_come);
        videoForbid = (ImageView) findViewById(R.id.video_call_forbid);
//        callTitle = (TextView) findViewById(R.id.video_call_title);
        callTime = (TextView) findViewById(R.id.video_call_time);

        videoCome.setOnClickListener(this);
        videoForbid.setOnClickListener(this);

//        callTitle.setText(DMVPhoneModel.getDisplayName(this));
        DMVPhoneModel.enableSpeaker(isSpeakerEnabled);
        communicateDownTimer.start();
        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);
        DMVPhoneModel.fixZOrder(videoView, videoCapture);
    }

    @Override
    protected void onResume() {
        DMVPhoneModel.onVideoResume();
        DMVPhoneModel.addCallStateListener(callStateListener);
        super.onResume();
        AnalyUtils.setBAnalyResume(this, "西墨呼叫");
    }

    @Override
    protected void onPause() {
        DMVPhoneModel.onVideoPause();
        DMVPhoneModel.removeCallStateListener(callStateListener);
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "西墨呼叫");
    }

    @Override
    protected void onDestroy() {
        DMVPhoneModel.onVideoDestroy();
        communicateDownTimer.onFinish();
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /*
         *
         * adjust Ring volumn
         *
         * Volumn + KeyEvent.KEYCODE_2
         *
         * Volumn - KeyEvent.KeyCode_8
         *
         * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) || keyCode == KeyEvent.KEYCODE_POUND) {
            decline();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decline() {
        DMVPhoneModel.refuseCall();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_call_forbid:
                initDialog(R.layout.video_call_forbid);
                lodingDialog.show();
                DMVPhoneModel.refuseCall();
                break;
            case R.id.video_call_come:
                initDialog(R.layout.video_call_come);
                lodingDialog.show();
                DMVPhoneModel.openDoor();
                DMVPhoneModel.refuseCall();
                break;
        }
    }
    private DMModelCallBack.DMCallStateListener callStateListener = new DMModelCallBack.DMCallStateListener() {
        @Override
        public void callState(DMCallState state, String message) {
            Log.d("CallStateLis calling", "value=" + state.value() + ",message=" + message);
            if (state == DMCallState.Connected || state == DMCallState.OutgoingRinging) {
                mTalkingLayout.setVisibility(View.VISIBLE);
                textViewCallState.setText("通话中…");
            } else if (state == DMCallState.StreamsRunning) {
            } else {
                communicateDownTimer.cancel();
                if (state == DMCallState.Error) {
                    //通话结束
                    countDownTimer.start();
                } else if (state == DMCallState.CallEnd) {
                    //通话结束
                    countDownTimer.start();
                }
            }
        }
    };

    private void initDialog(int srcId) {
        lodingDialog = new Dialog(this);
        lodingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = lodingDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        lp.y = (int) getResources().getDimension(R.dimen.base_dimen_278); // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        lodingDialog.setContentView(srcId);
        lodingDialog.setCanceledOnTouchOutside(false);
    }

    private void destroyDialog() {
        if (null != lodingDialog && lodingDialog.isShowing()) {
            lodingDialog.dismiss();
        }
    }

    private CountDownTimer countDownTimer = new CountDownTimer(3000, 3000) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            destroyDialog();
            finish();
        }
    };

    private CountDownTimer communicateDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程
            showStr = "";
            long millis = 60 - millisUntilFinished / 1000;
            long minute = millis / 60;
            long second = millis % 60;
            if ( minute < 10) {
                showStr = "0" + minute + ":";
            } else {
                showStr = minute + ":";
            }
            if ( second < 10) {
                showStr += "0" + second ;
            } else {
                showStr += second + "";
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callTime.setText(showStr);
                }
            });
        }

        @Override
        public void onFinish() {
        }
    };

}

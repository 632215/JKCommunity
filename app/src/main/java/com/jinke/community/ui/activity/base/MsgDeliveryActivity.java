package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.presenter.MsgCenterPresenter;
import com.jinke.community.ui.activity.control.OpenDoorActivity;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.utils.TextUtils;
import com.tencent.stat.StatService;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/6/7.
 */

public class MsgDeliveryActivity extends BaseActivity {
    @Bind(R.id.tx_state)
    TextView txState;
    @Bind(R.id.img_state)
    ImageView imgState;
    @Bind(R.id.tx_number)
    TextView txNumber;
    @Bind(R.id.tx_address)
    TextView txAddress;
    @Bind(R.id.tx_time)
    TextView txTime;
    @Bind(R.id.tx_company)
    TextView txCompany;
    @Bind(R.id.tx_id)
    TextView txId;
    @Bind(R.id.tx_person)
    TextView txPerson;
    @Bind(R.id.tx_phone)
    TextView txPhone;

    private MsgBean.ListBean postBean = null;
    private MsgBean.ListBean.ExtrasBean bean = null;

    @Override
    public MsgCenterPresenter initPresenter() {
        return new MsgCenterPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_msg_delivery;
    }

    @Override
    protected void initView() {
        showBackwardView("", true);
        postBean = (MsgBean.ListBean) getIntent().getExtras().get("bean");
        initData();
    }

    private void initData() {
        if (postBean == null) {
            ToastUtils.showShort(this, "数据为空");
            return;
        }
        bean = postBean.getExtras().get(postBean.getExtras().size() - 1);
        if (bean != null) {
            setTitle(postBean.getTitle());
            switch (postBean.getType()) {
                case "POSTMAN_POST": //POSTMAN_POST 快递员将包裹投递入柜
                    Glide.with(this).load(R.mipmap.icon_msg_wait).into(imgState);
                    DrawableUtils.setDrawableRight(this, txState, R.mipmap.icon_msg_delivery_wait);
                    txNumber.setText(getText(R.string.activity_msgcenter_num) + postBean.getExtras().get(postBean.getExtras().size() - 1).getOpen_code());
                    break;

                case "GETTER_TAKE": //收件人取走包裹
                case "POSTMAN_TAKE": //快递员将包裹取回
                case "ADMIN_TAKE": //柜子管理员(值守)将包裹取回
                    Glide.with(this).load(R.mipmap.icon_msg_success).into(imgState);
                    DrawableUtils.setDrawableRight(this, txState, R.mipmap.icon_msg_delivery_success);
                    txNumber.setText(getText(R.string.activity_msgcenter_already));
                    break;
            }
            txAddress.setText(bean.getLocation());
            txTime.setText(getText(R.string.activity_msgcenter_post_time) + TextUtils.intToString(bean.getCharge_begin_time(), "yyyy年MM月dd HH:mm"));
            txCompany.setText(getText(R.string.activity_msgcenter_post_company) + bean.getExpress_name());
            txId.setText(getText(R.string.activity_msgcenter_post_num) + bean.getMail_no());
            txPhone.setText(getText(R.string.activity_msgcenter_post_phone) + bean.getPost_phone());
            txPerson.setText(getText(R.string.activity_msgcenter_post_man) + bean.getPostman());
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MsgDeliveryActivity.this);
        StatService.trackBeginPage(MsgDeliveryActivity.this, "快递消息");
        AnalyUtils.setBAnalyResume(this, "快递消息");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onResume(MsgDeliveryActivity.this);
        StatService.trackBeginPage(MsgDeliveryActivity.this, "快递消息");
        AnalyUtils.setBAnalyPause(this, "快递消息");
    }
}

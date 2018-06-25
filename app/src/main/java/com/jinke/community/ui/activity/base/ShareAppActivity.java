package com.jinke.community.ui.activity.base;

import android.view.View;
import android.widget.RelativeLayout;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.ui.toast.ShareMethodWindow;
import com.jinke.community.utils.AnalyUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/3/24.
 */

public class ShareAppActivity extends BaseActivity implements ShareMethodWindow.OnMethodSelectedListener, UMShareListener {
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;

    private ShareMethodWindow shareMethodWindow;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_share_app;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_share_app_title));
        showBackwardView("", true);
        showForwardView(getString(R.string.activity_share), true);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        if (shareMethodWindow == null)
            shareMethodWindow = new ShareMethodWindow(this);
        shareMethodWindow.showPopWindow(rlRoot);
        shareMethodWindow.setListener(this);
    }

    @Override
    public void method(String relationship) {
        SHARE_MEDIA share_media = null;
        share_media = StringUtils.equals("1", relationship) ? SHARE_MEDIA.WEIXIN :
                StringUtils.equals("2", relationship) ? SHARE_MEDIA.WEIXIN_CIRCLE :
                        StringUtils.equals("3", relationship) ? SHARE_MEDIA.QQ : SHARE_MEDIA.QZONE;
        if (share_media != null)
            new ShareAction(this)
                    .setPlatform(share_media)
                    .withTitle("大社区APP下载二维码")
                    .withText("业主专项APP，更多便捷服务等你来体验，欢迎下载...")
                    .withTargetUrl("http://www.tq-service.com/jkpros/download/index.html")
                    .setCallback(this)
                    .share();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtils.showShort(this, "分享成功！");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtils.showShort(this, "分享失败！");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this,"分享");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this,"分享");
    }
}

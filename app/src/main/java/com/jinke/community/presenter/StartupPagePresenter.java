package com.jinke.community.presenter;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.ThemeBean;
import com.jinke.community.service.StartupPageBiz;
import com.jinke.community.service.impl.StartupPageImpl;
import com.jinke.community.service.listener.StartupPageListener;
import com.jinke.community.ui.activity.base.GuidePageActivity;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.utils.ThemeUtils;
import com.jinke.community.view.StartupPageView;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-7-22.
 */

public class StartupPagePresenter extends BasePresenter<StartupPageView> implements StartupPageListener {
    private Context mContext;
    private StartupPageBiz mBiz;

    public StartupPagePresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new StartupPageImpl(mContext);
    }

    /**
     * 启动过渡动画
     *
     * @param startup
     */
    public void stratAnminatoin(RelativeLayout startup) {
        if (mView != null) {
            AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
            animation.setDuration(1500);
            startup.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation arg0) {
                    if (mView != null) {
                        BaseUserBean baseUserBean = MyApplication.getBaseUserBean();
                        mView.startActivity(StringUtils.isEmpty(baseUserBean.getAccessToken()) ? GuidePageActivity.class : MainActivity.class);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }
            });
        }
    }

    //检查主题
    public void getTheme() {
        ThemeUtils.getThemeIcon(mContext);
//        SharedPreferencesUtils.setIconState(mContext, false);
    }

    /**
     * 检查主题回调
     *
     * @param info
     */
    @Override
    public void getThemeNext(ThemeBean info) {

    }

    @Override
    public void onError(String code, String msg) {

    }
}

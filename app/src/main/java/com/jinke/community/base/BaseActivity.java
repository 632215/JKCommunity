package com.jinke.community.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.baidu.mobstat.StatService;
import com.jinke.community.ui.toast.HouseKeeperPhoneDialog;
import com.jinke.community.ui.toast.dialog.SpotsDialog;
import com.jinke.community.utils.AppManager;
import com.jinke.community.utils.StatusBarUtils;

import org.loader.autohideime.HideIMEUtil;

import butterknife.ButterKnife;
import www.jinke.com.library.utils.commont.NetWorksUtils;

/**
 * Created by root on 17-6-13.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends TitleActivity {
    public T presenter;
    public boolean isConnected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = NetWorksUtils.isConnected(this);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        AppManager.addActivity(this);
        HideIMEUtil.wrap(this);
        presenter = initPresenter();
        if (null != presenter)
            presenter.attach((V) this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != presenter)
            presenter.attach((V) this);
    }


    @Override
    protected void onDestroy() {
        if (null != presenter)
            presenter.dettach();
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //监听用户点击屏幕事件，进行拦截
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            //判断当前获取的焦点是否在Editext上面
            if (StatusBarUtils.isShouldHideInput(view, ev)) {
                hideSoftInput(view.getWindowToken());//关闭软键盘
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化Presenter
     *
     * @return
     */
    public abstract T initPresenter();

    //布局文件ID
    protected abstract int getContentViewId();

    protected abstract void initView();

    public SpotsDialog progressDialog;

    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new SpotsDialog(this);
        }
        if (msg != null) {
            progressDialog.setCanceledOnTouchOutside(Boolean.valueOf(msg));
        }
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

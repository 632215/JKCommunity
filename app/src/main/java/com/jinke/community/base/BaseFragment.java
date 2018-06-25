package com.jinke.community.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.jinke.community.R;
import com.jinke.community.ui.toast.dialog.SpotsDialog;

import butterknife.ButterKnife;
import www.jinke.com.library.utils.commont.NetWorksUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;


/**
 * Created by root on 17-7-7.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    protected BaseActivity mActivity;
    public T presenter;
    private FrameLayout layoutContent;
    private TextView textTitle;
    private TextView buttonBackward;
    private TextView buttonForward;
    private TextView titleLine;
    private RelativeLayout layout_titlebar;


    //获取布局文件ID
    protected abstract int getLayoutId();

    public boolean isConnected = false;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isConnected = NetWorksUtils.isConnected(getActivity());
        View view = inflater.inflate(R.layout.fragment_base_title, container, false);
        initChild(view, inflater);
        ButterKnife.bind(this, view);

        presenter = initPresenter();
        if (null != presenter)
            presenter.attach((V) this);
        initView(view, savedInstanceState);
        return view;
    }

    protected void initChild(View view, LayoutInflater inflater) {
        textTitle = (TextView) view.findViewById(R.id.text_title);
        titleLine = (TextView) view.findViewById(R.id.layout_title_bar_line);
        layout_titlebar = (RelativeLayout) view.findViewById(R.id.layout_titlebar);
        layoutContent = (FrameLayout) view.findViewById(R.id.layout_content);
        layoutContent.removeAllViews();
        View child = inflater.inflate(getLayoutId(), null);
        layoutContent.addView(child);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != presenter)
            presenter.dettach();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this != null) {
            StatService.onResume(getActivity());
            StatService.onPageStart(getActivity(), String.valueOf(getActivity().getTitle()));
        }
        if (null != presenter)
            presenter.attach((V) this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this != null) {
            StatService.onResume(getActivity());
            StatService.onPageEnd(getActivity(), String.valueOf(getActivity().getTitle()));
        }
    }

    public abstract T initPresenter();

    public void showToast(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showLong(getActivity(), msg);
    }

    /**
     * 隐藏标题栏线
     */
    protected void hindTitleLine(int visibility) {
        titleLine.setVisibility(visibility);
    }

    /**
     * 设置标题栏背景色
     *
     * @param titleBarBgColor
     */
    protected void setTitleBarBgColor(int titleBarBgColor) {
        layout_titlebar.setBackgroundResource(titleBarBgColor);
    }

    /**
     * 设置标题内容
     *
     * @param titleId
     */
    public void setTitle(String titleId) {
        layout_titlebar.setVisibility(View.VISIBLE);
        textTitle.setText(titleId);
    }

    public SpotsDialog progressDialog;

    public void showProgressDialog(String msg) {
        progressDialog = new SpotsDialog(getActivity());
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

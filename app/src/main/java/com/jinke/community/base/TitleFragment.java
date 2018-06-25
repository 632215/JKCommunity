package com.jinke.community.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;

/**
 * Created by root on 17-8-10.
 */

public class TitleFragment extends Fragment {
    private TextView textTitle;
    private TextView buttonBackward;
    private TextView buttonForward;
    private TextView titleLine;
    private RelativeLayout layout_titlebar;
    private FrameLayout layoutContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_title, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        textTitle = (TextView) view.findViewById(R.id.text_title);
        titleLine = (TextView) view.findViewById(R.id.layout_title_bar_line);
        layoutContent = (FrameLayout) view.findViewById(R.id.layout_content);
        buttonBackward = (TextView) view.findViewById(R.id.button_backward);
        layout_titlebar = (RelativeLayout) view.findViewById(R.id.layout_titlebar);
        buttonForward = (TextView) view.findViewById(R.id.button_forward);
        buttonBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackward(v);
            }
        });
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForward(v);
            }
        });
    }


    /**
     * 隐藏标题栏线
     */
    protected void hindTitleLine() {
        titleLine.setVisibility(View.GONE);
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
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (buttonBackward != null) {
            if (show) {
                buttonBackward.setText(backwardResid);
                buttonBackward.setVisibility(View.VISIBLE);
            } else {
                buttonBackward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 返回按钮显示图标
     *
     * @param backwardResid 显示图标
     */
    protected void showBackwardViewIco(int backwardResid) {
        buttonBackward.setVisibility(View.VISIBLE);
        buttonBackward.setText("");
        buttonBackward.setCompoundDrawablesRelativeWithIntrinsicBounds(backwardResid, 0, 0, 0);
    }

    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(String backwardResid, boolean show) {
        if (buttonBackward != null) {
            if (show) {
                buttonBackward.setText(backwardResid);
                buttonBackward.setVisibility(View.VISIBLE);
            } else {
                buttonBackward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置返回按钮字颜色
     *
     * @param viewColor
     */
    protected void showBackwardViewColor(int viewColor) {
        buttonBackward.setTextColor(viewColor);
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     * @param show         true则显示
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (buttonForward != null) {
            if (show) {
                buttonForward.setVisibility(View.VISIBLE);
                buttonForward.setText(forwardResId);
            } else {
                buttonForward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     * @param show         true则显示
     */
    protected void showForwardView(String forwardResId, boolean show) {
        if (buttonForward != null) {
            if (show) {
                buttonForward.setVisibility(View.VISIBLE);
                buttonForward.setText(forwardResId);
            } else {
                buttonForward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置提交按钮字颜色
     *
     * @param viewColor
     */
    protected void showForwardViewColor(int viewColor) {
        buttonForward.setTextColor(viewColor);
    }

    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    protected void onForward(View forwardView) {
    }

    /**
     * 设置标题内容
     *
     * @param titleId
     */
    public void setTitle(int titleId) {
        layout_titlebar.setVisibility(View.VISIBLE);
        textTitle.setText(titleId);
    }

    /**
     * 设置标题内容
     *
     * @param textColor
     * @Override public void setTitle(CharSequence title) {
     * layout_titlebar.setVisibility(View.VISIBLE);
     * textTitle.setText(title);
     * }
     * <p>
     * /**
     * 设置标题文字颜色
     */
    public void setTitleColor(int textColor) {
        textTitle.setTextColor(textColor);
    }

}

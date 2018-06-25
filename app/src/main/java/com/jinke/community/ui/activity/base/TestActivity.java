package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.presenter.MainPresenter;

import butterknife.Bind;
import www.jinke.com.charmhome.application.LuMiConfig;
import www.jinke.com.charmhome.ui.activity.StartPageActivity;

/**
 * Created by root on 17-7-26.
 */

public class TestActivity extends BaseActivity {
    @Bind(R.id.hello)
    Button hello;

    @Override
    public MainPresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }


    @Override
    public void initView() {
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuMiConfig.ACCOUNT = "18315058036";
                startActivity(new Intent(TestActivity.this, StartPageActivity.class));
            }
        });
    }
}

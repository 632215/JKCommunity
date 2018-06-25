package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.ui.adapter.ViewPagerAdatper;
import com.jinke.community.ui.toast.banner.PageFrameLayout;
import com.jinke.community.utils.AnalyUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by root on 17-8-18.
 */

public class GuidePageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.contentFrameLayout)
    PageFrameLayout pageFrameLayout;
    @Bind(R.id.in_viewpager)
    ViewPager viewPager;

    private ViewPagerAdatper viewPagerAdatper;
    private List<View> list;
    private TextView txOk;

    @Override

    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        list = new ArrayList<>();
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.page_tab1, null);
        View view2 = lf.inflate(R.layout.page_tab2, null);
        View view3 = lf.inflate(R.layout.page_tab3, null);
        list.add(view1);
        list.add(view2);
        list.add(view3);
        viewPagerAdatper = new ViewPagerAdatper(list);
        viewPager.setAdapter(viewPagerAdatper);
        viewPager.setCurrentItem(0);
        txOk = (TextView) list.get(2).findViewById(R.id.tx_ok);
        txOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidePageActivity.this, LoginActivity.class));
            }
        });
        viewPager.setOnPageChangeListener(this);   //监听ViewPager
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GuidePageActivity.this);
        StatService.trackBeginPage(GuidePageActivity.this, "引导页");
        AnalyUtils.setBAnalyResume(this, "引导页");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GuidePageActivity.this);
        StatService.trackEndPage(GuidePageActivity.this, "引导页");
        AnalyUtils.setBAnalyPause(this, "引导页");
    }
}

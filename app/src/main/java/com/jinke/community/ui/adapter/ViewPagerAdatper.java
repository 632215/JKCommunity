package com.jinke.community.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ViewPagerAdatper extends PagerAdapter {
    private List<View> mViewList;

    public ViewPagerAdatper(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}

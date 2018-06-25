package com.jinke.community.service;

import com.jinke.community.presenter.LifePresenter;
import com.jinke.community.service.listener.ILifListener;
import com.lidroid.xutils.http.RequestParams;

import java.util.Map;

/**
 * Created by root on 17-8-16.
 */

public interface ILifBiz {
    /**
     * 获取顶部广告列表
     * @param map
     * @param listener
     */
    void getTopAdvertising(Map<String,String> map, ILifListener listener);


    /**
     * 获取导航栏一列表
     * @param map
     * @param listener
     */
    void getTopNavigationOne(Map<String,String> map, ILifListener listener);

    /**
     * 获取导航栏二的列表
     * @param map
     * @param listener
     */
    void getTopNavigationTwo(Map<String,String> map, ILifListener listener);

    /**
     * 获取导航栏二的列表
     * @param map
     * @param listener
     */
    void getRanking(Map<String,String> map, ILifListener listener);


    /**
     * 获取底部活动列表
     * @param map
     * @param listener
     */
    void getBottomActivity(Map<String,String> map, ILifListener listener);
}

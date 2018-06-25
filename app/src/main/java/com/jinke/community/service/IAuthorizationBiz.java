package com.jinke.community.service;

import com.jinke.community.presenter.AuthorizationPresenter;
import com.jinke.community.service.listener.IAuthorizationListener;

import java.util.Map;

/**
 * Created by root on 17-8-7.
 */

public interface IAuthorizationBiz {
    /**
     * 添加房屋授权
     * @param map
     * @param listener
     */
    void getaddAuthorization(Map<String,String> map,IAuthorizationListener listener);

    void getHouseListData(Map map,IAuthorizationListener listener);

    /**
     * 删除授权
     * @param map
     * @param listener
     */
    void deleteAuthorization(Map<String, String> map, IAuthorizationListener listener);
}

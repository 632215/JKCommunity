package com.jinke.community.service;

import com.jinke.community.http.main.OnRequestListener;

import java.util.Map;

/**
 * Created by root on 17-8-16.
 */

public interface ISuggestionsBiz {
    /**
     * 意见反馈
     *
     * @param map
     * @param listener
     */
    void getAddSuggestData(Map<String, String> map, OnRequestListener listener);
}

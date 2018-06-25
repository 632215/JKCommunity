package com.jinke.community.service;

import com.jinke.community.service.listener.CommentListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface CommentBiz {
    void addPostItComments(Map map, CommentListener listener);
}

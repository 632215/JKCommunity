package com.jinke.community.service.listener;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface CommentListener {
    void addPostItCommentsNext();

    void onError(String code, String msg);
}

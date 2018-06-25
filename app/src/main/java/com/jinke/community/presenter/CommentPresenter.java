package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.service.CommentBiz;
import com.jinke.community.service.impl.CommentImpl;
import com.jinke.community.service.listener.CommentListener;
import com.jinke.community.view.CommentView;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */

public class CommentPresenter extends BasePresenter<CommentView> implements CommentListener {
    private Context mContext;
    private CommentBiz mBiz;

    public CommentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new CommentImpl(mContext);
    }

    /**
     * 发表评论
     *
     * @param map
     */
    public void addPostItComments(Map map) {
        if (mView != null) {
            mView.showDiaog();
            mBiz.addPostItComments(map, this);
        }
    }

    @Override
    public void addPostItCommentsNext() {
        if (mView != null) {
            mView.hideDiaog();
            mView.addPostItCommentsNext();
        }
    }

    @Override
    public void onError(String code, String msg) {
        mView.hideDiaog();
    }
}

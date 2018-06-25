package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.service.impl.SuggestionsImpl;
import com.jinke.community.view.ISuggestionsView;

import java.util.Map;

/**
 * Created by root on 17-8-16.
 */

public class SuggestionsPresenter extends BasePresenter<ISuggestionsView> implements OnRequestListener<EmptyObjectBean> {

    private Context mContext;
    private SuggestionsImpl suggestions;

    public SuggestionsPresenter(Context mContext) {
        this.mContext = mContext;
        suggestions = new SuggestionsImpl(mContext);
    }

    public void getAddSuggest(Map<String, String> map) {
        if (mView != null) {
            suggestions.getAddSuggestData(map, this);
            mView.showLoading();
        }


    }

    @Override
    public void onSuccess(EmptyObjectBean bean) {
        if (mView != null) {
            mView.onSuccess(bean);
            mView.hindLoading();
        }

    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
            mView.hindLoading();
        }

    }
}

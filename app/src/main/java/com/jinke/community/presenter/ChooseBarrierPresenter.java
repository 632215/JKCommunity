package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.service.ChooseBarrierBiz;
import com.jinke.community.service.impl.ChooseBarrierImpl;
import com.jinke.community.service.listener.ChooseBarrierListener;
import com.jinke.community.view.ChooseBarrierView;

/**
 * Created by Administrator on 2017/10/10.
 */

public class ChooseBarrierPresenter extends BasePresenter<ChooseBarrierView> implements ChooseBarrierListener {
    private Context mContext;
    private ChooseBarrierBiz chooseBarrierBiz;

    public ChooseBarrierPresenter(Context mContext) {
        this.mContext = mContext;
        chooseBarrierBiz = new ChooseBarrierImpl(mContext);
    }


}

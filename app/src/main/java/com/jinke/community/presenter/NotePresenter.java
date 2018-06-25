package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.NoteBean;
import com.jinke.community.service.NoteBiz;
import com.jinke.community.service.impl.NoteImpl;
import com.jinke.community.service.listener.NoteListener;
import com.jinke.community.view.NoteView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/3/23.
 */

public class NotePresenter extends BasePresenter<NoteView> implements NoteListener {
    private Context mContext;
    private NoteBiz mNoteBiz;

    public NotePresenter(Context mContext) {
        this.mContext = mContext;
        mNoteBiz = new NoteImpl(mContext);
    }

    //获取标签
    //accessToken	是	string	用户accessToken
    //typeCode	    是	string	标签分类code,当前 技能skill 兴趣interest
    public void getNote(String type) {
        Map map = new HashMap();
        try {
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("typeCode", type);
        } catch (Exception e) {
            ToastUtils.showShort(mContext, "数据为空！");
        }
        if (mView != null && mNoteBiz != null) {
            mNoteBiz.getNote(map, this);
            mView.showLoading();
        }
    }

    /**
     * 获取标签成功回调
     *
     * @param info
     */
    @Override
    public void getNoteNext(NoteBean info) {
        if (info != null && mView != null) {
            mView.getNoteNext(info);
            mView.hideLoading();
        }
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.onErrorMsg(msg);
            mView.hideLoading();
        }
    }

    //设置选择的标签
    public void setNote(List<NoteBean.ListBean> mList) {
        if (mNoteBiz != null && mView != null) {
            Map map = new HashMap();
            if (StringUtils.isEmpty(MyApplication.getBaseUserBean().getAccessToken()))
                return;
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            String lableCode = "";
            for (NoteBean.ListBean bean : mList) {
                if (bean.getIsMark() == 1)
                    lableCode += bean.getLabel_code() + ",";
            }
            if (StringUtils.isEmpty(lableCode)) {
                ToastUtils.showShort(mContext, "请选择1—3个，可随时调整");
                return;
            } else
                lableCode.substring(lableCode.length() - 1, lableCode.length());
            map.put("lableCode", lableCode);
            mView.showLoading();
            mNoteBiz.setNote(map, this);
        }
    }

    /**
     * 设置标签成功回调
     *
     * @param info
     */
    @Override
    public void setNoteNext(EmptyObjectBean info) {
        if (info != null && mView != null) {
            mView.setNoteNext();
            mView.hideLoading();
        }
    }
}

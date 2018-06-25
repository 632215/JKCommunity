package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.service.AnnouncementBiz;
import com.jinke.community.service.impl.AnnouncementImpl;
import com.jinke.community.service.listener.AnnouncementListener;
import com.jinke.community.utils.ACache;
import com.jinke.community.view.AnnouncementView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AnnouncementPresenter extends BasePresenter<AnnouncementView> implements AnnouncementListener {
    private Context mContext;
    private AnnouncementBiz mBiz;
    private ACache acache;

    public AnnouncementPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new AnnouncementImpl(mContext);
    }

    /**
     * 获取当前用户所属小区列表
     */
    public void getCommunity() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        mBiz.getCommunity(map, this);
    }

    @Override
    public void getCommunityNext(UserCommunityBean info) {
        if (mView != null) {
            mView.getCommunityNext(info);
        }
    }

    /**
     * 获取公告列表
     *
     * @param map
     */
    public void getPostItNoticeList(Map<String, String> map) {
        mBiz.getPostItNoticeList(map, this);
    }

    @Override
    public void getPostItNoticeListNext(BrokeNoticeListBean info) {
        if (mView != null) {
            mView.getPostItNoticeListNext(info);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    //将首页的公告数据传递给公告列表
    public void addPostItNoticeList() {
        acache = ACache.get(mContext);
        BrokeNoticeListBean info = new BrokeNoticeListBean();
        List<BrokeNoticeListBean.ListBean> list = new ArrayList<>();
        NoticeListBean noticeListBean = (NoticeListBean) acache.getAsObject("NoticeListBean");
        if (noticeListBean != null) {
            for (NoticeListBean.ListBean bean : noticeListBean.getList()) {
                BrokeNoticeListBean.ListBean listBean = new BrokeNoticeListBean.ListBean();
                listBean.setAvatar(bean.getAvatar());
                listBean.setContent(bean.getContent());
                listBean.setCreateTime(bean.getCreateTime());
                listBean.setManager(bean.getManager());
                listBean.setNoticeId(bean.getNoticeId());
                listBean.setNoticeType(bean.getNoticeType());
                listBean.setTitle(bean.getTitle());
                listBean.setType(bean.getType());
                list.add(listBean);
            }
            info.setList(list);
        }
        mView.getPostItNoticeListNext(info);
    }
}

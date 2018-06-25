package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.PropertyProgressBean;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.NewPropertyDetailsPresenter;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.adapter.BrokenDetailsAdapter;
import com.jinke.community.ui.adapter.PropertyProgressAdapter;
import com.jinke.community.ui.adapter.SatisfactionAdapter;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AndroidUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.NewPropertyDetailsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/4/25.
 */

public class NewPropertyDetailsActivity extends BaseActivity<NewPropertyDetailsView, NewPropertyDetailsPresenter> implements
        NewPropertyDetailsView, PropertyProgressAdapter.ProgressAdapterListener {
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.tx_post_id)
    TextView txPostId;
    @Bind(R.id.tx_time)
    TextView txTime;
    @Bind(R.id.tx_post_house)
    TextView txPostHouse;
    @Bind(R.id.tx_post_person)
    TextView txPostPerson;
    @Bind(R.id.tx_content)
    TextView txContent;
    @Bind(R.id.fill_grid_view)
    FillGridView fillGridView;
    @Bind(R.id.tx_post_progress)
    TextView txPostProgress;
    @Bind(R.id.recycle_progress)
    FillRecyclerView recyclePprogress;
    @Bind(R.id.tx_housekeeper)
    TextView txHousekeeper;

    @Bind(R.id.rl_please_comment)
    RelativeLayout rlPleaseComment;
    @Bind(R.id.rl_comment)
    RelativeLayout rlComment;
    @Bind(R.id.tx_comment_date)
    TextView txCommentDate;
    @Bind(R.id.tx_comment_time)
    TextView txCommentTime;
    @Bind(R.id.grid_view)
    FillGridView gridViewSatisfaction;
    @Bind(R.id.tx_satisfaction)
    TextView txSatisfaction;
    @Bind(R.id.tx_comment)
    TextView txComment;

    private String postId = null;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
    BrokenDetailsAdapter adapter = null;//图片适配器
    PropertyProgressAdapter progressAdapter = null;//流程节点适配器
    private SatisfactionAdapter satisfactionAdapter = null;//满意度（星星）适配器
    private List<String> satisfactionList = new ArrayList<>();//满意度（星星）

    @Override
    public NewPropertyDetailsPresenter initPresenter() {
        return new NewPropertyDetailsPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_property;
    }

    @Override
    protected void initView() {
        setTitle("我的报事");
        showBackwardView("", true);
        loadingLayout.setStatus(LoadingLayout.Success);
        postId = getIntent().getStringExtra("keepId");
        getKeeperDetail();
    }

    private void getKeeperDetail() {
        if (StringUtils.isEmpty(MyApplication.getBaseUserBean().getAccessToken()))
            return;
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("postId", postId);
        presenter.getKeeperDetail(map);
    }

    /**
     * 获取报事详情成功回调
     *
     * @param progressBean,keepPropertyBean
     */
    @Override
    public void getProgressSuccess(final PropertyProgressBean progressBean, final KeepPropertyBean keepPropertyBean) {
        //报事详情
        setProperty(keepPropertyBean);
        //组装流程节点
        makeList(progressBean, keepPropertyBean);
    }

    //流程节点
    private void makeList(PropertyProgressBean progressBean, KeepPropertyBean keepPropertyBean) {
        if (progressBean != null) {
            setVisibility(View.GONE, View.GONE);
            List<PropertyProgressBean.ListObjBean> list = new ArrayList<>();
            if (progressBean.getListObj() != null && progressBean.getListObj().size() > 1) {
                list.add(progressBean.getListObj().get(0));//添加第一个节点 ：表示已经进入工单池
                getDealPerson(progressBean, list, keepPropertyBean);
                switch (keepPropertyBean.getStatus()) {
//                    case 1://待处理
//                        break;
//                    case 2://正在处理
//                        break;
                    case 3://已完成
                        setVisibility(View.GONE, View.VISIBLE);
                        setVisit(progressBean, list);
                        break;
                    case 4://已评价
                        setVisibility(View.VISIBLE, View.GONE);
                        setVisit(progressBean, list);
                        setHasComment(progressBean);
                        break;
                }
            }
            progressBean.setListObj(list);
            setProgress(progressBean);
        }
    }

    //设置回访人
    private void setVisit(PropertyProgressBean progressBean, List<PropertyProgressBean.ListObjBean> list) {
        for (int x = progressBean.getListObj().size() - 1; x > 1; x--) {
            if (StringUtils.equals("回访", progressBean.getListObj().get(x).getNodeName())) {
                list.add(progressBean.getListObj().get(x));
                break;
            }
        }
    }

    //设置评价显示内容
    private void setHasComment(PropertyProgressBean propertyProgressBean) {
        for (PropertyProgressBean.ListObjBean bean : propertyProgressBean.getListObj()) {
            if (!StringUtils.isEmpty(bean.getGrade())) {
                txCommentDate.setText(bean.getCreateTime().substring(0, bean.getCreateTime().indexOf(" ")).replace("-", "/"));
                txCommentTime.setText(bean.getCreateTime().substring(bean.getCreateTime().indexOf(" ") + 1, bean.getCreateTime().lastIndexOf(":")).replace("-", "/"));
                txComment.setText(bean.getRemark());
                for (int x = 0; x < 5; x++) {
                    satisfactionList.add(x < Integer.parseInt(bean.getGrade()) ? "1" : "0");
                }
                txSatisfaction.setText(StringUtils.equals("0", bean.getGrade()) ? getResources().getString(R.string.activity_comment_satisfaction1)
                        : StringUtils.equals("1", bean.getGrade()) ? getResources().getString(R.string.activity_comment_satisfaction2)
                        : StringUtils.equals("2", bean.getGrade()) ? getResources().getString(R.string.activity_comment_satisfaction3)
                        : StringUtils.equals("3", bean.getGrade()) ? getResources().getString(R.string.activity_comment_satisfaction4)
                        : getResources().getString(R.string.activity_comment_satisfaction5));
                satisfactionAdapter = new SatisfactionAdapter(this, R.layout.item_satisfaction, satisfactionList);
                gridViewSatisfaction.setAdapter(satisfactionAdapter);
            }
        }
    }

    //设置评价和请评价的可见性
    private void setVisibility(int rlCommentVisibility, int rlPleaseCommentVisibility) {
        rlComment.setVisibility(rlCommentVisibility);
        rlPleaseComment.setVisibility(rlPleaseCommentVisibility);
    }

    //确定处理人的联系方式
    private void getDealPerson(PropertyProgressBean progressBean, List<PropertyProgressBean.ListObjBean> list, KeepPropertyBean keepPropertyBean) {
        //寻找最后一个节点电话号码不为null的
        boolean flag = false;
        PropertyProgressBean.ListObjBean bean = null;
        for (int x = progressBean.getListObj().size() - 1; x > 1; x--) {
            if (!StringUtils.isEmpty(progressBean.getListObj().get(x).getTelePhone())
                    ) {
                if (!StringUtils.equals("回访", progressBean.getListObj().get(x).getNodeName())) {
                    flag = true;
                    bean = progressBean.getListObj().get(x);
                    list.add(1, bean);
                    break;
                }
            }
        }
        if (!flag && progressBean.getListObj().size() > 1) {
            bean = progressBean.getListObj().get(1);
            if (bean != null) {
                bean.setUserName("");
                bean.setTelePhone("");
                list.add(bean);
            }
        }
        if (progressBean.getListObj().size() >= 3 && keepPropertyBean.getStatus() > 2) {
            list.add(bean);
        }
    }

    /**
     * 获取到报事信息成功回调/报事节点失败
     *
     * @param keepPropertyBean
     */
    @Override
    public void getKeeperDetailSuccess(KeepPropertyBean keepPropertyBean) {
        setProperty(keepPropertyBean);
        txPostProgress.setText(R.string.activity_property_progress1);
        recyclePprogress.setVisibility(View.GONE);
    }

    //报事详情

    private void setProperty(final KeepPropertyBean keepPropertyBean) {
        if (keepPropertyBean != null) {
            txPostId.setText(StringUtils.isEmpty(keepPropertyBean.getKeepId()) ? "" : keepPropertyBean.getKeepId());
            txTime.setText(TextUtils.intToString(String.valueOf(keepPropertyBean.getCreateTime()), "yyyy/MM/dd HH:mm"));
            txPostHouse.setText(StringUtils.isEmpty(keepPropertyBean.getHouseName()) ? "" : keepPropertyBean.getHouseName());
            txPostPerson.setText(keepPropertyBean.getUserName() + " " + (StringUtils.isEmpty(keepPropertyBean.getPhone()) ? "" : TextUtils.changTelNum(keepPropertyBean.getPhone())));
            txContent.setText(keepPropertyBean.getContent());
            adapter = new BrokenDetailsAdapter(this, R.layout.item_property_details, keepPropertyBean.getImage());
            fillGridView.setAdapter(adapter);
            fillGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    computeBoundsBackward(keepPropertyBean.getImage(), fillGridView);
                    PhotoActivity.startActivity(NewPropertyDetailsActivity.this, mThumbViewInfoList, position);
                }
            });
            fillGridView.setVisibility(keepPropertyBean.getImage() == null || keepPropertyBean.getImage().size() == 0 ? View.GONE : View.VISIBLE);
            txPostProgress.setText(keepPropertyBean.getStatus() == 1 ? getString(R.string.activity_property_progress1) :
                    keepPropertyBean.getStatus() == 2 ? getString(R.string.activity_property_progress2) :
                            keepPropertyBean.getStatus() == 3 ? getString(R.string.activity_property_progress_finish) :
                                    getString(R.string.activity_property_progress4));
        }
    }

    //报事流程节点
    private void setProgress(PropertyProgressBean progressBean) {
        if (progressBean != null) {
            progressAdapter = new PropertyProgressAdapter(this, progressBean);
            recyclePprogress.setLayoutManager(new LinearLayoutManager(this));
            recyclePprogress.setAdapter(progressAdapter);
            progressAdapter.setListener(this);
            recyclePprogress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String code, String msg) {
        loadingLayout.setStatus(LoadingLayout.No_Network);
    }

    @OnClick({R.id.tx_housekeeper, R.id.tx_to_score})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_housekeeper:
                ACache aCache = ACache.get(this);
                CallCenterBean bean = (CallCenterBean) aCache.getAsObject("CallCenterBean");
                AndroidUtils.callPhone(this, bean != null && !StringUtils.isEmpty(bean.getServicePhone()) ? bean.getServicePhone()
                        : AppConfig.SERVICEPHONE);
                break;

            case R.id.tx_to_score://评价
                startActivity(new Intent(this, NewCommentActivity.class)
                        .putExtra("postId", postId));
                finish();
                break;
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     *
     * @param picUrl
     * @param itemGridView
     */
    private void computeBoundsBackward(List<String> picUrl, FillGridView itemGridView) {
        mThumbViewInfoList.clear();
        for (int i = 0; i < picUrl.size(); i++) {
            View itemView = itemGridView.getChildAt(i);
            ThumbViewInfo thumbViewInfo = new ThumbViewInfo(picUrl.get(i));
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.simpleImage);
                thumbView.getGlobalVisibleRect(bounds);
                thumbViewInfo.setBounds(bounds);
            }
            mThumbViewInfoList.add(thumbViewInfo);
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog("true");
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    /**
     * PropertyProgressAdapter 接口回调
     *
     * @return
     */
    @Override
    public void toScore() {
        startActivity(new Intent(this, NewCommentActivity.class)
                .putExtra("postId", postId));
        finish();
    }
}

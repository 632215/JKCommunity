package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.presenter.CommentPresenter;
import com.jinke.community.ui.adapter.SatisfactionAdapter;
import com.jinke.community.ui.toast.CommonDialog;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.view.CommentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;


/**
 * Created by Administrator on 2017/12/6.
 * 评论
 */

public class NewCommentActivity extends BaseActivity<CommentView, CommentPresenter> implements CommentView, AdapterView.OnItemClickListener {
    @Bind(R.id.fill_grid_view)
    FillGridView fillGridView;
    @Bind(R.id.tx_service_satisfaction)
    TextView txServiceSatisfaction;
    @Bind(R.id.ed_property_content)
    EditText edPropertyContent;

    private SatisfactionAdapter satisfactionAdapter;
    private List<String> satisfactionList = new ArrayList<>();
    private String score = "";//分数
    private CommonDialog commonDialog;
    private String postId = null;//报事ID

    @Override
    public CommentPresenter initPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_comment;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_comment);
        showBackwardView(R.string.empty, true);
        showForwardViewColor(getResources().getColor(R.color.color_main));
        showForwardView("发布", true);
        postId = getIntent().getStringExtra("postId");
        edPropertyContent.addTextChangedListener(new EmTextWatch(edPropertyContent, this));
        initListData();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initListData() {
        for (int x = 0; x < 5; x++) {
            satisfactionList.add("0");
        }
        satisfactionAdapter = new SatisfactionAdapter(this, R.layout.item_satisfaction, satisfactionList);
        satisfactionAdapter.setSizeFlag(1);
        fillGridView.setAdapter(satisfactionAdapter);
        fillGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        satisfactionList.clear();
        for (int x = 0; x < 5; x++) {
            satisfactionList.add(x <= position ? "1" : "0");
        }
        satisfactionAdapter.setDataList(satisfactionList);
        score = String.valueOf(position + 1);
        switch (position) {
            case 0:
                txServiceSatisfaction.setText(getResources().getString(R.string.activity_comment_satisfaction1));
                break;
            case 1:
                txServiceSatisfaction.setText(getResources().getString(R.string.activity_comment_satisfaction2));
                break;
            case 2:
                txServiceSatisfaction.setText(getResources().getString(R.string.activity_comment_satisfaction3));
                break;
            case 3:
                txServiceSatisfaction.setText(getResources().getString(R.string.activity_comment_satisfaction4));
                break;
            case 4:
                txServiceSatisfaction.setText(getResources().getString(R.string.activity_comment_satisfaction5));
                break;
        }
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        if (StringUtils.isEmpty(score)) {
            if (commonDialog == null) {
                commonDialog = new CommonDialog(this);
            }
            commonDialog.show();
            return;
        }
        if (StringUtils.isEmpty(edPropertyContent.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入内容");
            return;
        }
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        if (StringUtils.isEmpty(postId))
            return;
        map.put("postId", postId);
        map.put("score", score);
        if (MyApplication.getInstance().getDefaultHouse() != null)
            map.put("houseId", MyApplication.getInstance().getDefaultHouse().getHouse_id());
        else
            return;
        map.put("content", edPropertyContent.getText().toString().trim());
        presenter.addPostItComments(map);
    }

    //添加报事评论成功——跳转至物业报事详情
    @Override
    public void addPostItCommentsNext() {
        ToastUtils.showShort(this, "提交成功");
        startActivity(new Intent(this, NewPropertyDetailsActivity.class)
                .putExtra("keepId", postId));
        finish();
    }

    @Override
    public void showDiaog() {
        showProgressDialog("false");
    }

    @Override
    public void hideDiaog() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this, "新报事评论");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "新报事评论");
    }
}

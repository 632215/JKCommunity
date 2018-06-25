package com.jinke.community.ui.activity.house;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.presenter.AddAuthorizedPresenter;
import com.jinke.community.ui.toast.RelationshipWindow;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.utils.StatServiceUtils;
import com.jinke.community.view.AddAuthorizedView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/11/21.
 * 添加授权/编辑授权
 */

public class AddAuthorizedActivity extends BaseActivity<AddAuthorizedView, AddAuthorizedPresenter>
        implements AddAuthorizedView {
    @Bind(R.id.tx_relationship)
    TextView txRelationship;
    @Bind(R.id.ed_owner_name)
    EditText edName;
    @Bind(R.id.ed_owner_phone)
    EditText edOwnerPhone;
    private RelationshipWindow window;
    private HouseListBean.ListBean.Grants grants;
    private HouseListBean.ListBean listBean;
    private String isUpdate = "";
    private String deleteIds = "";

    @Override
    public AddAuthorizedPresenter initPresenter() {
        return new AddAuthorizedPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_authorized;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_authorization_add_authorized);
        showBackwardView(R.string.empty, true);
        grants = new HouseListBean.ListBean.Grants();
        isUpdate = (String) getIntent().getSerializableExtra("isUpdate");
        listBean = (HouseListBean.ListBean) getIntent().getSerializableExtra("listBean");
        //设置授权用户姓名输入限制
        edName.addTextChangedListener(new EmTextWatch(edName, this));
        if (StringUtils.equals("1", isUpdate)) {
            grants = (HouseListBean.ListBean.Grants) getIntent().getSerializableExtra("grants");
            deleteIds = String.valueOf(grants.getGrantId());
            switch (grants.getRelation()) {
                case "2":
                    txRelationship.setText("家人");
                    break;

                case "3":
                    txRelationship.setText("朋友");
                    break;

                case "4":
                    txRelationship.setText("租赁户");
                    break;

                case "1":
                    txRelationship.setText("业主");
                    break;
            }
            edName.setText(grants.getGrantName());
            edOwnerPhone.setText(grants.getGrantPhone());
        }
    }

    @OnClick({R.id.rl_relation, R.id.image_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_relation:
                if (window == null) {
                    window = new RelationshipWindow(this);
                }
                window.setOnRelationshipWindowListener(new RelationshipWindow.OnRelationshipWindowListener() {
                    @Override
                    public void relationship(String title, String rel) {
                        txRelationship.setText(title);
                        grants.setRelation(rel);
                    }
                });
                window.showPopWindow(view);
                break;
            case R.id.image_save:
                if (edOwnerPhone.getText().toString().length() != 11) {
                    ToastUtils.showShort(AddAuthorizedActivity.this, "您输入的电话号码格式有误！");
                    return;
                }
                if (StringUtils.isEmpty(edName.getText().toString())) {
                    ToastUtils.showShort(AddAuthorizedActivity.this, "请输入被授权人姓名！");
                    return;
                }
                if (StringUtils.isEmpty(grants.getRelation())) {
                    ToastUtils.showShort(AddAuthorizedActivity.this, "当前个人权限编辑还没完成哦！");
                    return;
                }
                if (listBean != null)
                    AnalyUtils.addAnaly(10045, listBean.getHouse_id());
                else
                    AnalyUtils.addAnaly(10045);
                grants.setGrantName(edName.getText().toString());
                grants.setGrantPhone(edOwnerPhone.getText().toString());
                List<HouseListBean.ListBean.Grants> grantsList = new ArrayList<>();
                grantsList.add(grants);
                ToastUtils.showShort(this, "保存中......");
                presenter.updateAuthorized(grantsList, listBean, deleteIds);
                break;
        }
    }

    @Override
    public void addAuthorizationNext(HouseListBean info) {
        ToastUtils.showShort(this, "保存成功");
        finish();
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog(String.valueOf(true));
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatServiceUtils.beginPage(this, "添加房屋授权");
        AnalyUtils.setBAnalyResume(this, "添加房屋授权");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatServiceUtils.endPage(this, "添加房屋授权");
        AnalyUtils.setBAnalyPause(this, "添加房屋授权");
    }
}

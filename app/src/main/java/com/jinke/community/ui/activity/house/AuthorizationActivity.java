package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.presenter.AuthorizationPresenter;
import com.jinke.community.ui.adapter.AuthorAdaptr;
import com.jinke.community.ui.toast.NetWorkErrDialog;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.IAuthorizationView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.NetWorksUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * 房屋授权管理
 * Created by root on 17-8-2.
 */

public class AuthorizationActivity extends BaseActivity<IAuthorizationView, AuthorizationPresenter>
        implements IAuthorizationView, AuthorAdaptr.OnAuthorizationListener, NetWorkErrDialog.NetWorkErrDialogListener {
    @Bind(R.id.tx_authorization_name)
    TextView txAuthorizationName;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tx_authorization_title)
    TextView authorizationTitle;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    HouseListBean.ListBean listBean;
    AuthorAdaptr adapter;
    private NetWorkErrDialog netWorkErrDialog;
    private ACache aCache;

    @Override
    public AuthorizationPresenter initPresenter() {
        return new AuthorizationPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_authorization;
    }

    private List<HouseListBean.ListBean.Grants> list = new ArrayList<>();

    @Override
    protected void initView() {
        setTitle(R.string.authorization_title);
        showBackwardView("", true);
        aCache = ACache.get(this);
        listBean = (HouseListBean.ListBean) getIntent().getSerializableExtra("bean");
        list = listBean.getGrants();
        adapter = new AuthorAdaptr(this, list);
        adapter.setOnDeleteAuthorizationListener(this);
        listView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        List<HouseListBean.ListBean.OwnersBean> dataList = listBean.getOwners();
        authorizationTitle.setText(listBean.getCommunity_name() + listBean.getHouse_name());
        String ownerString = "";
        for (HouseListBean.ListBean.OwnersBean ownerBean : dataList) {
            ownerString += ownerBean.getOwnerName() + "  " + TextUtils.changTelNum(ownerBean.getPhone()) + "   ";
        }
        txAuthorizationName.setText(ownerString);
    }

    @OnClick({R.id.tx_authorization_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_authorization_add:
                if (NetWorksUtils.isConnected(this)) {
                    if (listBean != null)
                        AnalyUtils.addAnaly(10044, listBean.getHouse_id());
                    else
                        AnalyUtils.addAnaly(10044);
                    Intent intent = new Intent(AuthorizationActivity.this, AddAuthorizedActivity.class);
                    intent.putExtra("listBean", listBean);
                    intent.putExtra("isUpdate", "0");
                    startActivity(intent);
                } else {
                    netWorkErrDialog = new NetWorkErrDialog(this);
                    netWorkErrDialog.setListener(this);
                    netWorkErrDialog.show();
                }
                break;
        }
    }

    String deleteIds = "";

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
    }

    @Override
    public void showMsg(String msg) {
        hideDialog();
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(AuthorizationActivity.this, msg);
    }

    @Override
    public void showDialog() {
        showProgressDialog(null);
    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort(this, "修改成功");
        hideDialog();
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getHouseListData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHouseListData();
        StatService.onResume(AuthorizationActivity.this);
        StatService.trackBeginPage(AuthorizationActivity.this, "添加授权");
        AnalyUtils.setBAnalyResume(this, "添加授权");
    }

    @Override
    public void getHouseListDataNext(HouseListBean info) {
        aCache.put("HouseListBean", info, ACache.TIME_DAY);
        for (HouseListBean.ListBean bean : info.getList()) {
            if (StringUtils.equals(bean.getHouse_id(), listBean.getHouse_id())) {
                if (bean.getGrants() != null)
                    loadingLayout.setStatus(bean.getGrants().size() > 0 ? LoadingLayout.Success : LoadingLayout.Authorized_Empty);
                list = bean.getGrants();
                adapter.setDataList(list);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AuthorizationActivity.this);
        StatService.trackEndPage(AuthorizationActivity.this, "添加授权");
        AnalyUtils.setBAnalyPause(this, "添加授权");
    }

    /**
     * AuthorAdaptr回调接口
     *
     * @param bean
     * @param Ids
     */
    @Override
    public void onDelete(HouseListBean.ListBean.Grants bean, String Ids) {
        if (list.contains(bean)) {
            list.remove(bean);
            adapter.notifyDataSetChanged();
        }
        if (!Ids.equals("0") && !StringUtils.isEmpty(Ids)) {
            deleteIds += Ids + ",";
        }
        presenter.getJsonData(adapter, listBean, deleteIds);
    }

    @Override
    public void deleteOnNext(String string) {
        showMsg(string);
//        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getHouseListData();
    }

    /**
     * AuthorAdaptr回调接口
     *
     * @param grants
     */
    @Override
    public void onEditClick(HouseListBean.ListBean.Grants grants) {
        Intent intent = new Intent(AuthorizationActivity.this, AddAuthorizedActivity.class);
        intent.putExtra("listBean", listBean);
        intent.putExtra("isUpdate", "1");
        intent.putExtra("grants", grants);
        startActivity(intent);
    }

    @Override
    public void refresh() {
    }
}

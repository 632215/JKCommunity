package com.jinke.community.ui.activity.house;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.IdCardBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.AuthenticationPresenter;
import com.jinke.community.ui.adapter.IDCardAdapter;
import com.jinke.community.ui.image.ImagePicker;
import com.jinke.community.ui.image.bean.ImageItem;
import com.jinke.community.ui.image.ui.ImageGridActivity;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.FileNameUtil;
import com.jinke.community.utils.PictureUtils;
import com.jinke.community.view.AuthenticationView;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.stat.StatService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

import static com.jinke.community.utils.PictureUtils.REQUEST_CODE_SELECT;

/**
 * 身份验证
 * Created by Administrator on 2018/5/29.
 */

public class AuthenticationActivity extends BaseActivity<AuthenticationView, AuthenticationPresenter>
        implements IDCardAdapter.IDCardListener
        , PictureUtils.LubanListener
        , AuthenticationView {

    @Bind(R.id.fill_recycler_view)
    FillRecyclerView fillRecyclerView;

    private List<IdCardBean> list = new ArrayList<>();
    private IDCardAdapter idCardAdapter = null;
    private String houseId = null;//房屋号
    private String houseName = null;//房屋名字

    @Override
    public AuthenticationPresenter initPresenter() {
        return new AuthenticationPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initView() {
        showBackwardView(R.string.empty, true);
        setTitle(R.string.activity_authentication_title);
        houseId = getIntent().getStringExtra("houseId");
        houseName = getIntent().getStringExtra("houseName");
        showForwardViewColor(getResources().getColor(R.color.gray_forward));
        showForwardView(R.string.activity_authentication_save, true);
        initAdapter();
    }

    //初始化adapter并加入一个空白的bean
    private void initAdapter() {
        IdCardBean bean = new IdCardBean();
        bean.setHouseId(houseName);
        list.add(bean);
        if (idCardAdapter == null)
            idCardAdapter = new IDCardAdapter(this, list, this);
        fillRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fillRecyclerView.setAdapter(idCardAdapter);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        checkInfo();
    }

    //检查资料完整性
    private void checkInfo() {
        if (checkInput()) return;
        uploadInfo();
    }

    private boolean checkInput() {
        for (IdCardBean bean : list) {
            if (StringUtils.isEmpty(bean.getPortrait()) ||
                    StringUtils.isEmpty(bean.getNational())) {
                ToastUtils.showShort(this, "请您拍照上传身份证信息");
                return true;
            }

            if (StringUtils.isEmpty(bean.getName())) {
                ToastUtils.showShort(this, "请您完善姓名信息");
                return true;
            }

            if (StringUtils.isEmpty(bean.getIdcard()) || !Pattern.matches("(^\\d{18}$)|(^\\d{15}$)", bean.getIdcard()) ||
                    bean.getIdcard().length() < 18) {
                ToastUtils.showShort(this, "请您确认身份证输入数据");
                return true;
            }
            String chartPattern = "([\\u4e00-\\u9fa5]{1," + bean.getName().length() + "})";
            if (!Pattern.matches(chartPattern, bean.getName())) {
                ToastUtils.showShort(this, "请您输入中文姓名");
                return true;
            }
            if (!Pattern.matches("^1[0-9]{10}$", bean.getPhone()) || bean.getPhone().length() < 11) {
                ToastUtils.showShort(this, "请您输入正确手机号码");
                return true;
            }
        }
        return false;
    }

    //上传资料
    private void uploadInfo() {
        Map<String, String> map = new HashMap<>();//用于计算签名
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        String idCard = "";
        String ownerName = "";
        String phone = "";
        for (IdCardBean bean : list) {
            idCard += bean.getIdcard() + ",";
            ownerName += bean.getName() + ",";
            phone += bean.getPhone() + ",";
        }
        map.put("idCard", idCard.substring(0, idCard.lastIndexOf(",")));//身份证号
        map.put("houseId", houseId);//房屋号
        map.put("ownerName", ownerName.substring(0, ownerName.lastIndexOf(",")));//业主名字
        map.put("phone", phone.substring(0, phone.lastIndexOf(",")));//业主电话

        RequestParams params = HttpMethods.MapToParams(map);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); ) {
                params.addBodyParameter("image" + i, new File(list.get(i).getPortrait()));
                params.addBodyParameter("image" + (i + 1), new File(list.get(i).getNational()));
                i = i + 2;
            }
        }
        presenter.upload(params);
    }

    /**
     * 上传成功回调
     */
    @Override
    public void uploadSuccess() {
        startActivity(new Intent(this, CheckActivity.class)
                .putExtra("houseState", "D"));
        finish();
    }

    /**
     * 上传失败
     *
     * @param s
     */
    @Override
    public void uploadFail(String s) {
        ToastUtils.showShort(this, s);
    }

    @OnClick({R.id.tx_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_add:
                if (checkInput()) return;
                IdCardBean bean = new IdCardBean();
                bean.setHouseId(houseName);
                list.add(bean);
                idCardAdapter.addData(list);
                break;
        }
    }

    /**
     * IDcardAdapter 删除回调
     *
     * @param position
     */
    @Override
    public void delete(int position) {
        list.remove(position);
        idCardAdapter.setDataList(list);
    }

    private int position;
    private int index;
    private ArrayList<ImageItem> images = null;
    private List<String> picSelectList;


    /**
     * IDcardAdapter 拍照回调
     *
     * @param position
     * @param index
     */
    @Override
    public void camera(int index, int position) {
        this.position = position;
        this.index = index;
        PictureUtils.initOpenCamera(this);
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picSelectList = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem imageItem : images) {
//                    switch (index) {//原图
//                        case 0:
//                            list.get(position).setPortrait(imageItem.path);
//                            break;
//                        case 1:
//                            list.get(position).setNational(imageItem.path);
//                            break;
//                    }
                    String uuid = FileNameUtil.getrandom();
                    PictureUtils.getPicZipPath(this, imageItem.path, uuid);
                    picSelectList.add(uuid);
                }
            }
        }
//        idCardAdapter.setDataList(list);//原图
    }

    /**
     * 拍照压缩 回调
     *
     * @param name
     */
    @Override
    public void luBanFinish(String name) {
        for (String tempName : picSelectList) {
            if (name.contains(tempName)) {
                switch (index) {
                    case 0:
                        list.get(position).setPortrait(name);
                        break;
                    case 1:
                        list.get(position).setNational(name);
                        break;
                }
            }
        }
        idCardAdapter.setDataList(list, position);
    }

    @Override
    public void showLoading() {
        showProgressDialog("false");
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(AuthenticationActivity.this);
        StatService.trackBeginPage(AuthenticationActivity.this, "身份验证");
        AnalyUtils.setBAnalyResume(this, "身份验证");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onResume(AuthenticationActivity.this);
        StatService.trackBeginPage(AuthenticationActivity.this, "身份验证");
        AnalyUtils.setBAnalyPause(this, "身份验证");
    }
}

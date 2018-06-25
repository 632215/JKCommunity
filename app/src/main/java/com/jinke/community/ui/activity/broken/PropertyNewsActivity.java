package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.PropertyNewsPresent;
import com.jinke.community.ui.adapter.BrokeUpAdapter;
import com.jinke.community.ui.image.ImagePicker;
import com.jinke.community.ui.image.bean.ImageItem;
import com.jinke.community.ui.image.ui.ImageGridActivity;
import com.jinke.community.ui.toast.SelectAlbumWindow;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.utils.FileNameUtil;
import com.jinke.community.utils.PictureUtils;
import com.jinke.community.view.IPropertyNewsView;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.stat.StatService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

import static com.jinke.community.utils.PictureUtils.REQUEST_CODE_SELECT;

/**
 * Created by root on 17-8-6.
 * 添加物业报事
 */

public class PropertyNewsActivity extends BaseActivity<IPropertyNewsView, PropertyNewsPresent>
        implements IPropertyNewsView, SelectHouseDialog.onSelectHouseListener
        , AdapterView.OnItemClickListener, SelectAlbumWindow.OnSelectAlbumWindowListener
        , BrokeUpAdapter.BrokeUpAdapterlistener, PictureUtils.LubanListener {
    @Bind(R.id.ed_property_content)
    EditText edContent;
    @Bind(R.id.tx_counter)
    TextView txCounter;
    @Bind(R.id.fill_grid_view)
    FillGridView fillGridView;
    @Bind(R.id.rl_select_house)
    RelativeLayout rlSelectHouse;
    @Bind(R.id.tx_select_house_title)
    TextView txTitle;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.ed_owner)
    EditText edOwner;
    @Bind(R.id.ed_owner_phone)
    EditText edOwnerPhone;

    private HouseListBean.ListBean listBean;
    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息
    private BaseUserBean baseUserBean;
    private BrokeUpAdapter adapter;
    private List<String> list = new ArrayList<>();
    private SelectAlbumWindow window;
    private SelectHouseDialog dialog;
    private List<String> picSelectList = new ArrayList<>();
    ArrayList<ImageItem> images = null;
    private int flag = 2;//新版本报事为1，旧版本为0

    @Override
    public PropertyNewsPresent initPresenter() {
        return new PropertyNewsPresent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_property_news;
    }

    @Override
    protected void initView() {
        showBackwardView("", true);
        setTitle("物业报事");
        showForwardViewColor(getResources().getColor(R.color.loaclayout_color3));
        showForwardView("报事记录", true);
        baseUserBean = MyApplication.getBaseUserBean();
        if (baseUserBean != null) {
            edOwner.setText(baseUserBean.getNickName());
            edOwnerPhone.setText(baseUserBean.getPhone());
        }
        dialog = new SelectHouseDialog(this, this);
        window = new SelectAlbumWindow(this);
        window.setOnSelectAlbumWindowListener(this);
        initEditLayout();
        initPictureList();
        getHouseList();
        //设置授权用户姓名输入限制
        edOwner.addTextChangedListener(new EmTextWatch(edOwner, this));//初始化输入框限制
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        startActivity(new Intent(PropertyNewsActivity.this, PropertyHistoryActivity.class));
    }

    /**
     * 初始化图片列表
     */
    private void initPictureList() {
        list.add("a");
        adapter = new BrokeUpAdapter(this, list, this);
        fillGridView.setAdapter(adapter);
        fillGridView.setOnItemClickListener(this);
    }

    /**
     * 初始化文本框的监听
     */
    private void initEditLayout() {
        txCounter.setText("0/800");
        edContent.addTextChangedListener(new EmTextWatch(edContent, this));
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                txCounter.setText(s.length() + "/800");
            }
        });
    }

    private void getHouseList() {
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean == null) {
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", baseUserBean.getAccessToken());
            presenter.getHouseList(map);
        } else {
            setDefaultHouse();
        }
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        aCache.put("HouseListBean", info, ACache.TIME_DAY);
        houseListBean = info;
        setDefaultHouse();
    }

    public void setDefaultHouse() {
        if (listBean == null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (bean.getDft_house() == 1) {   //默认选中,1-选中，0-没选中
                    listBean = bean;
                }
            }
        }
        txTitle.setText(listBean.getCommunity_name() + listBean.getHouse_name());
        rlSelectHouse.setClickable(houseListBean.getList().size() > 0 ? true : false);
        txSelect.setVisibility(houseListBean.getList().size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @OnClick({R.id.rl_select_house, R.id.tx_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_house:
                dialog.show();
                break;
            case R.id.tx_submit:
                checkContent();
                break;
        }
    }

    private void checkContent() {
        if (StringUtils.isEmpty(edContent.getText().toString().trim())) {
            ToastUtils.showShort(this, "请填写您的报事需求,我们会尽快处理!");
            return;
        }
        if (StringUtils.isEmpty(edOwnerPhone.getText().toString().trim()) || edOwnerPhone.getText().toString().trim().length() < 11) {
            ToastUtils.showShort(this, "请确认您的联系电话！");
            return;
        }
        if (StringUtils.isEmpty(edOwner.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入您的昵称！");
            return;
        }
        presenter.getConfig(listBean.getCommunity_id());
    }

    /**
     * 获取配置试点小区 成功回调
     *
     * @param info
     */
    @Override
    public void getConfigNext(CommunityBean info) {
        if (info != null)
            for (CommunityBean.ListBean bean : info.getList())
                if (StringUtils.equals("property_broken_test", bean.getAuthority_code())) {
                    flag = info != null && (StringUtils.equals("1", bean.getStatus())) ? 1 : 0;
                }
        addPostIt();
    }

    /**
     * 获取配置试点失败 成功回调
     */
    @Override
    public void getConfigError() {
        flag = 0;
        addPostIt();
    }

    private void addPostIt() {
        if (listBean != null)
            AnalyUtils.addAnaly(10038, listBean.getHouse_id());
        else
            AnalyUtils.addAnaly(10038);
        Map<String, String> map = new HashMap<>();//用于计算签名
        map.put("accessToken", baseUserBean.getAccessToken());
        map.put("content", edContent.getText().toString().trim());
        map.put("houseId", listBean.getHouse_id());
        map.put("contactName", baseUserBean.getNickName());
        map.put("contactPhone", edOwnerPhone.getText().toString().trim());
        map.put("postType", flag == 0 ? "old" : "new");
        RequestParams params = HttpMethods.MapToParams(map);
        if (list.size() > 0) {
            for (int i = 0; i < list.size() - 1; i++) {
                params.addBodyParameter("image" + i, new File(list.get(i)));
            }
        }
        presenter.addPostIt(params);
    }

    @Override
    public void addPostItNext(PropertyBean.ListBean data) {
        switch (flag) {
            case 0:
                startActivity(new Intent(this, PropertyDetailsActivity.class)
                        .putExtra("postId", data.getKeepId()));
                break;
            case 1:
                startActivity(new Intent(this, NewPropertyDetailsActivity.class)
                        .putExtra("keepId", data.getKeepId()));
                break;
        }
        finish();
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog("false");
    }

    @Override
    public void hindLoading() {
        hideDialog();
    }

    @Override
    public void selectHouse(HouseListBean.ListBean bean) {
        listBean = bean;
        txTitle.setText(listBean.getCommunity_name() + listBean.getHouse_name());
    }

    @Override
    public void onDeletePicture(int position) {
        list.remove(position);
        adapter.setDataList(list);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * BrokeUpAdapter 点击回调
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == list.size() - 1 && list.size() <= 6) {
            window.showPopWindow(view);
            AppConfig.trackCustomEvent(this, "BrokeUpActivity_click", "选择添加图片");
        } else if (position == list.size() - 1 && list.size() == 7) {
            ToastUtils.showShort(this, "此次上传,最多只能上传6张照片!");
            return;
        }
    }

    /**
     * 弹框选择回调
     *
     * @param relationship
     */
    @Override
    public void relationship(String relationship) {
        switch (relationship) {
            case "1":
                PictureUtils.initOpenCamera(this);
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case "2":
                PictureUtils.initOpenAlbum(7 - list.size(), this);
                Intent intentAlbum = new Intent(this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                intentAlbum.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                startActivityForResult(intentAlbum, REQUEST_CODE_SELECT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                picSelectList = new ArrayList<>();
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem imageItem : images) {
                    String uuid = FileNameUtil.getrandom();
                    PictureUtils.getPicZipPath(this, imageItem.path, uuid);
                    picSelectList.add(uuid);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PropertyNewsActivity.this);
        StatService.trackBeginPage(PropertyNewsActivity.this, "添加物业报事");
        AnalyUtils.setBAnalyResume(this, "添加物业报事");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PropertyNewsActivity.this);
        StatService.trackEndPage(PropertyNewsActivity.this, "添加物业报事");
        AnalyUtils.setBAnalyPause(this, "添加物业报事");
    }

    @Override
    public void luBanFinish(String name) {
        for (String tempName : picSelectList) {
            if (name.contains(tempName)) {
                list.add(0, name);
            }
        }
        adapter.setDataList(list);
    }
}

package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.LoginBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.BrokeUpPresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.adapter.BrokeUpAdapter;
import com.jinke.community.ui.image.ImagePicker;
import com.jinke.community.ui.image.bean.ImageItem;
import com.jinke.community.ui.image.ui.ImageGridActivity;
import com.jinke.community.ui.toast.SelectAlbumWindow;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.utils.FileNameUtil;
import com.jinke.community.utils.PictureUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.IBrokeUpView;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.stat.StatService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

import static com.jinke.community.utils.PictureUtils.REQUEST_CODE_SELECT;

/**
 * Created by root on 17-8-6.
 * 小金妹爆料
 */
public class BrokeUpActivity extends BaseActivity<IBrokeUpView, BrokeUpPresenter>
        implements SelectHouseDialog.onSelectHouseListener, AdapterView.OnItemClickListener
        , IBrokeUpView, SelectAlbumWindow.OnSelectAlbumWindowListener, BrokeUpAdapter.BrokeUpAdapterlistener
        , CompoundButton.OnCheckedChangeListener, PictureUtils.LubanListener {
    @Bind(R.id.broken_GridView)
    FillGridView brokenGridView;
    @Bind(R.id.rl_select_house)
    RelativeLayout rlSelectHouse;
    @Bind(R.id.tx_chooes)
    TextView txChoose;
    @Bind(R.id.tx_address)
    TextView txAddress;
    @Bind(R.id.tx_house_owen)
    TextView txHouseOwen;
    @Bind(R.id.ed_property_content)
    EditText content;
    @Bind(R.id.tx_counter)
    TextView txCounter;
    @Bind(R.id.cb_nickName)
    CheckBox nickName;
    @Bind(R.id.cb_explain)
    CheckBox cbExplain;

    private BrokeUpAdapter adapter;
    private List<String> list = new ArrayList<>();
    private SelectAlbumWindow window;
    private SelectHouseDialog dialog;
    private HouseListBean.ListBean listBean;
    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息
    ArrayList<ImageItem> images = null;
    private List<String> picSelectList = new ArrayList<>();

    @Override
    public BrokeUpPresenter initPresenter() {
        return new BrokeUpPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_broke_up;
    }

    @Override
    protected void initView() {
        setTitle("爆料");
        showBackwardView("", true);
        showForwardView(R.string.broken_history, true);
        listBean = (HouseListBean.ListBean) getIntent().getSerializableExtra("listBean");
        showForwardViewColor(R.color.activity_broke_news_color);
        initEditLayout();
        initPictureList();
        getHouseList();
        window = new SelectAlbumWindow(this);
        window.setOnSelectAlbumWindowListener(this);
        dialog = new SelectHouseDialog(this, this);
        nickName.setChecked(true);
        nickName.setOnCheckedChangeListener(this);
        cbExplain.setChecked(true);
        cbExplain.setOnCheckedChangeListener(this);
        setDefaultHouse();
    }

    /**
     * 初始化图片列表
     */
    private void initPictureList() {
        list.add("a");
        adapter = new BrokeUpAdapter(this, list, this);
        brokenGridView.setAdapter(adapter);
        brokenGridView.setOnItemClickListener(this);
    }

    /**
     * 初始化文本框的监听
     */
    private void initEditLayout() {
        txCounter.setText("0/800");
        content.addTextChangedListener(new EmTextWatch(content, this));
        content.addTextChangedListener(new TextWatcher() {
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
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            presenter.getHouseList(map);
        } else {
            setDefaultHouse();
        }
    }

    /**
     * 获取房屋列表成功回调
     *
     * @param info
     */
    @Override
    public void getHouseListNext(HouseListBean info) {
        aCache.put("HouseListBean", info, ACache.TIME_DAY);
        houseListBean = info;
        setDefaultHouse();
    }

    public void setDefaultHouse() {
        if (houseListBean != null) {
            rlSelectHouse.setClickable(houseListBean.getList().size() > 1 ? true : false);
            txChoose.setVisibility(houseListBean.getList().size() > 1 ? View.VISIBLE : View.GONE);
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (bean.getDft_house() == 1) {   //默认选中,1-选中，0-没选中
                    if (listBean == null) {
                        listBean = bean;
                    }
                    txAddress.setText(listBean.getCommunity_name() + listBean.getHouse_name());
                    String phone = "";
                    if (!StringUtils.isEmpty(MyApplication.getBaseUserBean().getPhone()))
                        phone = StringUtils.isEmpty(MyApplication.getBaseUserBean().getPhone()) ? "" : TextUtils.changTelNum(MyApplication.getBaseUserBean().getPhone());
                    if (!StringUtils.isEmpty(MyApplication.getBaseUserBean().getNickName()))
                        txHouseOwen.setText(MyApplication.getBaseUserBean().getNickName() + "  " + phone);
                }
            }
            if (listBean != null) {
                nickName.setText("使用昵称(" + (StringUtils.isEmpty(MyApplication.getBaseUserBean().getNickName()) ? MyApplication.getBaseUserBean().getName() : MyApplication.getBaseUserBean().getNickName()) + ")");
            }
        }
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        startActivity(new Intent(BrokeUpActivity.this, BrokenPersonActivity.class));
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
        if (list != null && position == list.size() - 1 && list.size() <= 6) {
            window.showPopWindow(view);
            AppConfig.trackCustomEvent(this, "BrokeUpActivity_click", "选择添加图片");
        } else if (position == list.size() - 1 && list.size() == 7) {
            ToastUtils.showShort(this, "此次上传,最多只能上传6张照片!");
            return;
        }
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
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
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.rl_select_house, R.id.tx_broken_submit, R.id.tx_explain})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_house:
                dialog.show();
                break;

            case R.id.tx_broken_submit:
                if (listBean == null) {
                    ToastUtils.showShort(BrokeUpActivity.this, "请选择报事房屋！");
                    return;
                }

                if (StringUtils.isEmpty(content.getText().toString().trim())) {
                    ToastUtils.showShort(BrokeUpActivity.this, "请填写爆料内容！");
                    return;
                }

                if (!cbExplain.isChecked()) {
                    ToastUtils.showShort(BrokeUpActivity.this, "请阅读爆料服务声明");
                    return;
                }
                HashMap map = new HashMap();
                map.put("nickName", nickName.isChecked() ? MyApplication.getBaseUserBean().getNickName() : listBean.getOwners().get(0).getOwnerName());
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("communityId", listBean.getCommunity_id());
                map.put("houseName", listBean.getHouse_name());
                map.put("type", "1");
                map.put("content", content.getText().toString().trim());
                RequestParams params = HttpMethods.MapToParams(map);
                if (list.size() > 0) {
                    for (int i = 0; i < list.size() - 1; i++) {
                        params.addBodyParameter("image" + i, new File(list.get(i)));
                    }
                }
                presenter.getUpLoadFile(params);
                break;

            case R.id.tx_explain:
//                Intent payment = new Intent(this, DealActivity.class);
                Intent payment = new Intent(this, WebActivity.class);
                payment.putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_BREAK_EXPLAIN);
                payment.putExtra("title", "服务声明");
                startActivity(payment);
                break;
        }
    }

    /**
     * 上传成功回调
     *
     * @param bean
     */
    @Override
    public void onFileUpSuccess(LoginBean bean) {
        startActivity(new Intent(this, BrokenPersonActivity.class));
        finish();
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
    public void selectHouse(HouseListBean.ListBean bean) {
        listBean = bean;
        txAddress.setText(bean.getCommunity_name() + bean.getHouse_name());
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(BrokeUpActivity.this);
        StatService.trackBeginPage(BrokeUpActivity.this, "上传爆料");
        AnalyUtils.setBAnalyResume(this, "上传爆料");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BrokeUpActivity.this);
        StatService.trackEndPage(BrokeUpActivity.this, "上传爆料");
        AnalyUtils.setBAnalyPause(this, "上传爆料");
    }

    /**
     * 删除图片回调
     *
     * @param imageIndex
     */
    @Override
    public void onDeletePicture(int imageIndex) {
        list.remove(imageIndex);
        adapter.setDataList(list);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_nickName:
                DrawableUtils.setDrawableLeft(this, nickName, isChecked == true ? R.mipmap.icon_activity_break_new_selected : R.mipmap.icon_activity_break_new_un_select);
                break;
            case R.id.cb_explain:
                DrawableUtils.setDrawableLeft(this, cbExplain, isChecked == true ? R.mipmap.icon_activity_break_new_selected : R.mipmap.icon_activity_break_new_un_select);
                break;
        }
    }

    /**
     * 鲁班压缩回调
     *
     * @param name
     */
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
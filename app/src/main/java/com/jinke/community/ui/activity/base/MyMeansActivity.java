package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.MyMeansPresenter;
import com.jinke.community.ui.image.ImagePicker;
import com.jinke.community.ui.image.bean.ImageItem;
import com.jinke.community.ui.image.ui.ImageGridActivity;
import com.jinke.community.ui.toast.HeadOperaWindow;
import com.jinke.community.ui.toast.SelectAlbumWindow;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.utils.PictureUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.IMyMeansView;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.stat.StatService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-4.
 * 个人资料修改页面
 */

public class MyMeansActivity extends BaseActivity<IMyMeansView, MyMeansPresenter> implements SelectAlbumWindow.OnSelectAlbumWindowListener
        , IMyMeansView, HeadOperaWindow.OnSelectOperateWindowListener {
    @Bind(R.id.present_head_image)
    SimpleDraweeView presentHeadImage;
    @Bind(R.id.tx_owner)
    TextView txOwner;
    @Bind(R.id.tx_userName)
    TextView txUserName;
    @Bind(R.id.tx_phone)
    TextView txPhone;
    @Bind(R.id.tx_sex)
    TextView txSex;
    @Bind(R.id.ed_nickName)
    EditText edNickName;
    BaseUserBean baseUserBean;

    @Bind(R.id.tx_skill)
    TextView txSkill;
    @Bind(R.id.tx_interest)
    TextView txInterest;
    private HeadOperaWindow headOperaWindow;
    private String headUri = null;
    private static final int IMAGE_PICKER = 0x4444;
    private ArrayList<ImageItem> images = new ArrayList<>();
    private SelectAlbumWindow window;

    @Override
    public MyMeansPresenter initPresenter() {
        return new MyMeansPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_means;
    }

    @Override
    protected void initView() {
        setTitle("我的资料");
        showBackwardView("", true);
        showForwardView("保存", true);
        baseUserBean = MyApplication.getBaseUserBean();
        if (baseUserBean != null)
            headUri = baseUserBean.getAvatar();
        window = new SelectAlbumWindow(this);
        window.setCamera("男");
        window.setAlbum("女");
        //设置授权用户姓名输入限制
        edNickName.addTextChangedListener(new EmTextWatch(edNickName, this));
        initData();
    }

    private void initData() {
        presentHeadImage.setImageURI(baseUserBean.getAvatar());
        txPhone.setText(baseUserBean.getPhone());
        txUserName.setText(baseUserBean.getName());
        edNickName.setText(baseUserBean.getNickName());
        txOwner.setText(baseUserBean.getIdentity());
        txSex.setText(baseUserBean.getSex().equals("2") ? "女" : "男");
        window.setOnSelectAlbumWindowListener(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        if (StringUtils.isEmpty(edNickName.getText().toString().trim())) {
            ToastUtils.showShort(this, "昵称输入不能为空!");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("sex", sex + "");
        map.put("nickName", edNickName.getText().toString().trim());
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("accessToken", MyApplication.getBaseUserBean().getAccessToken());
//        params.addBodyParameter("sex", sex + "");
//        params.addBodyParameter("nickName", edNickName.getText().toString().trim());
//        params.addHeader("body_val", MyApplication.creatSign(map));
        RequestParams params = HttpMethods.MapToParams(map);
        if (!StringUtils.isEmpty(headUri) && !headUri.contains("http")) {
            params.addBodyParameter("image0", new File(headUri));
        }
        presenter.getAlterMeans(params);
    }

    /**
     * 更新用户信息成功回调
     *
     * @param msg
     */
    @Override
    public void getAlterMeansNext(String msg) {
        //修改本地保存信息
        BaseUserBean baseUserBean = MyApplication.getBaseUserBean();
        baseUserBean.setNickName(edNickName.getText().toString().trim());
        baseUserBean.setSex(String.valueOf(sex));
        baseUserBean.setAvatar(msg);
        if (StringUtils.isEmpty(baseUserBean.getAvatar()) ||
                StringUtils.isEmpty(baseUserBean.getIdentity()) ||
                StringUtils.isEmpty(baseUserBean.getName()))
            baseUserBean.setIsSuccess("false");
        else
            baseUserBean.setIsSuccess("true");
        SharedPreferencesUtils.saveBaseUserBean(this, baseUserBean);
        finish();
    }

    @OnClick({R.id.rl_sex, R.id.present_head_image, R.id.tx_interest, R.id.tx_skill})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sex:
                window.showPopWindow(view);
                break;

            case R.id.present_head_image:
                if (headOperaWindow == null)
                    headOperaWindow = new HeadOperaWindow(this);
                headOperaWindow.showPopWindow(view);
                headOperaWindow.setOnSelectOperateWindowListener(this);
                break;

            case R.id.tx_interest:// 技能skill 兴趣interest
                startActivity(new Intent(this, NoteActivity.class).putExtra("title", "interest"));
                break;

            case R.id.tx_skill://技能skill 兴趣interest
                startActivity(new Intent(this, NoteActivity.class).putExtra("title", "skill"));
                break;
        }
    }

    int sex = 1;

    @Override
    public void relationship(String relationship) {
        txSex.setText(relationship.equals("1") ? "男" : "女");
        sex = relationship.equals("1") ? 1 : 2;
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void showLoading() {
        showProgressDialog(null);
    }

    @Override
    public void hindLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MyMeansActivity.this);
        StatService.trackBeginPage(MyMeansActivity.this, "修改个人资料");
        AnalyUtils.setBAnalyResume(this, "修改个人资料");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MyMeansActivity.this);
        StatService.trackEndPage(MyMeansActivity.this, "修改个人资料");
        AnalyUtils.setBAnalyPause(this, "修改个人资料");
    }

    @Override
    public void operate(String operateCode) {
        switch (operateCode) {

            case "1"://更换头像
                PictureUtils.setHeadOperate(this);
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                startActivityForResult(intent, IMAGE_PICKER);
                break;

            case "2"://查看大图
                startActivity(new Intent(this, ImageShowActivity.class)
                        .putExtra("url", headUri));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                presentHeadImage.setImageURI(Uri.fromFile(new File(images.get(0).path)));
                headUri = String.valueOf(images.get(0).path);
            } else {
                ToastUtils.showShort(this, "没有数据");
            }
        }
    }
}

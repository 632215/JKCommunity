package com.jinke.community.ui.activity.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.utils.AnalyUtils;

import java.io.File;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/3/26.
 */

public class ImageShowActivity extends BaseActivity {
    @Bind(R.id.img_head)
    ImageView imgHead;
    private String url = null;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image_show;
    }

    @Override
    protected void initView() {
        showBackwardView("", true);
        setTitle("查看大图");
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(url)) {
            if (url.contains("http")) {
                Glide.with(this)
                        .load(url)
                        .asBitmap()
                        .placeholder(R.mipmap.icon_present_default_head)
                        .error(R.mipmap.icon_present_default_head)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imgHead.setImageBitmap(resource);
                            }
                        });
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(url);
                Glide.with(this)
                        .load(url)
                        .asBitmap()
                        .override(bitmap.getWidth(), bitmap.getHeight())
                        .placeholder(R.mipmap.icon_present_default_head)
                        .error(R.mipmap.icon_present_default_head)
                        .into(imgHead);
            }
        }

        AnalyUtils.setBAnalyResume(this, "图片展示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "图片展示");
    }
}

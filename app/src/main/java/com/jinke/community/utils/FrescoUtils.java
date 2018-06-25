package com.jinke.community.utils;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by root on 17-8-17.
 */

public class FrescoUtils {

    /**
     * 加载本地图片（drawable图片）
     * @param context
     * @param simpleDraweeView
     * @param id
     */
    public static void loadResPic(Context context, SimpleDraweeView simpleDraweeView, int id) {
        Uri uri = Uri.parse("res://" +
                context.getPackageName() +
                "/" + id);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载本地图片（assets图片）
     * @param context
     * @param simpleDraweeView
     * @param nameWithSuffix 带后缀的名称
     */
    public static void loadAssetsPic(Context context, SimpleDraweeView simpleDraweeView, String nameWithSuffix) {
        Uri uri = Uri.parse("asset:///" +
                nameWithSuffix);
        simpleDraweeView.setImageURI(uri);
    }
}

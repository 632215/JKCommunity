package com.jinke.community.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jinke.community.R;
import com.jinke.community.ui.image.ImageLoader;
import com.jinke.community.ui.image.ImagePicker;
import com.jinke.community.ui.image.view.CropImageView;

import java.io.File;
import java.io.IOException;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/4/4.
 */

public class PictureUtils {
    public static int REQUEST_CODE_SELECT = 1000;
    private static LubanListener listener;

    public static void initOpenCamera(LubanListener lis) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new JinKeImageLoader());   //设置图片加载器
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        listener = lis;
    }

    /**
     * 打开相册选择图片
     *
     * @param i
     */
    public static void initOpenAlbum(int i, LubanListener lis) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new JinKeImageLoader());   //设置图片加载器
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(i);              //选中数量限制
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setShowCamera(false);                     //不显示相机按钮
        listener = lis;
    }

    /**
     * 头像操作
     *
     * @param context
     */
    public static void setHeadOperate(Context context) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                Glide.with(activity)                             //配置上下文
                        .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.drawable.icon_fail_pic)           //设置错误图片
                        .placeholder(R.drawable.icon_fail_pic)     //设置占位图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                        .into(imageView);
            }

            @Override
            public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
                Glide.with(activity)                             //配置上下文
                        .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                        .into(imageView);
            }

            @Override
            public void clearMemoryCache() {

            }
        });
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    public static class JinKeImageLoader implements ImageLoader {
        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
            Glide.with(activity).load(path).error(R.drawable.icon_fail_pic)
                    .placeholder(R.drawable.icon_fail_pic)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        }

        @Override
        public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
            Glide.with(activity).load(path).error(R.drawable.icon_fail_pic)
                    .placeholder(R.drawable.icon_fail_pic)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
        }
    }

    /**
     * 根据图片路径压缩图片
     *
     * @param path
     */
    public static void getPicZipPath(final Context context, final String path, final String uuid) {
        File file = new File(path);
        if (file.isDirectory() || !file.exists()) {
            return;
        }
        if (Build.VERSION.SDK_INT > 23) {
            if (!file.exists() || file.isDirectory()) {
                return;
            }
        }
        if (file.exists()) {
            final File finalFile = file;
            if (context == null)
                return;
            Luban.with(context)
                    .load(file)//传人要压缩的图片
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            LogUtils.i("开始压缩" + finalFile.length());
                        }

                        @Override
                        public void onSuccess(File tempFile) {
                            LogUtils.e("压缩结束" + tempFile.length());
                            File lastFile = new File(tempFile.getAbsolutePath().substring(0, tempFile.getAbsolutePath().lastIndexOf("."))
                                    + path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")) + uuid + ".jpg");
                            if (lastFile.exists()) {
                                lastFile.delete();
                            }
                            try {
                                lastFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            tempFile.renameTo(lastFile);
                            if (listener != null) {
                                Log.e("32s压缩结束", tempFile.getAbsolutePath());
                                if (tempFile.exists()) {
                                    tempFile.delete();
                                }
                                listener.luBanFinish(lastFile.getAbsolutePath());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShort(context, "图片压缩失败");
                        }
                    }).launch();
        } else {
            ToastUtils.showShort(context, "文件损坏，请重新选择");
        }
    }

    public interface LubanListener {
        void luBanFinish(String name);
    }

}

package com.jinke.community.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.jinke.community.R;
import com.jinke.community.bean.ThemeBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.ui.widget.CustomRadioButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/3/30.
 */
public class ThemeUtils {
    public static void getThemeIcon(final Context mContext) {
        for (final ThemeBean bean : AppConfig.themelist) {
            String url = (StringUtils.equals(HttpMethods.BASE_URL, "https://api.tq-service.com/v2/tqapp/jk_community/uc/") ? AppConfig.THEME_URL
                    : AppConfig.DEV_THEME_URL) + bean.getValue();
            Glide.with(mContext)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存  
                    .skipMemoryCache(true)//跳过内存缓存
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            saveBitmap(resource, bean.getName(), mContext);
                        }
                    });
        }
    }

    /**
     * 保存Bitmap
     *
     * @param bitmap
     * @param fileName
     * @param mContext
     */
    public static void saveBitmap(Bitmap bitmap, String fileName, Context mContext) {
        if (bitmap != null) {
            File file = new File(AppConfig.PICTURE_ADDRESS + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                LogUtils.e("32s：保存失败" + fileName);
                e.printStackTrace();
            }
            LogUtils.e("32s：保存成功" + fileName);
            //            SharedPreferencesUtils.setIconState(mContext, true);
        } else
            LogUtils.e("32s：图片不存在");
    }

    public static void checkThemeIcon(Context context, int part, ImageView... images) {
        int partNum = part == 1 ? 0 : part == 2 ? 8 : 13;
        for (int x = 0; x < images.length; x++) {
            String filePath = AppConfig.PICTURE_ADDRESS + "/" + AppConfig.themelist.get(partNum + x).getName();
            if (new File(filePath).exists()) {
                BitmapDrawable bitmap = (BitmapDrawable) BitmapDrawable.createFromPath(filePath);
                images[x].setBackground(bitmap);
            }
        }
    }

    public static void checkThemeButton(Context context, CustomRadioButton... buttons) {
        for (int x = 0; x < buttons.length; x++) {
            String filePath = AppConfig.PICTURE_ADDRESS + "/" + AppConfig.themelist.get(2 * x).getName();
            String filePathPressed = AppConfig.PICTURE_ADDRESS + "/" + AppConfig.themelist.get(2 * x + 1).getName();
            if (new File(filePath).exists() && new File(filePathPressed).exists()) {
                StateListDrawable drawable = new StateListDrawable();
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_housekeeper_uncheck);
                int width = bitmap.getWidth();
                int highth = bitmap.getHeight();
//                int width = 70;
//                int highth = 70;
                Drawable defaultDrawable = Drawable.createFromPath(filePath);
                Drawable selectedDrawable = Drawable.createFromPath(filePathPressed);
                drawable.addState(new int[]{
                        android.R.attr.state_checked}, selectedDrawable);
                drawable.addState(new int[]{
                        -android.R.attr.state_checked}, defaultDrawable);
                drawable.setBounds(0, 0, width, highth);
                buttons[x].setCompoundDrawables(null, drawable, null, null);
            }
        }
    }

    public static BitmapDrawable getDrawable(String path) throws Exception {
        {
            //打开文件
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] bt = new byte[1024];
            //得到文件的输入流
            InputStream in = new FileInputStream(file);
            //将文件读出到输出流中
            int readLength = in.read(bt);
            while (readLength != -1) {
                outStream.write(bt, 0, readLength);
                readLength = in.read(bt);
            }
            //转换成byte 后 再格式化成位图
            byte[] data = outStream.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// 生成位图
            BitmapDrawable bd = new BitmapDrawable(bitmap);
            return bd;
        }
    }
}
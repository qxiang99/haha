package com.bintutu.shop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bintutu.shop.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * DESC :图片加载工具类.
 */
public class GlideUtil {


    public static void load(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url))
            Glide.with(context)
                    .load(url)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.image_bg)
                    .into(imageView);
    }

    public static void load(final Context context, String url, final ImageView imageView, final String name) {
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {

                    savaBitmap(context, AppConstant.IMAGE_DATAIL, name, bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    // 保存图片到手机指定目录
    public static void savaBitmap(Context context, String file, String imgName, byte[] bytes) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream fos = null;
            try {
                filePath = file;
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                String imgNames = filePath + "/" + imgName;
                fos = new FileOutputStream(imgNames);
                fos.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 保存图片到手机
    public static void download(final Context context, final String url) {
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                Log.e("download","url:"+url);
                String imgName = "";
                String[] aa = url.split("\\/");
                for (int i = 0; i < aa.length; i++) {
                    if (aa[i].endsWith("jpg")) {
                        imgName = aa[i];
                    }
                }
                String filePath = null;
                FileOutputStream fos = null;
                filePath = AppConstant.IMAGE_BANNER;
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                String imgNames = filePath + "/" + imgName;
                if (!(new File(imgNames)).exists()) {
                    try {
                        fos = new FileOutputStream(imgNames);
                        fos.write(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
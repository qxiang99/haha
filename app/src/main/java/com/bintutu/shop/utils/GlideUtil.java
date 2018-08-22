package com.bintutu.shop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bintutu.shop.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

    public static void load(Context context, int url, ImageView imageView) {
            Glide.with(context)
                    .load(url)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.image_bg)
                    .into(imageView);
    }

}
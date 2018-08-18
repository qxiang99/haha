package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import com.bintutu.shop.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GifDailog extends Dialog {


    @BindView(R.id.image)
    GifImageView image;
    private Context mContext;
    private GifDrawable gifDrawable;

    public GifDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_gif, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

    }

    public void StartGif() {
        try {
            gifDrawable = new GifDrawable(mContext.getResources(), R.drawable.scan);
            image.setImageDrawable(gifDrawable);//这里是实际决定资源的地方，优先级高于xml文件的资源定义
        } catch (IOException e) {
            e.printStackTrace();
        }
        gifDrawable.start();
    }


    public void StopGif() {
       if (gifDrawable!=null){
           gifDrawable.stop();
       }
    }




}

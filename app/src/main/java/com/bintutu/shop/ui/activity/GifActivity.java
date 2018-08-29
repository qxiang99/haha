package com.bintutu.shop.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GifActivity extends Activity {


    @BindView(R.id.image)
    GifImageView image;
    private GifDrawable gifDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        StartGif();
    }




    public void StartGif() {
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.scan);
            image.setImageDrawable(gifDrawable);//这里是实际决定资源的地方，优先级高于xml文件的资源定义
        } catch (IOException e) {
            e.printStackTrace();
        }
        gifDrawable.start();
    }


    public void StopGif() {
        if (gifDrawable != null) {
            gifDrawable.stop();
        }
    }


}

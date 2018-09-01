package com.bintutu.shop.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GifActivity extends Activity {


    @BindView(R.id.imageface)
    GifImageView imageface;
    @BindView(R.id.imageleft)
    GifImageView imageleft;
    @BindView(R.id.imageright)
    GifImageView imageright;
    @BindView(R.id.imagebottom)
    GifImageView imagebottom;
    private GifDrawable gifDrawable;
    private GifDrawable gifDrawableface;
    private GifDrawable gifDrawableleft;
    private GifDrawable gifDrawableright;
    private GifDrawable gifDrawablebottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {

        try {
            gifDrawableface = new GifDrawable(getResources(), R.drawable.scan_face);
            imageface.setImageDrawable(gifDrawableface);
            gifDrawableleft = new GifDrawable(getResources(), R.drawable.scan_left);
            imageleft.setImageDrawable(gifDrawableleft);
            gifDrawableright = new GifDrawable(getResources(), R.drawable.scan_right);
            imageright.setImageDrawable(gifDrawableright);
            gifDrawablebottom = new GifDrawable(getResources(), R.drawable.scan_bottom);
            imagebottom.setImageDrawable(gifDrawablebottom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int[] count = {0};

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                count[0] = 1 + count[0];

                Message message = new Message();
                message.what = count[0];
                mHandler.sendMessage(message);
            }
        }, 5000, 5000);
        startface();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int type = msg.what % 4;
            Log.e("mHandler", "...." + msg.what + "...." + type);

            if (type == 0) {
                startface();
            }
            if (type == 1) {
                startleft();
            }
            if (type == 2) {
                startright();
            }
            if (type == 3) {
                startbottom();
            }
        }
    };

    private void startface() {

        if (gifDrawablebottom != null) {
            gifDrawablebottom.stop();
        }
        imageface.setVisibility(View.VISIBLE);
        imageleft.setVisibility(View.GONE);
        imageright.setVisibility(View.GONE);
        imagebottom.setVisibility(View.GONE);
        gifDrawableface.start();


    }

    private void startleft() {

        if (gifDrawableface != null) {
            gifDrawableface.stop();
        }
        imageface.setVisibility(View.GONE);
        imageleft.setVisibility(View.VISIBLE);
        imageright.setVisibility(View.GONE);
        imagebottom.setVisibility(View.GONE);
        gifDrawableleft.start();
    }

    private void startright() {

        if (gifDrawableleft != null) {
            gifDrawableleft.stop();
        }
        imageface.setVisibility(View.GONE);
        imageleft.setVisibility(View.GONE);
        imageright.setVisibility(View.VISIBLE);
        imagebottom.setVisibility(View.GONE);
        gifDrawableright.start();
    }

    private void startbottom() {

        if (gifDrawableright != null) {
            gifDrawableright.stop();
        }
        imageface.setVisibility(View.GONE);
        imageleft.setVisibility(View.GONE);
        imageright.setVisibility(View.GONE);
        imagebottom.setVisibility(View.VISIBLE);
        gifDrawablebottom.start();
    }


    public void StopGif() {
        if (gifDrawable != null) {
            gifDrawable.stop();
        }
    }


}

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {

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
        }, 100, 1000);
        startface();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int type = msg.what % 80;
            Log.e("mHandler", "...." + msg.what + "...." + type);

            if (type >0&&type<21) {
                startface();
            }
            if (type >20&&type<41) {
                startleft();
            }
            if (type >40&&type<61) {
                startright();
            }
            if (type >60&&type<81) {
                startbottom();
            }
        }
    };

    private void startface() {
        try {
            GifDrawable gifDrawableface = new GifDrawable(getResources(), R.drawable.scan_face);
            imageface.setImageDrawable(gifDrawableface);
            gifDrawableface.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startleft() {
        try {
            GifDrawable gifDrawableleft = new GifDrawable(getResources(), R.drawable.scan_left);
            imageface.setImageDrawable(gifDrawableleft);
            gifDrawableleft.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startright() {
        try {
            GifDrawable gifDrawableright = new GifDrawable(getResources(), R.drawable.scan_right);
            imageface.setImageDrawable(gifDrawableright);
            gifDrawableright.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startbottom() {
        try {
            GifDrawable gifDrawablebottom = new GifDrawable(getResources(), R.drawable.scan_bottom);
            imageface.setImageDrawable(gifDrawablebottom);
            gifDrawablebottom.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* private void ImageGone(GifImageView image) {
        imageface.setVisibility(View.GONE);
        imageleft.setVisibility(View.GONE);
        imageright.setVisibility(View.GONE);
        imagebottom.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
    }*/



}

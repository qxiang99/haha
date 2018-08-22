package com.bintutu.shop.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.view.SmoothImageView;

import java.util.ArrayList;

public class SpaceImageDetailActivity extends Activity {

    private ArrayList<String> mDatas;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    SmoothImageView imageView = null;
    private int mResid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResid = getIntent().getIntExtra("images", 0);
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);
        imageView.setImageResource(mResid);
        // ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);
//		imageView.setImageResource(R.drawable.temp);
        // ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
        // 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        // 0.5f);
        // scaleAnimation.setDuration(300);
        // scaleAnimation.setInterpolator(new AccelerateInterpolator());
        // imageView.startAnimation(scaleAnimation);
      /*  Intent resultIntent = new Intent();
        resultIntent.putExtra("addtype", "1");
        resultIntent.putExtra("name", performer.getName());
        resultIntent.putExtra("citycode", performer.getCitycode());
        setResult(225, resultIntent);
        finish();*/
    }

    @Override
    public void onBackPressed() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

}

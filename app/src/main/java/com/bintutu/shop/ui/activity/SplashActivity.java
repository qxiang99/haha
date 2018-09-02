package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.ConfigManager;

public class SplashActivity extends BaseActivity {


    private Handler handler=new Handler();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
    }

    @Override
    protected void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartApp();
            }
        },2000);
    }

    @Override
    protected void setListener() {

    }


    private void StartApp() {
        if (ConfigManager.Device.isShowSplash()){
            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(SplashActivity.this,ShopLoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
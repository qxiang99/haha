package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.DensityUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,ReadyToScanActivity.class));

            }
        });

        findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Log.e("text2",DensityUtil.px2dp(MainActivity.this,88)+"...");
                Log.e("text2",DensityUtil.px2dp(MainActivity.this,90)+"...");

            }
        });
    }



    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ShowToast("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}

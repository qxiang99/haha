package com.bintutu.shop.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bintutu.shop.R;
import com.bintutu.shop.utils.DensityUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


}

package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;

public class FittingActivity extends BaseActivity {
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fitting);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FittingActivity.this, FitTestActivity.class));
            }
        });

    }
}

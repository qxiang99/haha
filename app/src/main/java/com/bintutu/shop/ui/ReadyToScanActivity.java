package com.bintutu.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.bintutu.shop.R;

public class ReadyToScanActivity extends BaseActivity {
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_readytoscan);
    }

    @Override
    protected void init() {
        final ConstraintLayout constraintLayout = findViewById(R.id.ready_con);
        findViewById(R.id.ready_but_startscan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //constraintLayout.setVisibility(View.VISIBLE);

                startActivity(new Intent(ReadyToScanActivity.this,DetailActivity.class));
            }
        });
    }

    @Override
    protected void setListener() {

    }
}

package com.bintutu.shop.ui.activity;


import android.os.Bundle;
import android.widget.Button;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.RecyclerCoverFlow;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mian_coverflow)
    RecyclerCoverFlow mReCverFlow;
    @BindView(R.id.ready_but_home)
    Button readyButHome;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }
}

package com.bintutu.shop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bintutu.shop.ShopApplication;

public abstract class BaseActivity extends AppCompatActivity {

    public ShopApplication mApp;
    //private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ShopApplication.getInstance();
        initContentView(savedInstanceState);
        //绑定控件
        //unbinder = ButterKnife.bind(this);
        init();
        setListener();
    }




    // 初始化UI，setContentView等
    protected abstract void initContentView(Bundle savedInstanceState);

    protected abstract void init();

    protected abstract void setListener();
}
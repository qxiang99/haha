package com.bintutu.shop;

import android.app.Application;

public class ShopApplication extends Application {


    private static ShopApplication mcontext;

    public static ShopApplication getInstance() {
        return mcontext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = this;
    }
}

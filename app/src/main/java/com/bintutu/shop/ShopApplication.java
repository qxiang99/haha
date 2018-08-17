package com.bintutu.shop;

import android.app.Application;

import com.bintutu.shop.utils.ToastUtils;

public class ShopApplication extends Application {


    private static ShopApplication mcontext;
    private ToastUtils toastUtils;

    public static ShopApplication getInstance() {
        return mcontext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = this;
    }

    public void ShowToast(String message) {
        toastUtils.showToast(message);
    }

    public String GetDebug() {
        return "true";
    }
}

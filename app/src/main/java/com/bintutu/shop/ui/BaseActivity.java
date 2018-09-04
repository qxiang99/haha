package com.bintutu.shop.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.bintutu.shop.ShopApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    public ShopApplication mApp;
    private Unbinder unbinder;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ShopApplication.getInstance();
        initContentView(savedInstanceState);
        //绑定控件
        unbinder = ButterKnife.bind(this);
        init();
        setListener();
    }




    // 初始化UI，setContentView等
    protected abstract void initContentView(Bundle savedInstanceState);

    protected abstract void init();

    protected abstract void setListener();


    /**
     * 通用弹出提示
     */
    public void ShowToast(String message) {
        mApp.ShowToast(message);
    }



    public void jumpLoading(String title) {
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(title);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }




    public void closeLoading() {
        if (dialog != null) dialog.dismiss();

    }
}
package com.bintutu.shop.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.CloseDailog;
import com.bintutu.shop.ui.view.RecyclerCoverFlow;
import com.bintutu.shop.ui.view.WifiDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mian_coverflow)
    RecyclerCoverFlow mReCverFlow;
    @BindView(R.id.main_lin_poweroff)
    LinearLayout mLinPowerOff;
    @BindView(R.id.main_lin_wifi)
    LinearLayout mLinWifi;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        initData();
    }


    @Override
    protected void setListener() {
        mLinPowerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDailog closeDailog = new CloseDailog(MainActivity.this);
                closeDailog.show();
            }
        });
        mLinWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiDailog wifiDailog = new WifiDailog(MainActivity.this);
                wifiDailog.show();
            }
        });
    }

    private void initData() {
        //登陆
        OkGo.<BaseResponse<String>>post("http://opzhpptsb.bkt.clouddn.com/image.json")
                .params("shop_id", "67901")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        Log.e("onSuccess",response.body()+"....");
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });
    }
}

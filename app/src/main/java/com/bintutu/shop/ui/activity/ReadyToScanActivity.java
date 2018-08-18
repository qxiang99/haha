package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.GifDailog;
import com.bintutu.shop.utils.AppConstant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadyToScanActivity extends BaseActivity {


    @BindView(R.id.ready_but_return)
    Button readyButReturn;
    @BindView(R.id.ready_but_home)
    Button readyButHome;
    @BindView(R.id.ready_but_startscan)
    Button readyButStartscan;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_readytoscan);
    }

    @Override
    protected void init() {
        //请求扫描仪是否在线
        OkGo.<BaseResponse<LoginBean>>get(AppConstant.LOGIN)
                .params("name", "")
                .execute(new JsonCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                    }

                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                    }
                });

    }

    @Override
    protected void setListener() {
        //返回
        readyButReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //首页
        readyButHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadyToScanActivity.this,MainActivity.class));
                finish();
            }
        });
        //开始扫描
        readyButStartscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startScan();
            }
        });

    }

    private void startScan() {
        //开启动画
        GifDailog gifDailog = new GifDailog(ReadyToScanActivity.this);
        gifDailog.show();
        gifDailog.StartGif();
        //发出扫描命令
        //循环请求结果
    }

}

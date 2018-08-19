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
import com.bintutu.shop.okgo.SimpleResponse;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.GifDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.DebugLog;
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
        OkGo.<BaseResponse<String>>get("http://192.168.12.1/getID")
                //.params("name", "")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......"+response.body());
                        if (response.body()!=null){

                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
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
               //startScan();
               // startActivity(new Intent(ReadyToScanActivity.this,DetailActivity.class));
                requestData();
            }
        });

    }

    private void startScan() {
        //开启动画
        GifDailog gifDailog = new GifDailog(ReadyToScanActivity.this);
        gifDailog.show();
        gifDailog.StartGif();
        //发出扫描命令
        ScanPost();
        //循环请求结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestData();
            }
        }).start();


    }

    private void ScanPost() {
        OkGo.<BaseResponse<String>>post("http://192.168.12.1/beginScan")
                .params("id","2018234223" )
                .params("begin", "1")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {

                    }
                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });




    }


    private void requestData() {
       OkGo.<BaseResponse<String>>post("http://192.168.12.1/requestData")
                .params("id","2018234223" )
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......"+response.body());
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });

/*
        OkGo.<BaseResponse<String>>get("http://192.168.12.1/awawaw/left.json")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......"+response.body());
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });

        OkGo.<BaseResponse<String>>get("http://192.168.12.1/awawaw/right.json")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......"+response.body());
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });*/
    }

}

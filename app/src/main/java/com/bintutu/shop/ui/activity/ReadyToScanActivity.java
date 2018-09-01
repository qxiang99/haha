package com.bintutu.shop.ui.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.bean.ScanBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.okgo.SimpleResponse;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.GifDailog;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadyToScanActivity extends BaseActivity {


    @BindView(R.id.ready_but_return)
    Button readyButReturn;
    @BindView(R.id.ready_but_home)
    Button readyButHome;
    @BindView(R.id.ready_but_startscan)
    Button readyButStartscan;
    private String scanNametime;
    private Gson gson;
    private GifDailog gifDailog;
    private int retry;
    private Timer timer;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_readytoscan);
    }

    @Override
    protected void init() {
        gifDailog = new GifDailog(ReadyToScanActivity.this);
        gson = new Gson();
        //请求扫描仪是否在线
        OkGo.<BaseResponse<String>>get(AppConstant.GET_ID)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......" + response.body());
                        if (response.body()!=null){
                            ConfigManager.Device.setEquipmentID(String.valueOf(response.body()));
                        }else {
                            ShowToast("设备不在线！！");
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
                startActivity(new Intent(ReadyToScanActivity.this, MainActivity.class));
                finish();
            }
        });
        //首页
        readyButHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadyToScanActivity.this, MainActivity.class));
                finish();
            }
        });
        //开始扫描
        readyButStartscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startScan();
                startActivity(new Intent(ReadyToScanActivity.this,GifActivity.class));

            }
        });

    }

    private void startScan() {
        retry = 0;
        //开启动画
        gifDailog.show();
        gifDailog.StartGif();
        //发送扫描命令加循环请求
        RequestScan();
    }

    private void RequestScan() {
        //发出扫描命令
        ScanPost();
        //循环请求结果
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                requestData();
            }
        }, 5000,10000);
    }

    private void ScanPost() {
        scanNametime = System.currentTimeMillis() + "";
        OkGo.<BaseResponse<String>>post(AppConstant.BEGIN_SCAN)
                .params("id", scanNametime)//名称
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
        //AppConstant.REQUEST_DATA
        OkGo.<BaseResponse<String>>post(AppConstant.REQUEST_DATA)
                .params("id", scanNametime)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        ScanBean scanBean = gson.fromJson(String.valueOf(response.body()), ScanBean.class);
                        DebugLog.e(scanBean.getResult()+"。。。。。1。。。。。");
                        if (scanBean.getResult() == 1) {
                            timer.cancel();
                            gifDailog.StopGif();
                            gifDailog.dismiss();
                            Intent intent = new Intent(ReadyToScanActivity.this, DetailActivity.class);
                            intent.putExtra(Constant.ItentKey1, scanNametime);
                            startActivity(intent);
                        } else if (scanBean.getResult() == 0) {
                            timer.cancel();
                            retry++;
                            if (retry<2){
                                //发送扫描命令加循环请求
                                RequestScan();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        timer.cancel();
                    }
                });


    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(ReadyToScanActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

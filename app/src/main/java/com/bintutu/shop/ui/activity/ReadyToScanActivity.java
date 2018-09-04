package com.bintutu.shop.ui.activity;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
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
    @BindView(R.id.ready_but_file)
    Button readyButFile;
    private String scanNametime;
    private Gson gson;
    private GifDailog gifDailog;
    private int retry;
    private Timer timer;
    private boolean scangif =false;

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
                            scangif=true;
                            readyButStartscan.setBackgroundResource(R.mipmap.start_scan);
                            ConfigManager.Device.setEquipmentID(String.valueOf(response.body()));
                        }else {
                            scangif = false;

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
                if (scangif){
                    Intent intent = new Intent(ReadyToScanActivity.this, GifActivity.class);
                    startActivityForResult(intent,300);
                }else {
                    ShowToast("设备不在线！！");
                }

            }
        });
        readyButFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFile();
            }
        });

    }

    private void startFile() {
     startActivity(new Intent(ReadyToScanActivity.this,PhotoViewActivity.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DebugLog.e("TAG", "result."+resultCode);
        if (resultCode==200){
            String result = data.getExtras().getString(Constant.ItentKey1);//得到新Activity 关闭后返回的数据

            Intent intent = new Intent(ReadyToScanActivity.this, DetailActivity.class);
            intent.putExtra(Constant.ItentKey1, result);
            startActivity(intent);

        }else if (resultCode==500){
                ShowToast("扫描出错");
        }


    }


}

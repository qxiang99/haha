package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.EventMsg;
import com.bintutu.shop.utils.RxBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import butterknife.BindView;


public class ReadyToScanActivity extends BaseActivity {


    @BindView(R.id.ready_but_return)
    Button readyButReturn;
    @BindView(R.id.ready_but_home)
    Button readyButHome;
    @BindView(R.id.ready_but_startscan)
    Button readyButStartscan;
    @BindView(R.id.ready_but_file)
    Button readyButFile;
    private boolean scangif = false;//判断设备是否在线 更换图标
    private String activtyname;
    private String activtyurl="";

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_readytoscan);
    }

    @Override
    protected void init() {
        //请求扫描仪是否在线
        JudgeOnlineScan();
        Intent intent = getIntent();
        activtyname = intent.getStringExtra(Constant.ItentKey1);
        if ("WebActivity".equals(activtyname)){
            activtyurl = intent.getStringExtra(Constant.ItentKey2);
        }
    }


    @Override
    protected void setListener() {
        //返回
        readyButReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("WebActivity".equals(activtyname)){
                    Intent intent = new Intent(ReadyToScanActivity.this, WebActivity.class);
                    intent.putExtra(Constant.ItentKey1, activtyurl);
                    startActivity(intent);
                }
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
                if (scangif) {
                    Intent intent = new Intent(ReadyToScanActivity.this, GifActivity.class);
                    startActivityForResult(intent, 300);
                } else {
                    ShowToast("设备不在线！！");
                }

            }
        });
        //
        readyButFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFile();
            }
        });

    }

    /**
     * GET
     * 请求扫描仪是否在线
     */
    private void JudgeOnlineScan() {
        OkGo.<BaseResponse<String>>get(AppConstant.GET_ID)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        if (response.body() != null) {
                            scangif = true;
                            readyButStartscan.setBackgroundResource(R.mipmap.start_scan_bg);
                            ConfigManager.Device.setEquipmentID(String.valueOf(response.body()));
                        } else {
                            scangif = false;
                            ShowToast("设备不在线！！");
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 查看截图保存的图片
     */
    private void startFile() {
        startActivity(new Intent(ReadyToScanActivity.this, PhotoViewActivity.class));
    }

    /**
     * GIF扫描页返回的数据
     * resultCode=200 扫描成功
     * resultCode=500 扫描出错
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200) {
            String result = data.getExtras().getString(Constant.ItentKey1);
            Intent intent = new Intent(ReadyToScanActivity.this, DetailActivity.class);
            intent.putExtra(Constant.ItentKey1, result);
            intent.putExtra(Constant.ItentKey2, activtyname);
            if ("WebActivity".equals(activtyname)){
                intent.putExtra(Constant.ItentKey3, activtyurl);
            }
            startActivity(intent);
            finish();

            EventMsg eventMsg = new EventMsg();
            eventMsg.setCode(500);
            RxBus.getInstance().post(eventMsg);
        } else if (resultCode == 500) {
            ShowToast("扫描出错");
        }
    }


}

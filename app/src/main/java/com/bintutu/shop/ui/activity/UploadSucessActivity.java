package com.bintutu.shop.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.UploadFittingBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.CutDown;
import com.bintutu.shop.utils.DebugLog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadSucessActivity extends BaseActivity {

    @BindView(R.id.sucess_text_number)
    TextView sucessTextNumber;
    @BindView(R.id.sucess_but_choose)
    Button sucessButChoose;
    @BindView(R.id.sucess_but_backhome)
    Button sucessButBackhome;
    @BindView(R.id.sucess_text_goscan)
    TextView sucessTextGoscan;
    @BindView(R.id.sucess_lin_goscan)
    LinearLayout sucessLinGoscan;
    @BindView(R.id.sucess_lin_fitting)
    LinearLayout sucessLinFitting;
    private CutDown cutDown;
    private Context mContext;
    private String footlen;
    private String uploadid;
    private Gson gson;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_uploadsucess);
    }

    @Override
    protected void init() {
        gson = new Gson();
        mContext = this;

        Intent intent = getIntent();
        uploadid = intent.getStringExtra(Constant.ItentKey1);
        footlen = intent.getStringExtra(Constant.ItentKey2);
        sucessTextNumber.setText("扫描编码：" + uploadid);
        sucessLinFitting.setEnabled(false);
        getFitting();
        Countdown();
    }


    @Override
    protected void setListener() {
        sucessButChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadSucessActivity.this, WebActivity.class);
                if (ConfigManager.Foot.getIdid() != null && !ConfigManager.Foot.getIdid().equals("")) {
                    intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_CHOOSE(
                            ConfigManager.Foot.getCustomer_id(),
                            ConfigManager.Foot.getCustomer_phone(),
                            ConfigManager.Foot.getIdid(),
                            ConfigManager.Foot.getchoosed_color_id(),
                            ConfigManager.Foot.getchoosed_fur_id(),
                            ConfigManager.Foot.getchoosed_sole_material_id(),
                            ConfigManager.Foot.getchoosed_sole_accessory_id(),
                            ConfigManager.Foot.getchoosed_exclusive_id()));
                } else {
                    intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_SORT(ConfigManager.Foot.getCustomer_id(), ConfigManager.Foot.getCustomer_phone()));
                }
                startActivity(intent);
                ConfigManager.Foot.setCustomer_id("");
                ConfigManager.Foot.setCustomer_phone("");
                ConfigManager.Foot.setIdid("");
                ConfigManager.Foot.setchoosed_fur_id("");
                ConfigManager.Foot.setchoosed_color_id("");
                ConfigManager.Foot.setchoosed_exclusive_id("");
                ConfigManager.Foot.setchoosed_sole_accessory_id("");
                ConfigManager.Foot.setchoosed_sole_material_id("");
                finish();
            }
        });
        sucessLinGoscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadSucessActivity.this, ReadyToScanActivity.class);
                intent.putExtra(Constant.ItentKey1, "UploadSucessActivity");
                startActivity(intent);
                finish();
            }
        });
        sucessButBackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigManager.Foot.setCustomer_id("");
                ConfigManager.Foot.setCustomer_phone("");
                ConfigManager.Foot.setIdid("");
                ConfigManager.Foot.setchoosed_color_id("");
                ConfigManager.Foot.setchoosed_exclusive_id("");
                ConfigManager.Foot.setchoosed_sole_accessory_id("");
                ConfigManager.Foot.setchoosed_sole_material_id("");
                startActivity(new Intent(UploadSucessActivity.this, MainActivity.class));
                finish();
            }
        });
        sucessLinFitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cutDown != null) {
                    cutDown.Stop();
                }

                Intent intent = new Intent(UploadSucessActivity.this, FittingActivity.class);
                intent.putExtra(Constant.ItentKey7, 2);
                intent.putExtra(Constant.ItentKey1, uploadid);
                intent.putExtra(Constant.ItentKey2, footlen);
                startActivity(intent);
                finish();

            }
        });
    }

    private void Countdown() {

        cutDown = new CutDown(60, new CutDown.OnCuDownListener() {
            @Override
            public void onNext(int time) {
                if (sucessTextGoscan != null) {
                    sucessTextGoscan.setTextColor(ContextCompat.getColor(UploadSucessActivity.this, R.color.text_color_c1));
                    sucessTextGoscan.setEnabled(false);
                    sucessTextGoscan.setText("继续扫描  ( " + time + " )");
                }
            }

            @Override
            public void onFinish() {
                if (sucessTextGoscan != null && mContext != null) {
                    Intent intent = new Intent(UploadSucessActivity.this, ReadyToScanActivity.class);
                    intent.putExtra(Constant.ItentKey1, "UploadSucessActivity");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable mThrowable) {

            }
        });
        cutDown.subscribeCutDown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
        if (cutDown != null) {
            cutDown.Stop();
        }

    }

    public void getFitting() {
        jumpLoading("请求数据");
        OkGo.<BaseResponse<String>>get(AppConstant.HAVE_FITTING)
                .params("shop_id", ConfigManager.Device.getShopID())
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        closeLoading();
                        String data = String.valueOf(response.body());
                        UploadFittingBean uploadBean = gson.fromJson(data, UploadFittingBean.class);
                        if (uploadBean != null & uploadBean.getCode() == 0) {
                            if (uploadBean.getResult()==1){
                                sucessLinFitting.setEnabled(true);
                            }

                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        super.onError(response);
                        closeLoading();
                    }
                });
    }
}

package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    @BindView(R.id.edit_male_iamge)
    ImageView editMaleIamge;
    @BindView(R.id.edit_female_iamge)
    ImageView editFemaleIamge;
    @BindView(R.id.edit_male)
    LinearLayout editMale;
    @BindView(R.id.edit_female)
    LinearLayout editFemale;
    @BindView(R.id.lin_forehand_tight_image)
    ImageView linForehandTightImage;
    @BindView(R.id.lin_forehand_tight)
    LinearLayout linForehandTight;
    @BindView(R.id.lin_forehand_centre_image)
    ImageView linForehandCentreImage;
    @BindView(R.id.lin_forehand_centre)
    LinearLayout linForehandCentre;
    @BindView(R.id.lin_forehand_image)
    ImageView linForehandImage;
    @BindView(R.id.lin_forehand_pine)
    LinearLayout linForehandPine;
    @BindView(R.id.lin_instep_tight_image)
    ImageView linInstepTightImage;
    @BindView(R.id.lin_instep_tight)
    LinearLayout linInstepTight;
    @BindView(R.id.lin_instep_centre_image)
    ImageView linInstepCentreImage;
    @BindView(R.id.lin_instep_centre)
    LinearLayout linInstepCentre;
    @BindView(R.id.lin_instep_image)
    ImageView linInstepImage;
    @BindView(R.id.lin_instep_pine)
    LinearLayout linInstepPine;


    private boolean scangif = false;//判断设备是否在线 更换图标


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_readytoscan);
    }

    @Override
    protected void init() {
        //请求扫描仪是否在线
        JudgeOnlineScan();

        editMaleIamge.setEnabled(true);
        editFemaleIamge.setEnabled(true);


        linForehandTightImage.setEnabled(false);
        linForehandCentreImage.setEnabled(true);
        linForehandImage.setEnabled(true);

        linInstepTightImage.setEnabled(false);
        linInstepCentreImage.setEnabled(true);
        linInstepImage.setEnabled(true);

    }


    @Override
    protected void setListener() {
        editMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMaleIamge.setEnabled(false);
                editFemaleIamge.setEnabled(true);

            }
        });
        editFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMaleIamge.setEnabled(true);
                editFemaleIamge.setEnabled(false);

            }
        });
        linForehandTight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linForehandTightImage.setEnabled(false);
                linForehandCentreImage.setEnabled(true);
                linForehandImage.setEnabled(true);
            }
        });
        linForehandCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linForehandTightImage.setEnabled(true);
                linForehandCentreImage.setEnabled(false);
                linForehandImage.setEnabled(true);
            }
        });
        linForehandPine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linForehandTightImage.setEnabled(true);
                linForehandCentreImage.setEnabled(true);
                linForehandImage.setEnabled(false);
            }
        });
        linInstepTight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linInstepTightImage.setEnabled(false);
                linInstepCentreImage.setEnabled(true);
                linInstepImage.setEnabled(true);
            }
        });

        linInstepCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linInstepTightImage.setEnabled(true);
                linInstepCentreImage.setEnabled(false);
                linInstepImage.setEnabled(true);
            }
        });
        linInstepPine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linInstepTightImage.setEnabled(true);
                linInstepCentreImage.setEnabled(true);
                linInstepImage.setEnabled(false);
            }
        });


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
            startActivity(intent);
            finish();

            EventMsg eventMsg = new EventMsg();
            eventMsg.setCode(500);
            RxBus.getInstance().post(eventMsg);
        } else if (resultCode == 500) {
            ShowToast("扫描出错");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

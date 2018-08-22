package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Constant;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadSucessActivity extends BaseActivity {

    @BindView(R.id.sucess_text_number)
    TextView sucessTextNumber;
    @BindView(R.id.sucess_but_goscan)
    Button sucessButGoscan;
    @BindView(R.id.sucess_but_choose)
    Button sucessButChoose;
    @BindView(R.id.sucess_but_backhome)
    Button sucessButBackhome;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_uploadsucess);
    }

    @Override
    protected void init() {

        Intent intent = getIntent();
        String number = intent.getStringExtra(Constant.ItentKey1);
        sucessTextNumber.setText("扫描编码："+number);
    }

    @Override
    protected void setListener() {
        sucessButChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadSucessActivity.this, MainActivity.class);
                intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_CHOOSE);
                startActivity(intent);
                finish();
            }
        });
        sucessButGoscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSucessActivity.this, ReadyToScanActivity.class));
                finish();
            }
        });
        sucessButBackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSucessActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}

package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FitTestActivity extends BaseActivity {
    @BindView(R.id.sucess_lin_fitting)
    LinearLayout sucessLinFitting;
    @BindView(R.id.sucess_lin_goscan)
    LinearLayout sucessLinGoscan;
    @BindView(R.id.fittest_but_choose)
    Button sucessButChoose;
    @BindView(R.id.fittest_but_backhome)
    Button sucessButBackhome;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fittest);
    }

    @Override
    protected void init() {
        sucessLinFitting.setEnabled(false);
        sucessLinGoscan.setEnabled(false);
    }

    @Override
    protected void setListener() {
        sucessButChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitTestActivity.this, WebActivity.class);
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
                startActivity(new Intent(FitTestActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}

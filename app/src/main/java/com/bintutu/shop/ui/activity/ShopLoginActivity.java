package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import butterknife.BindView;

public class ShopLoginActivity extends BaseActivity {

    @BindView(R.id.shoplogin_edit_phone)
    EditText mShopLoginEditPhone;
    @BindView(R.id.shoplogin_edit_pwd)
    EditText mShopLoginEditPwd;
    @BindView(R.id.shoplogin_but_submit)
    Button mShopLoginButSubmit;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shoplogin);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

        mShopLoginButSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

    }

    private void submit() {
        final String PHONE = mShopLoginEditPhone.getText().toString().trim();
        final String PWD = mShopLoginEditPwd.getText().toString().trim();

        if (TextUtils.isEmpty(PHONE)) {
            ShowToast("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(PWD)) {
            ShowToast("密码不能为空");
            return;
        }

        if (PWD.length()!=6) {
            ShowToast("密码长度不够");
            return;
        }

        if (!PHONE.endsWith(PWD)) {
            ShowToast("密码不正确");
            return;
        }


        //登陆
        OkGo.<BaseResponse<String>>post(AppConstant.SHOP_LOGIN)
                .params("phone", PHONE)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        ConfigManager.Device.setShopID("");
                        ConfigManager.Device.setShopPhone("");
                        startActivity(new Intent(ShopLoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });
    }
}

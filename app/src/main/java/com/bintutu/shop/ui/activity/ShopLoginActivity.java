package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.bean.ScanBean;
import com.bintutu.shop.bean.ShopLoginBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.DebugLog;
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
    private Gson gson;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shoplogin);
    }

    @Override
    protected void init() {
        gson = new Gson();
    }

    @Override
    protected void setListener() {

        mShopLoginButSubmit.setOnClickListener(new View.OnClickListener() {
            private long lastClick;
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() - lastClick <= 3000) {
                    return;
                }
                lastClick = System.currentTimeMillis();

                mShopLoginButSubmit.setEnabled(false);
                submit();
            }
        });

    }

    private void submit() {
        final String PHONE = mShopLoginEditPhone.getText().toString().trim();
        final String PWD = mShopLoginEditPwd.getText().toString().trim();

        if (TextUtils.isEmpty(PHONE)) {
            ShowToast("手机号不能为空");
            mShopLoginButSubmit.setEnabled(true);
            return;
        }
        if (TextUtils.isEmpty(PWD)) {
            ShowToast("密码不能为空");
            mShopLoginButSubmit.setEnabled(true);
            return;
        }

        if (PWD.length() != 6) {
            ShowToast("密码长度不够");
            mShopLoginButSubmit.setEnabled(true);
            return;
        }

        if (!PHONE.endsWith(PWD)) {
            ShowToast("密码不正确");
            mShopLoginButSubmit.setEnabled(true);
            return;
        }

        jumpLoading("登陆中...");
        //登陆
        OkGo.<BaseResponse<String>>get(AppConstant.SHOP_LOGIN)
                .params("phone", PHONE)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        closeLoading();
                        mShopLoginButSubmit.setEnabled(true);
                        ShopLoginBean shopLoginBean = gson.fromJson(String.valueOf(response.body()), ShopLoginBean.class);
                        DebugLog.e("shopLoginBean", (shopLoginBean.getResult() != null) + ".......");
                        if (shopLoginBean.getResult() != null) {
                            ShopLoginBean.ResultBean resultBean = shopLoginBean.getResult();
                            ShowToast("登录成功");
                            ConfigManager.Device.setShowSplash(true);
                            ConfigManager.Device.setShopID(resultBean.getId());
                            ConfigManager.Device.setShopPhone(resultBean.getPhone());
                            startActivity(new Intent(ShopLoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            ShowToast("登录失败");
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        closeLoading();
                        mShopLoginButSubmit.setEnabled(true);
                    }
                });
    }
}

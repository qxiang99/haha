package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginDailog extends Dialog {

    @BindView(R.id.login_edit_number)
    EditText mLoginEditNumber;
    @BindView(R.id.login_edit_phone)
    EditText mLoginEditPhone;
    @BindView(R.id.login_edit_code)
    EditText mLoginEditCode;
    @BindView(R.id.login_text_code)
    TextView mLoginTextCode;
    @BindView(R.id.login_but_submit)
    Button mLoginButSubmit;
    private Context mContext;

    public LoginDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_login, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        setListener();
    }

    private void setListener() {
        mLoginTextCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String PHONE = mLoginEditPhone.getText().toString().trim();

                if (TextUtils.isEmpty(PHONE)) {
                    ToastUtils.showToast(mContext, "手机号不能为空");
                    return;
                }

                //发送验证码
                OkGo.<BaseResponse<String>>post(AppConstant.VARIFICATIONCODE)
                        .params("phone", PHONE)
                        .execute(new JsonCallback<BaseResponse<String>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse<String>> response) {

                            }

                            @Override
                            public void onError(Response<BaseResponse<String>> response) {
                            }
                        });

            }
        });

        mLoginButSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Number = mLoginEditNumber.getText().toString().trim();
                final String PHONE = mLoginEditPhone.getText().toString().trim();
                final String code = mLoginTextCode.getText().toString().trim();
                if (TextUtils.isEmpty(Number)) {
                    ToastUtils.showToast(mContext, "编号不能为空");
                    return;
                }if (TextUtils.isEmpty(PHONE)) {
                    ToastUtils.showToast(mContext, "手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast(mContext, "验证码不能为空");
                    return;
                }


                //验证码登陆
                OkGo.<BaseResponse<String>>post(AppConstant.LOGIN)
                        .params("phone", PHONE)
                        .params("varification_code", code)
                        .execute(new JsonCallback<BaseResponse<String>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse<String>> response) {

                            }

                            @Override
                            public void onError(Response<BaseResponse<String>> response) {
                            }
                        });

            }
        });
    }



    private OnLoginClickListener mListener;

    public void seLogintListener(OnLoginClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnLoginClickListener{
            void Data(String number,String phone,String customer_id);

    }
}
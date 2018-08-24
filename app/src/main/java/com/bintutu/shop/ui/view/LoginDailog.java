package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.CutDown;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.ToastUtils;
import com.google.gson.Gson;
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
    @BindView(R.id.login_view3)
    View loginView3;
    private Context mContext;
    private CutDown cutDown;

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


                getCode();

                //发送验证码
                OkGo.<BaseResponse<LoginBean>>post(AppConstant.VARIFICATIONCODE)
                        .params("phone", PHONE)
                        .execute(new JsonCallback<BaseResponse<LoginBean>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                                DebugLog.e(".......");
                                ToastUtils.showToast(mContext, "发送成功");
                            }

                            @Override
                            public void onError(Response<BaseResponse<LoginBean>> response) {
                                super.onError(response);
                            }
                        });

            }
        });

        mLoginButSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Number = mLoginEditNumber.getText().toString().trim();
                final String PHONE = mLoginEditPhone.getText().toString().trim();
                final String code = mLoginEditCode.getText().toString().trim();
                if (TextUtils.isEmpty(Number)) {
                    ToastUtils.showToast(mContext, "编号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(PHONE)) {
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
                                Gson gson = new Gson();
                                String data = String.valueOf(response.body());
                                LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                                if (loginBean != null & loginBean.getCode() == 0) {
                                    ToastUtils.showToast(mContext, "登录成功");
                                    if (mListener != null) {
                                        mListener.Data(Number, PHONE, loginBean.getResult().getCustomer_id());
                                    }
                                    dismiss();
                                }

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

    public void getCode() {
        cutDown = new CutDown(60, new CutDown.OnCuDownListener() {
            @Override
            public void onNext(int time) {
                if (mLoginTextCode != null) {
                    mLoginTextCode.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_bf));
                    loginView3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.text_color_bf));
                    mLoginTextCode.setEnabled(false);
                    mLoginTextCode.setText("重新获取(" + time + ")");
                }
            }

            @Override
            public void onFinish() {
                if (mLoginTextCode != null) {
                    mLoginTextCode.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color));
                    loginView3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_color));
                    mLoginTextCode.setEnabled(true);
                    mLoginTextCode.setText("获取验证码");
                }
            }

            @Override
            public void onError(Throwable mThrowable) {

            }
        });
        cutDown.subscribeCutDown();
    }

    public interface OnLoginClickListener {
        void Data(String number, String phone, String customer_id);

    }

    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showToast(mContext, "再按一次退出登录");
            mExitTime = System.currentTimeMillis();
        } else {
            if (cutDown!=null){
                cutDown.Stop();
            }
            dismiss();
        }
    }

}
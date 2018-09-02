package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bintutu.shop.R;
import com.bintutu.shop.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiDailog extends Dialog {

    private Context mContext;
    @BindView(R.id.wifi_edit_name)
    EditText mWifiEditName;
    @BindView(R.id.wifi_edit_pwd)
    EditText mWifiEditPwd;
    @BindView(R.id.wifi_but_diss)
    Button mWifiButDiss;
    @BindView(R.id.wifi_but_confirm)
    Button mWifiButConfirm;

    public WifiDailog(Context context) {
        super(context, R.style.close_dialog);
        mContext=context;
        //加载布局文件
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.wifi_dialog, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setListener();
    }

    private void setListener() {
        mWifiButDiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mWifiButConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = mWifiEditName.getText().toString().trim();
                final String Pwd = mWifiEditPwd.getText().toString().trim();
                if (TextUtils.isEmpty(Name)) {
                    ToastUtils.showToast(mContext, "Wi-Fi名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(Pwd)) {
                    ToastUtils.showToast(mContext, "秘密不能为空");
                    return;
                }


                if (mSetClickListener!=null){
                    mSetClickListener.onSetData(Name,Pwd);
                }
                mWifiEditName.setText("");
                mWifiEditPwd.setText("");
                dismiss();

            }
        });
    }

    private OnSetClickListener mSetClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }


    public interface OnSetClickListener {
        void onSetData(String name, String pwd);
    }


}
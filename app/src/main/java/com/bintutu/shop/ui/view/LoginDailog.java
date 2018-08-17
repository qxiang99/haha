package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.bintutu.shop.R;
import butterknife.ButterKnife;

public class LoginDailog extends Dialog {


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
    }



   /* private OnOpenClickListener mListener;

    public void setListener(OnOpenClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnOpenClickListener{
        void PullData(PullBean pullBean);

    }*/
}
package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.bintutu.shop.R;

import butterknife.ButterKnife;

public class CommanDailog extends Dialog {

    private Context mContext;

    public CommanDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_commandailog, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setListener();
    }

    private void setListener() {

    }

}
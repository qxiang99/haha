package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.bintutu.shop.R;

public class EditDailog extends Dialog {
    private Context mContext;


    public EditDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);

        mContext = context;
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.edit_dailog, null, false);
        setContentView(inflate);
        initView();
    }


    private void initView() {

    }
}


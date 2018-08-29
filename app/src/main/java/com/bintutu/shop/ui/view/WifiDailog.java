package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bintutu.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiDailog extends Dialog {

    private Context context;


    public WifiDailog(Context context) {
        super(context, R.style.close_dialog);
        this.context=context;
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

    }

    private void ConFirm() {
        //关闭扫描仪
    }


}
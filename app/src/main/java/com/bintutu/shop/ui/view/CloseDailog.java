package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintutu.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CloseDailog extends Dialog {

    private Context context;
    @BindView(R.id.close_but_diss)
    Button mCloseButDiss;
    @BindView(R.id.close_but_confirm)
    Button mCloseButConFirm;

    public CloseDailog(Context context) {
        super(context, R.style.close_dialog);
        this.context=context;
        //加载布局文件
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.close_dialog, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setListener();
    }

    private void setListener() {
        mCloseButDiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mCloseButConFirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConFirm();
            }
        });
    }

    private void ConFirm() {
        //关闭扫描仪
    }


}
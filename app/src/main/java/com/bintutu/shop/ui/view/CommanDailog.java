package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.FootTagBean;
import com.bintutu.shop.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommanDailog extends Dialog {

    private Context mContext;

    @BindView(R.id.command_edit_pwd)
    EditText mCommandEditPwd;
    @BindView(R.id.command_but_submit)
    Button mCommandButSubmit;
    @BindView(R.id.command_close)
    Button mCommandClose;

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
        mCommandClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mCommandButSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String PWD = mCommandEditPwd.getText().toString().trim();

                if (TextUtils.isEmpty(PWD)) {
                    ToastUtils.showToast(mContext, "口令不能为空");
                    return;
                }

                if (mSetClickListener!=null){
                    mSetClickListener.onSetData(PWD);
                }
                mCommandEditPwd.setText("");
                dismiss();
            }
        });
    }



    private OnSetClickListener mSetClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }


    public interface OnSetClickListener {
        void onSetData(String command);
    }

}
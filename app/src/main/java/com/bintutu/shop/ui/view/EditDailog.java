package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDailog extends Dialog {
    private Context mContext;

    @BindView(R.id.edit_input)
    EditText mEditInput;
    @BindView(R.id.edit_but_submit)
    Button mEditButSumbit;
    @BindView(R.id.edit_but_dismiss)
    Button mEditButDismiss;

    public EditDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);

        mContext = context;
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.edit_dailog, null, false);
        ButterKnife.bind(this, inflate);
        setContentView(inflate);
        initView();
    }

    private void initView() {
        mEditButDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mEditButSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String edit = mEditInput.getText().toString().trim();
                if (TextUtils.isEmpty(edit)) {
                    ToastUtils.showToast(mContext, "请输入描述信息");
                    return;
                }
                if(mEditClickListener!=null){
                    mEditClickListener.onSetData(edit);
                    mEditInput.setText("");
                }
            }
        });

    }

    private OnEditClickListener mEditClickListener;

    public void setEditClickListener(OnEditClickListener mEditClickListener) {
        this.mEditClickListener = mEditClickListener;
    }

    public interface OnEditClickListener {
        void onSetData(String edit);
    }
}


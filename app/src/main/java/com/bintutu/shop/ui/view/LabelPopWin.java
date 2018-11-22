package com.bintutu.shop.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.adapter.DefaultAdapter;
import com.bintutu.shop.ui.adapter.FittingAdapter;
import com.bintutu.shop.ui.adapter.LabelAdapter;

import java.util.ArrayList;
import java.util.List;

public class LabelPopWin extends PopupWindow {

    private Context mContext;
    private final RecyclerView mRecyclerview;
    private List<String> list = new ArrayList<>();
    public LabelPopWin(Context context) {
        super(context);
        mContext = context;
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.label_popwin, null, false);
        setContentView(inflate);
        mRecyclerview = inflate.findViewById(R.id.recyclerview);
        initView();
    }

    private void initView() {
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xfff8f1e6);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        list.add("MO235M1");
        list.add("MO240M1");
        list.add("MO240L1");
        list.add("MO240S1");
        list.add("MO245M1");
        list.add("MO245L1");
        list.add("MO245S1");
        list.add("MO250M1");
        list.add("MO250L1");
        list.add("MO250S1");
        list.add("MO255M1");
        list.add("MO255L1");
        list.add("MO255S1");
        list.add("MO260M1");
        list.add("MO260L1");
        list.add("MO260S1");
        list.add("MO265M1");
        list.add("MO265L1");
        list.add("MO265S1");
        list.add("MO270M1");
        list.add("MO270L1");
        list.add("MO270S1");
        list.add("MO275M1");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        LabelAdapter labelAdapter = new LabelAdapter(list);
        mRecyclerview.setAdapter(labelAdapter);
        labelAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                dismiss();
                if (mOnMClickListner!=null){
                    mOnMClickListner.onmClick(String.valueOf(data));

                }
            }
        });
    }

    private OnMClickListener mOnMClickListner;

    public void setOnMClickListner (OnMClickListener onConfirmClickListner) {
        mOnMClickListner = onConfirmClickListner;
    }

    public interface OnMClickListener {
        void onmClick (String result);
    }
}

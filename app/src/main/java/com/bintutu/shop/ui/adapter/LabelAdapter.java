package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bintutu.shop.R;

import java.util.List;

import butterknife.BindView;

public class LabelAdapter extends DefaultAdapter<String> {


    private Context mContext;
    private List<String> newData;

    public LabelAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new OrderListItem(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_label;
    }

    public void setNewData(List<String> newData) {
        updateList(newData);
    }

    protected class OrderListItem extends BaseHolder<String> {
        @BindView(R.id.lable_text)
        TextView labletext;

        protected OrderListItem(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }

        @Override
        public void setData(final String data, final int position) {
            labletext.setText(data);
        }

        @Override
        protected void onRelease() {
            super.onRelease();
            System.gc();
        }
    }

    public OnSetClickListener mSetClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }

    public interface OnSetClickListener {
        void onSetData( List<String> infos);
    }
}
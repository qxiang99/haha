package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.DetailBean;

import java.util.List;

import butterknife.BindView;

public class DetailAdapter extends DefaultAdapter<DetailBean> {


    private Context mContext;

    public DetailAdapter(List<DetailBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<DetailBean> getHolder(View v, int viewType) {
        return new OrderListItem(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_detail;
    }

    protected class OrderListItem extends BaseHolder<DetailBean> {
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_text_name)
        TextView itemTextName;
        @BindView(R.id.item_text_left)
        TextView itemTextLeft;
        @BindView(R.id.item_text_right)
        TextView itemTextRight;
        @BindView(R.id.item_lin)
        LinearLayout itemLin;
        @BindView(R.id.item_lin2)
        LinearLayout itemLin2;
        @BindView(R.id.item_text_number)
        TextView itemTextNumber;

        protected OrderListItem(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }

        @Override
        public void setData(final DetailBean data, final int position) {
            if (position != 0) {
                itemTitle.setVisibility(View.GONE);
                itemLin2.setVisibility(View.GONE);
            }
            if (position % 2 == 0) {
                itemLin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                itemLin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_color_two));
            }
            itemTextNumber.setText(Integer.toString(data.getNumber()));
            itemTextName.setText(data.getName());
            itemTextLeft.setText(data.getLeft());
            itemTextRight.setText(data.getRight());
        }

        @Override
        protected void onRelease() {
            super.onRelease();
            //mLlItem.setBackgroundColor(0);
            System.gc();
        }
    }

   /* public void addInformation(List<DataPersonBean.ListBean> mList) {
        updateList(mList);
    }

    private OnSetClickListener mSetClickListener;
    private OnDelClickListener mDelClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }

    public interface OnSetClickListener {
        void onSetData(DataPersonBean.ListBean data);
    }


    public void setDelClickListener(OnDelClickListener mDelClickListener) {
        this.mDelClickListener = mDelClickListener;
    }

    public interface OnDelClickListener {
        void onDelData(DataPersonBean.ListBean data);
    }*/
}

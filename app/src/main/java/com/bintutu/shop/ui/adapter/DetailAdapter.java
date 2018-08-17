package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.view.View;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.DetailBean;
import java.util.List;

public class DetailAdapter  extends DefaultAdapter<DetailBean> {


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



        protected OrderListItem(View itemView) {
            super(itemView);
            mContext = itemView.getContext();

        }

        @Override
        public void setData(final DetailBean data, final int position) {


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

package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.FittingBean;

import java.util.List;

import butterknife.BindView;

public class FittingAdapter extends DefaultAdapter<FittingBean> {


    private Context mContext;
    private List<FittingBean> newData;

    public FittingAdapter(List<FittingBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<FittingBean> getHolder(View v, int viewType) {
        return new OrderListItem(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fitting;
    }

    public void setNewData(List<FittingBean> newData) {
        updateList(newData);
    }

    protected class OrderListItem extends BaseHolder<FittingBean> {
        @BindView(R.id.item_text_number)
        TextView itemTextNumber;
        @BindView(R.id.item_lin)
        LinearLayout itemLin;

        @BindView(R.id.item_text_one)
        TextView itemTextOne;
        @BindView(R.id.item_text_two)
        TextView itemTextTwo;
        @BindView(R.id.item_text_three)
        TextView itemTextThree;

        protected OrderListItem(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }

        @Override
        public void setData(final FittingBean data, final int position) {
            itemTextNumber.setText("区域  "+" "+data.getName());
            if (position % 2 == 0) {
                itemLin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                itemLin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_color_two));
            }

            if (data.getType()==0){
                SetType(0);
            }
            if (data.getType()==1){
                SetType(1);
            }
            if (data.getType()==2){
                SetType(2);
            }
            itemTextOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetType(0);
                    data.setType(0);
                    if (mSetClickListener!=null){
                        mSetClickListener.onSetData(data,getInfos());
                    }
                }
            });
            itemTextTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetType(1);
                    data.setType(1);
                    if (mSetClickListener!=null){
                        mSetClickListener.onSetData(data, getInfos());
                    }
                }
            });
            itemTextThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetType(2);
                    data.setType(2);
                    if (mSetClickListener!=null){
                        mSetClickListener.onSetData(data, getInfos());
                    }
                }
            });

        }

        private void SetType(int type) {
            itemTextOne.setEnabled(true);
            itemTextTwo.setEnabled(true);
            itemTextThree.setEnabled(true);
            if (type==0){
                itemTextOne.setEnabled(false);
            }
            if (type==1){
                itemTextTwo.setEnabled(false);
            }
            if (type==2){
                itemTextThree.setEnabled(false);
            }
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
        void onSetData(FittingBean data, List<FittingBean> infos);
    }
}

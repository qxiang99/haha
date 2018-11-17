package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bintutu.shop.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class CoverFlowAdapter extends RecyclerView.Adapter<CoverFlowAdapter.ViewHolder> {

    private Context mContext;

    private onItemClick clickCb;
    private List<String> mdetailList;

    public CoverFlowAdapter(Context c, List<String> detailList) {
        mdetailList = detailList;
        mContext = c;
    }

    public CoverFlowAdapter(Context c, onItemClick cb) {
        mContext = c;
        clickCb = cb;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.coverflow_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mdetailList.size()!=0){
            Glide.with(mContext).load(mdetailList.get(position % mdetailList.size())).into(holder.img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                if (clickCb != null) {
                    clickCb.clickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public void setItem(int item) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    interface onItemClick {
        void clickItem(int pos);
    }
}

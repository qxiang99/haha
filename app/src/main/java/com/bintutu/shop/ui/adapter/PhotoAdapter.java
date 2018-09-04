package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.bintutu.shop.R;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.List;
import butterknife.BindView;



public class PhotoAdapter extends DefaultAdapter<String> {


    private Context mContext;

    public PhotoAdapter(List<String> infos) {
        super(infos);
    }



    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new OrderListItem(v);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.photo_item;
    }



    protected class OrderListItem extends BaseHolder<String> {

        @BindView(R.id.image)
        ImageView mImage;

        protected OrderListItem(View itemView) {
            super(itemView);
            mContext = itemView.getContext();

        }

        @Override
        public void setData(final String data, final int position) {
            Glide.with(itemView.getContext()).load(new File(data)).asBitmap().into(mImage);

            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSetClickListener !=null){
                        mSetClickListener.onSetData(data,position);
                    }
                }
            });

        }

        @Override
        protected void onRelease() {
            super.onRelease();
            System.gc();
        }
    }




    public void addInformation(List<String> mList) {
        updateList(mList);
    }

    private OnSetClickListener mSetClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }

    public interface OnSetClickListener {
        void onSetData(String data,int position);
    }

}

package com.bintutu.shop.ui.adapter;

public class DetailAdapter  /*extends DefaultAdapter<DetailBean> {

    public OnItemClickListener mListener;

    public FeedBackAdapter(List<DetailBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Feedback> getHolder(View v, int viewType) {
        return new VH(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_feedback;
    }

    public class VH extends BaseHolder<Feedback> {
        @BindView(R.id.item_layout)
        ConstraintLayout mItemLayout;
        @BindView(R.id.item_title)
        TextView mItemTitle;
        @BindView(R.id.item_badge)
        CheckBox mItemBadge;
        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(final Feedback data, final int position) {
            mItemTitle.setText(data.getName());
            mItemBadge.setChecked(data.isChecked());
            mItemTitle.setEnabled(data.isChecked());
            if (data.isChecked()){
                //虽然叫蓝色 实际为红色
                mItemLayout.setBackgroundResource(R.drawable.feedback_blue_line);
            }else {
                mItemLayout.setBackgroundResource(R.drawable.feedback_gray_line);
            }
            mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null){
                        return;
                    }
                    if (!mItemBadge.isChecked()){
                        mListener.onItemChecked(data,position);
                        changeOtherTypeState(position);

                    }else {

                        mListener.onItemCancel(data,position);
                        cancelChecked();
                        DebugLog.e("取消选中>>>>>>>>>>>.");
                    }
                }
            });

        }
        //更改其他问题类型的状态
        private void changeOtherTypeState(int  position){
            for (int i = 0; i < getInfos().size(); i++) {
                if ((position == i)){
                    getInfos().get(i).setChecked(true);
                }else {
                    getInfos().get(i).setChecked(false);
                }
            }
            notifyDataSetChanged();
        }
        //取消选中
        private void cancelChecked(){
            for (int i = 0; i < getInfos().size(); i++) {
                getInfos().get(i).setChecked(false);
            }
            notifyDataSetChanged();
        }
    }

    public OnItemClickListener getListener() {
        return mListener;
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener{
        void onItemChecked(Feedback string,int postion);
        void onItemCancel(Feedback string,int postion);
    }

*/{
}

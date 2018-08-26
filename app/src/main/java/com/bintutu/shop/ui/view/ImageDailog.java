package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.TAGBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDailog extends Dialog {


   @BindView(R.id.imag_picture)
   PictureTagLayout picture;

    private Context mContext;
    private ImageView mView;
    private int mWidth;
    private int mHidth;


    public ImageDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);

        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_image, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setListener();

    }



    private void setListener() {
        picture.setSetClickListener(new PictureTagLayout.OnSetClickListener() {
            @Override
            public void onSetData(Bitmap viewBitmap,  TAGBean tagBean) {
                if (mImageClickListener!=null){
                    mImageClickListener.onSetData(mView,viewBitmap,tagBean);
                }
            }
        });
    }


    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public void setImage(String name, ImageView view, Bitmap imageRe) {
        mView = view;
        picture.setImage(name,imageRe);

    }


    private OnImageClickListener mImageClickListener;

    public void setImageClickListener(OnImageClickListener mImageClickListener) {
        this.mImageClickListener = mImageClickListener;
    }


    public interface OnImageClickListener {
        void onSetData(ImageView view, Bitmap viewBitmap,TAGBean tagBean);
    }

}

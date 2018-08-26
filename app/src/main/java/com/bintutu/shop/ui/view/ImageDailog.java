package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.TAGBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDailog extends Dialog {


   @BindView(R.id.imag_picture)
   PictureTagLayout picture;
   @BindView(R.id.imag_back)
   Button imagBack;

    private Context mContext;
    private ImageView mView;
    private int mWidth;
    private int mHidth;
    private TAGBean mtagBean;


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
                mtagBean = tagBean;
            }
        });

        imagBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mImageClickListener!=null&&mtagBean!=null){
                    mImageClickListener.onSetData(mView,createViewBitmap(picture),mtagBean);
                }
            }
        });
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            if (mImageClickListener!=null&&mtagBean!=null){
                mImageClickListener.onSetData(mView,createViewBitmap(picture),mtagBean);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
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

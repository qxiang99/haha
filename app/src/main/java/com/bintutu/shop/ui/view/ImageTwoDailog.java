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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageTwoDailog extends Dialog {


    @BindView(R.id.imagetwo_image_img)
    TagLayout picture;

    private Context mContext;
    private ImageView mView;
    private int mWidth;
    private int mHidth;
    private TAGBean mtagBean;


    public ImageTwoDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_imagetwo, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setListener();

    }



    private void setListener() {

    }


    public void setImage(String name, ImageView view, Bitmap imageRe) {
        picture.setImage(name,imageRe);
    }
}
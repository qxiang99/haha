package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bintutu.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDailog extends Dialog {


   @BindView(R.id.imag_picture)
   PictureTagLayout picture;

    private Context mContext;


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

    }

    public void setImage(int Rid) {
        picture.setImage(Rid);

    }

}

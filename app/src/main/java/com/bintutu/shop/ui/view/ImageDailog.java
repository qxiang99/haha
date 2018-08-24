package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bintutu.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDailog extends Dialog {


    @BindView(R.id.image_img)
    ImageView imageImg;
    @BindView(R.id.image_pic)
    PictureTagLayout imagePic;
    private Context mContext;
    private ImageView mIamge;

    public ImageDailog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        imageImg.setImageResource(Rid);
        //Bitmap bitmap = createViewBitmap(imageRel);
        //image.setImageBitmap(bitmap);
    }


    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}

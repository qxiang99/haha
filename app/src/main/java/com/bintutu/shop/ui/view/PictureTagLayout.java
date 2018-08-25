package com.bintutu.shop.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.utils.DebugLog;

@SuppressLint("NewApi")
public class PictureTagLayout extends RelativeLayout implements OnTouchListener{

    int startX = 0;
    int startY = 0;
    private Context mContext;
    private ImageView PictureImage;
    private RelativeLayout PictureRel;
    private View LayoutPicture;

    public PictureTagLayout(Context context) {
        super(context, null);
    }
    public PictureTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }
    private void init(Context context){
        LayoutPicture = inflate(context, R.layout.layout_picture, this);
        PictureRel = (RelativeLayout) LayoutPicture.findViewById(R.id.picture_rel);
        PictureImage = (ImageView) LayoutPicture.findViewById(R.id.picture_image);
        this.setOnTouchListener(this);
    }

    public void setImage(int image) {
        PictureImage.setImageResource(image);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                addItem(startX,startY);
                break;
        }
        return false;
    }


    private void addItem(int x,int y){

        EditDailog editDailog = new EditDailog(mContext);
        editDailog.show();

      /*  LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_point, this, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgPoint);
        LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        int inDw = imageView.getDrawable().getIntrinsicWidth();
        int inDh = imageView.getDrawable().getIntrinsicHeight();
        Log.e("addItem",inDw+"...inDw");
        Log.e("addItem",inDh+"...inDh");
        params.leftMargin = x-inDw/2;
        params.topMargin = y-inDh/2;
        if (mSetClickListener!=null){
            mSetClickListener.onSetData(createViewBitmap(LayoutPicture));
        }
        this.addView(view,params);*/
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    private OnSetClickListener mSetClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }



    public interface OnSetClickListener {
        void onSetData(Bitmap viewBitmap);
    }




}

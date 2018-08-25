package com.bintutu.shop.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bintutu.shop.R;
import com.bintutu.shop.utils.DebugLog;

@SuppressLint("AppCompatCustomView")
public class DrawImageview extends ImageView implements View.OnTouchListener {

    private Context mcontext;
    private Paint mPaint;
    private final int mBgColor = 0xFF000000;

    int startX = 0;
    int startY = 0;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    public DrawImageview(Context context) {
        this(context, null);
    }

    public DrawImageview(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawImageview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xffFF5317);
        mPaint.setStyle(Paint.Style.FILL);
        mBitmap = BitmapFactory.decodeResource(mcontext.getResources(), R.mipmap.red_point);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                addItem(startX, startY);
                break;
        }
        return false;
    }

    private void addItem(int startX, int startY) {
        DebugLog.e("startX:" + startX + ".......startY:" + startY);
        //mCanvas.drawBitmap(mBitmap, startX, startY, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
    }


}

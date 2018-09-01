package com.bintutu.shop.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.FootTagBean;
import com.bintutu.shop.bean.TAGBean;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

public class  TagLayout extends RelativeLayout implements View.OnTouchListener {

    int startX = 0;
    int startY = 0;
    private Context mContext;
    private ImageView PictureImage;
    private RelativeLayout PictureRel;
    private RelativeLayout LayoutPicture;
    private List<FootTagBean> FootList;
    private String mName;
    private FootTagBean footTagBean;
    private int tag;


    public TagLayout(Context context) {
        super(context, null);
    }
    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }
    private void init(Context context){
        LayoutPicture = (RelativeLayout) inflate(context, R.layout.layout_tag, this);
        PictureRel = (RelativeLayout) LayoutPicture.findViewById(R.id.picture_rel);
        PictureImage = (ImageView) LayoutPicture.findViewById(R.id.picture_image);
        //设置Tag
        tag = 1;
        FootList = new ArrayList<>();
        this.setOnTouchListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setImage(String name, Bitmap imageRe, List<FootTagBean> List) {
        mName = name;
        PictureImage.setBackground(new BitmapDrawable(getResources(), imageRe));

        if (List!=null&&List.size()>0){
            FootList.addAll(List);
            tag =List.size() +1;
            setADDview(FootList);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                SetItem(startX,startY);
                break;
        }
        return false;
    }

    private void SetItem(final int x,final int y){
        final EditDailog editDailog = new EditDailog(mContext);
        editDailog.show();
        editDailog.setEditClickListener(new EditDailog.OnEditClickListener() {
            @Override
            public void onSetData(String edit) {
                editDailog.dismiss();
                addItem(x,y,edit);
            }
        });

    }

    private void addItem(int x, int y, String edit) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.layout_point, this, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgPoint);
        TextView textPoint = (TextView) view.findViewById(R.id.textPoint);
        textPoint.setText(tag+"");
        LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        int inDw = imageView.getBackground().getIntrinsicWidth();
        int inDh = imageView.getBackground().getIntrinsicHeight();
        int inLINDw2 = LayoutPicture.getWidth();
        int inLINDh2 = LayoutPicture.getHeight();
        params.leftMargin = x-inDw;
        double pagex = (double)x/inLINDw2;
        double pagey = (double)y/inLINDh2;
        DebugLog.e("addItem.....","x:"+x+".....with:"+inLINDw2+"....y:"+y+"....hegth:"+inLINDh2);
        DebugLog.e("addItem.....",".....pagex:"+pagex+".....pagey:"+pagey);
        params.topMargin = y-inDh/2;
        LayoutPicture.addView(view,params);

        invalidate();

        SetTagbaen();


        footTagBean.setContent(edit);
        footTagBean.setX(x);
        footTagBean.setY(y);
        footTagBean.setPageX(pagex);
        footTagBean.setPageY(pagey);
        footTagBean.setIndex(tag);
        footTagBean.setView(view);
        footTagBean.setLayoutParams(params);
        FootList.add(footTagBean);
        tag = tag+1;
        if (mSetClickListener!=null){
            mSetClickListener.onSetData(FootList);
        }
    }


    private OnSetClickListener mSetClickListener;

    public void setSetClickListener(OnSetClickListener mSetClickListener) {
        this.mSetClickListener = mSetClickListener;
    }

    public void setRemove(int remove) {
        FootTagBean footTagBean = FootList.get(remove);
        LayoutPicture.removeView(footTagBean.getView());
        FootList.remove(remove);
        if (mSetClickListener!=null){
            mSetClickListener.onSetData(FootList);
        }
        if ( tag>0){
            tag = tag-1;
        }

    }

    public void setRemoveAll() {
        LayoutPicture.removeViews(1,FootList.size());
        FootList.clear();
        if (mSetClickListener!=null){
            mSetClickListener.onSetData(FootList);
        }
        tag =1;
    }

    public void setADDview(List<FootTagBean> list) {

       for (FootTagBean footTagBean :list){
           LayoutPicture.addView(footTagBean.getView(),footTagBean.getLayoutParams());
       }
        if (mSetClickListener!=null){
            mSetClickListener.onSetData(FootList);
        }
    }

    public void setStart() {
        LayoutPicture.removeAllViews();
    }


    public interface OnSetClickListener {
        void onSetData( List<FootTagBean> FootList);
    }

    private void SetTagbaen() {
        footTagBean = new FootTagBean();
        footTagBean.setName(mName);

        if (mName.startsWith("左脚")){
            footTagBean.setFoot("left");
            if (mName.startsWith("左脚内侧")) {
                footTagBean.setItem("medial");
                footTagBean.setId("medial-left");
            }
            if (mName.startsWith("左脚脚面")) {
                footTagBean.setItem("face");
                footTagBean.setId("face-left");
            }
            if (mName.startsWith("左脚外侧")) {
                footTagBean.setItem("lateral");
                footTagBean.setId("lateral-left");
            }
        }
        if (mName.startsWith("右脚")){
            footTagBean.setFoot("right");
            if (mName.startsWith("右脚内侧")) {
                footTagBean.setItem("medial");
                footTagBean.setId("medial-right");
            }
            if (mName.startsWith("右脚脚面")) {
                footTagBean.setItem("face");
                footTagBean.setId("face-right");
            }
            if (mName.startsWith("右脚外侧")) {
                footTagBean.setItem("lateral");
                footTagBean.setId("lateral-right");
            }
        }

    }


}

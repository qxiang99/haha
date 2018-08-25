package com.bintutu.shop.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.TAGBean;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class PictureTagLayout extends RelativeLayout implements OnTouchListener{

    int startX = 0;
    int startY = 0;
    private Context mContext;
    private ImageView PictureImage;
    private RelativeLayout PictureRel;
    private View LayoutPicture;
    private List<TAGBean.DetailListBean> dElList;
    private String mName;
    private boolean addTag;
    private TAGBean tagBean;

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
        tagBean = new TAGBean();
        dElList = new ArrayList<>();
        addTag = true;
        this.setOnTouchListener(this);
    }


    public void setImage(String name, Bitmap imageRe) {
        mName = name;
        PictureImage.setBackground(new BitmapDrawable(getResources(), imageRe));
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
        int tag = ConfigManager.Device.getTag();
        textPoint.setText(tag+"");
        ConfigManager.Device.setTag((tag+1));
        LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        int inDw = imageView.getBackground().getIntrinsicWidth();
        int inDh = imageView.getBackground().getIntrinsicHeight();
        params.leftMargin = x-inDw/2;
        params.topMargin = y-inDh/2;
        this.addView(view,params);

        invalidate();
        if (addTag){
            addTag =false;
            SetTagbaen();
        }


        TAGBean.DetailListBean detailListBean = new TAGBean.DetailListBean();
        detailListBean.setContent(edit);
        detailListBean.setX(x);
        detailListBean.setY(y);
        detailListBean.setIndex(x/inDw);
        detailListBean.setPageY(y/inDh);
        detailListBean.setIndex(tag);
        dElList.add(detailListBean);

        if (mSetClickListener!=null){
            mSetClickListener.onSetData(createViewBitmap(LayoutPicture),tagBean);
        }
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
        void onSetData(Bitmap viewBitmap, TAGBean tagBean);
    }

    private void SetTagbaen() {

        tagBean.setName(mName);

        if (mName.startsWith("左脚")){
            tagBean.setFoot("left");
            if (mName.startsWith("左脚内侧")) {
                tagBean.setItem("medial");
                tagBean.setId("medial-left");
            }
            if (mName.startsWith("左脚脚面")) {
                tagBean.setItem("face");
                tagBean.setId("medial-face");
            }
            if (mName.startsWith("左脚外侧")) {
                tagBean.setItem("outside");
                tagBean.setId("medial-outside");
            }
        }
        if (mName.startsWith("右脚")){
            tagBean.setFoot("left");
            if (mName.startsWith("右脚内侧")) {
                tagBean.setItem("medial");
                tagBean.setId("medial-left");
            }
            if (mName.startsWith("右脚脚面")) {
                tagBean.setItem("face");
                tagBean.setId("medial-face");
            }
            if (mName.startsWith("右脚外侧")) {
                tagBean.setItem("outside");
                tagBean.setId("medial-outside");
            }
        }

        tagBean.setDetailList(dElList);
    }


}

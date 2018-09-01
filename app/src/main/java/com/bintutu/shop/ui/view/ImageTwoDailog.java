package com.bintutu.shop.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.FootTagBean;
import com.bintutu.shop.bean.TAGBean;
import com.bintutu.shop.utils.DebugLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageTwoDailog extends Dialog {


    @BindView(R.id.imagetwo_image_img)
    TagLayout picture;
    @BindView(R.id.imagetwo_lin)
    LinearLayout ImageTwoLin;
    @BindView(R.id.imagetwo_lin_detele)
    Button ImageLinDetele;
    @BindView(R.id.imagetwo_but_laststep)
    Button ButLaststep;
    @BindView(R.id.imagetwo_but_cleanup)
    Button ButCleanup;
    @BindView(R.id.imagetwo_but_keep)
    Button ButKeep;

    private Context mContext;
    private ImageView mView;
    private List<FootTagBean> mFootList;


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
        ImageLinDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFootList!=null&&mFootList.size()>0){
                    setRemove(1,mFootList);
                }
                dismiss();
            }
        });
        ButLaststep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFootList!=null&&mFootList.size()>0){
                    setRemove(0,mFootList);
                }
            }
        });
        ButCleanup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFootList!=null&&mFootList.size()>0){
                    setRemove(1,mFootList);
                }
            }
        });
        ButKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageClickListener!=null){
                    mImageClickListener.onSetData(mView,createViewBitmap(picture),mFootList);
                }
                dismiss();
                picture.setStart();
            }
        });
        picture.setSetClickListener(new TagLayout.OnSetClickListener() {
            @Override
            public void onSetData( List<FootTagBean> FootList) {
                mFootList = FootList;
                ImageTwoLin.removeAllViews();
                if (mFootList!=null&&mFootList.size()>0){
                    setAddView(mFootList);
                }
            }
        });
    }

    private void setRemove(int type, List<FootTagBean> mFootList) {
        if (type==0){
            picture.setRemove(mFootList.size()-1);
        }else {
            picture.setRemoveAll();
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setImage(String name, ImageView view, Bitmap imageRe, List<FootTagBean> footList) {
        mView = view;
        picture.setImage(name,imageRe,footList);
    }

    public void setAddView(List<FootTagBean> FootList) {
        for (int i=0;i<FootList.size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.iamgetwo_item, null);
            TextView number = (TextView) view.findViewById(R.id.imagetwo_item_text_number);
            number.setText(String.valueOf(FootList.get(i).getIndex()));
            TextView title = (TextView) view.findViewById(R.id.imagetwo_item_text_title);
            title.setText("["+FootList.get(i).getName()+"]");
            TextView context = (TextView) view.findViewById(R.id.imagetwo_item_text_context);
            context.setText(FootList.get(i).getContent());
            ImageTwoLin.addView(view);
        }
    }



    private OnImageClickListener mImageClickListener;

    public void setImageClickListener(OnImageClickListener mImageClickListener) {
        this.mImageClickListener = mImageClickListener;
    }


    public interface OnImageClickListener {
        void onSetData(ImageView view, Bitmap viewBitmap,List<FootTagBean> mFootList);
    }


    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
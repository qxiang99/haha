package com.bintutu.shop.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.PhotoAdapter;
import com.bintutu.shop.ui.view.PhotoView;
import com.bintutu.shop.ui.view.SlideViewPager;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Utils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.pager)
    SlideViewPager mViewPager;
    @BindView(R.id.photo_recycleview)
    RecyclerView mRecycleview;
    @BindView(R.id.photo_lin)
    LinearLayout photoLin;
    @BindView(R.id.photo_text)
    TextView photoText;
    @BindView(R.id.photo_but_return)
    Button photoButReturn;
    private PhotoAdapter mAdapter;
    private String file;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_photoview);
    }

    @Override
    protected void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 8);
        mRecycleview.setLayoutManager(layoutManager);

        List<String> imagelist = Utils.getAllFiles(AppConstant.IMAGE_LONG, "png");
        if (imagelist != null && imagelist.size() > 0) {
            photoText.setVisibility(View.GONE);
            setGrid(imagelist);
            setPager(imagelist);
        }else {
            photoText.setVisibility(View.VISIBLE);
            mRecycleview.setVisibility(View.GONE);
            photoText.setText("没有生成的截图");
        }

    }

    @Override
    protected void setListener() {
        photoButReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setGrid(final List<String> imagelist) {
        mAdapter = new PhotoAdapter(imagelist);
        mRecycleview.setAdapter(mAdapter);

        mAdapter.setSetClickListener(new PhotoAdapter.OnSetClickListener() {
            @Override
            public void onSetData(String data, int position) {
                photoLin.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                mViewPager.setCurrentItem(position, false);
            }
        });
    }


    public void setPager(final List<String> imagelist) {
        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imagelist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(PhotoViewActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                try {
                    view.setImageDrawable(imagelist.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && mViewPager.getVisibility() == View.VISIBLE) {
            photoLin.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

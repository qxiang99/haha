package com.bintutu.shop.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bintutu.shop.R;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.PhotoView;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Utils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends BaseActivity {
    @BindView(R.id.gv)
    GridView mGridview;
    @BindView(R.id.pager)
    ViewPager mViewPager;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_photoview);
    }

    @Override
    protected void init() {
        List<String> imagelist = Utils.getAllFiles(AppConstant.IMAGE_LONG, "png");
        if (imagelist != null && imagelist.size() > 0) {
            setGrid(imagelist);
            setPager(imagelist);
        }

    }

    @Override
    protected void setListener() {
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mViewPager.setVisibility(View.VISIBLE);
                mViewPager.setCurrentItem(position, false);

            }
        });
    }

    private void setGrid(final List<String> imagelist) {
        mGridview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imagelist.size();
            }
            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                PhotoView p = new PhotoView(PhotoViewActivity.this);
                p.setLayoutParams(new AbsListView.LayoutParams((int) (getResources().getDisplayMetrics().density * 100), (int) (getResources().getDisplayMetrics().density * 100)));
                p.setScaleType(ImageView.ScaleType.CENTER_CROP);
                try {
                    p.setImageDrawable(imagelist.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 把PhotoView当普通的控件把触摸功能关掉
                p.disenable();
                return p;
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
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&&mViewPager.getVisibility()==View.VISIBLE) {

            mViewPager.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.bintutu.shop.ui.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bintutu.shop.ui.activity.DetailActivity;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
//    @Override
//    public float getPageWidth(int position) {
//       return 0.9f;
//    }


    private Context mContext;
    private int[] image;

    public ViewPagerAdapter(Context context, int[] imageRes) {
        mContext = context;
        image = imageRes;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);
        imageView.setBackgroundResource(image[position]);
        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}

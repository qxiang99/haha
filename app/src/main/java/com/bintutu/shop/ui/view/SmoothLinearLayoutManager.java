package com.bintutu.shop.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * author Gao
 * date 2017/11/23 0023
 * description
 */

public class SmoothLinearLayoutManager extends CoverFlowLayoutManger {


    public SmoothLinearLayoutManager(boolean isFlat, boolean isGreyItem, boolean isAlphaItem, float cstInterval) {
        super(isFlat, isGreyItem, isAlphaItem, cstInterval);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return 0.2f;
                    }
                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

}

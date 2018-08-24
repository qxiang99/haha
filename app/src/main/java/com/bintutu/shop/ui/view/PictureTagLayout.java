package com.bintutu.shop.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class PictureTagLayout extends RelativeLayout implements OnTouchListener{
	private static final int CLICKRANGE = 5;
	int startX = 0;
	int startY = 0;
	int startTouchViewLeft = 0;
	int startTouchViewTop = 0;
	private View touchView,clickView;
	public PictureTagLayout(Context context) {
		super(context, null);
	}
	public PictureTagLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init(){
		this.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchView = null;
			if(clickView!=null){
				((PictureTagView)clickView).setStatus(PictureTagView.Status.Normal);
				clickView = null;
			}
			startX = (int) event.getX();
			startY = (int) event.getY();

				addItem(startX,startY);
			break;
		}
		return false;
	}

	
	private void addItem(int x,int y){
		View view = null;
		LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.leftMargin = x;
		view = new PictureTagView(getContext(), PictureTagView.Direction.Left);

		params.topMargin = y;

		this.addView(view, params);
	}

}

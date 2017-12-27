package com.zhao.zhaolibrary.snapview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.zhao.zhaolibrary.snapview.util.LogUtil;

public class MyScrollView extends ScrollView {
	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		LogUtil.e("MyScrollView--onInterceptTouchEvent");
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}
		boolean onIntercept = super.onInterceptTouchEvent(ev);
		LogUtil.e("MyScrollView--onInterceptTouchEvent return " + onIntercept);

		return onIntercept;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean touchEvent = super.onTouchEvent(ev);
		LogUtil.e("MyScrollView--onTouchEvent return " + touchEvent);
		return touchEvent;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (onScrollListener != null) {
			onScrollListener.onScroll(x, y, oldx, oldy);
		}
	}

	private OnJDScrollListener onScrollListener;

	public OnJDScrollListener getOnScrollListener() {
		return onScrollListener;
	}

	public void setOnJDScrollListener(OnJDScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	public interface OnJDScrollListener {
		void onScroll(int x, int y, int oldx, int oldy);
	}
}

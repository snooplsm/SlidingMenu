package com.slidingmenu.lib;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewBehind extends CustomViewAbove {
	
	private static final String TAG = "CustomViewBehind";
	private boolean mChildrenEnabled;
	private CustomViewAbove mViewAbove;
	
	public CustomViewBehind(Context context) {
		this(context, null);
	}

	public CustomViewBehind(Context context, AttributeSet attrs) {
		super(context, attrs, false);
	}

	public int getChildLeft(int i) {
		return 0;
	}

	@Override
	public int getCustomWidth() {
		int i = isMenuOpen()? 0 : 1;
		return getChildWidth(i);
	}

	@Override
	public int getChildWidth(int i) {
		if (i <= 0) {
			return getBehindWidth();
		} else {
			return getChildAt(i).getMeasuredWidth();
		}
	}

	public int getBehindWidth() {
		ViewGroup.LayoutParams params = getLayoutParams();
		return params.width;
	}

	@Override
	public void setContent(View v) {
		super.setMenu(v);
	}
	
	public void setChildrenEnabled(boolean enabled) {
		mChildrenEnabled = enabled;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
			}
			mVelocityTracker.addMovement(ev);
		} else if (action == MotionEvent.ACTION_MOVE) {
			mVelocityTracker.addMovement(ev);
			mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
			int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(
					mVelocityTracker, mActivePointerId);
			int initialYVelocity = (int) VelocityTrackerCompat.getYVelocity(mVelocityTracker, mActivePointerId);
			System.out.println(initialVelocity + ","+initialYVelocity);
			System.out.println("whaasssttt");
			if(Math.abs(initialVelocity)>100) {
				if(Math.abs(initialYVelocity) < 100) {
					System.out.println("intercepting");
					return true;
				}
			}
			// Scroll to follow the motion event

		}	
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return mViewAbove.onTouchEvent(e);
	}

	public void setCustomViewAbove(CustomViewAbove customViewAbove) {
		this.mViewAbove = customViewAbove;
	}
	
}

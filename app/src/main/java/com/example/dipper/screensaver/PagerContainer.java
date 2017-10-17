/*
 * Copyright (c) 2017. Dipper
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dipper.screensaver;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Dipper on 2016/10/30.
 */

public class PagerContainer extends FrameLayout implements OnPageChangeListener {
    private ViewPager pager;
    boolean mNeedsRedraw = false;

    public PagerContainer(Context context) {
        super(context);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setClipChildren(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    protected void onFinishInflate() {
        try {
            pager = (ViewPager) getChildAt(0);
            if (pager != null)
                pager.addOnPageChangeListener(this);

        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a MyViewPager");
        }
    }

    public ViewPager getPager() {
        Log.d("dipper", "pager is " + (pager == null ? "null" : "not null"));
        return pager;
    }

    private Point center = new Point();
    private Point InitialTouch = new Point();
    private Point bindingTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        center.x = w / 2;
        center.y = h / 2;
    }

//    private boolean noScroll = true; //true 代表不能滑动 //false 代表能滑动

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                InitialTouch.x = center.x - InitialTouch.x;
                InitialTouch.y = center.y - InitialTouch.y;
            default:
                float deltaX = center.x - InitialTouch.x;
                float deltaY = center.y - InitialTouch.y;
                event.offsetLocation(deltaX, deltaY);
                break;
        }
        return pager.dispatchTouchEvent(event);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNeedsRedraw) {
            invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}

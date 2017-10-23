package com.example.dipper.screensaver;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Created by dipper on 2017/10/20.
 */

public class MyHandler extends Handler {
    public static final int CHANG_IMAGE = 1;
    private ViewPager viewPager;

    public MyHandler(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case CHANG_IMAGE:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, false);
                break;
            default:
                break;
        }
    }
}

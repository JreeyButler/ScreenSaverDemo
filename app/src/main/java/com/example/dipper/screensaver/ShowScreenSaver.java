package com.example.dipper.screensaver;

import android.os.Handler;
import android.service.dreams.DreamService;
import android.support.v4.view.ViewPager;

/**
 * Created by dipper on 2017/10/11.
 */

public class ShowScreenSaver extends DreamService {
    public static final long time = 5000;
    public static final String TAG = "ShowScreenSaver";

    private PagerContainer pagerContainer;
    private ViewPager viewPager;
    private MyHandler mHandler;
    private Handler handler;


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(R.layout.activity_main);
        Utils.makeImagesDir();//创建图片文件夹
        initScreenSaver();
    }

    private void initScreenSaver() {
        pagerContainer = findViewById(R.id.pager_container);
        viewPager = pagerContainer.getPager();
        viewPager.setAdapter(new MyPagerAdapter(this));
        mHandler = new MyHandler(viewPager);
        handler = new Handler();
        handler.postDelayed(task, time);
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, time);
            mHandler.sendEmptyMessage(MyHandler.CHANG_IMAGE);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

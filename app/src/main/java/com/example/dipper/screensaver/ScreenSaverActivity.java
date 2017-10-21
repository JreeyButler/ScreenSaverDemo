package com.example.dipper.screensaver;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;


/**
 * Created by dipper on 2017/10/12.
 */

public class ScreenSaverActivity extends Activity {
    public static final String TAG = "ScreenSaverActivity";
    public static final long time = 60000;
    private PagerContainer pagerContainer;
    private ViewPager viewPager;
    private MyHandler mHandler;
    private Handler handler;

    private KeyguardManager km;
    private KeyguardManager.KeyguardLock kl;

    private PowerManager pm;
    private PowerManager.WakeLock wl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
        wl.acquire();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initScreenSaver();
    }

    private void initScreenSaver() {
        pagerContainer = findViewById(R.id.pager_container);
        viewPager = pagerContainer.getPager();
        viewPager.setAdapter(new MyPagerAdapter(this, wl));
        mHandler = new MyHandler(viewPager);
        handler = new Handler();
        handler.postDelayed(task, time);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, time);
            mHandler.sendEmptyMessage(MyHandler.CHANG_IMAGE);
        }
    };

    @Override
    protected void onResume() {
        //wl.acquire();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (wl != null) {
            try {
                wl.release();
            } catch (Throwable th) {
                // ignoring this exception, probably wakeLock was already released
            }
        }
        super.onDestroy();
    }
}

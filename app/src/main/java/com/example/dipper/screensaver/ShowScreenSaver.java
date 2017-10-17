package com.example.dipper.screensaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.service.dreams.DreamService;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

/**
 * Created by dipper on 2017/10/11.
 */

public class ShowScreenSaver extends DreamService {
    private PagerContainer pagerContainer;
    private ViewPager viewPager;
    private MyHandler mHandler;
    private Handler handler;
    public static final long time = 5000;


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(R.layout.activity_main);
        initScreenSaver();
    }

    private void initScreenSaver() {
        pagerContainer = findViewById(R.id.pager_container);
        viewPager = pagerContainer.getPager();
        viewPager.setAdapter(new MyPagerAdapter(this));
        mHandler = new MyHandler();
        handler = new Handler();

        handler.postDelayed(task, time);
        //registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, time);
            mHandler.sendEmptyMessage(MyHandler.CHANG_IMAGE);
        }
    };

    /*private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(context, ScreenSaverActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    };*/


    class MyHandler extends Handler {
        public static final int CHANG_IMAGE = 1;

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

    class MyPagerAdapter extends PagerAdapter {
        private int[] images;
        private ImageView imageView;
        private Context context;

        public MyPagerAdapter(Context context) {
            images = new int[]{R.drawable.slideshow_1, R.drawable.slideshow_2, R.drawable.slideshow_3,
                    R.drawable.slideshow_4, R.drawable.slideshow_5, R.drawable.slideshow_6,
                    R.drawable.slideshow_7, R.drawable.slideshow_8, R.drawable.slideshow_9,
                    R.drawable.slideshow_10};
            this.context = context;
        }

        @Override
        public int getCount() {
            if (images == null)
                return 0;
            return 100000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= images.length;
            if (position < 0) {
                position = images.length + position;
            }

            View view1 = LayoutInflater.from(context).inflate(R.layout.image_item, null);
            ViewParent viewParent = view1.getParent();
            if (viewParent != null) {
                ViewGroup parent = (ViewGroup) viewParent;
                parent.removeView(view1);
            }
            imageView = view1.findViewById(R.id.screen_images);
            imageView.setImageResource(images[position]);
            container.addView(view1);
            return view1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //test();
            container.removeView((View) object);
        }

        private void test() {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bm = bitmapDrawable.getBitmap();
                if (bm != null && !bm.isRecycled()) {
                    Log.d("...desimg..", "被回收了" + bm.getByteCount());
                    imageView.setImageResource(0);
                    bm.recycle();
                }
            }
        }
    }
}

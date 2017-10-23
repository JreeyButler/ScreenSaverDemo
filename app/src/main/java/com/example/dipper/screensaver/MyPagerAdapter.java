package com.example.dipper.screensaver;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.PowerManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

/**
 * Created by yan.liangliang on 2017/10/20.
 */

public class MyPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private int[] images;
    private ImageView imageView;
    private Context context;
    private PowerManager.WakeLock wl;

    public MyPagerAdapter(Context context, PowerManager.WakeLock wl) {
        images = new int[]{R.drawable.slideshow_1, R.drawable.slideshow_2, R.drawable.slideshow_3,
                R.drawable.slideshow_4, R.drawable.slideshow_5, R.drawable.slideshow_6,
                R.drawable.slideshow_7, R.drawable.slideshow_8, R.drawable.slideshow_9,
                R.drawable.slideshow_10};
        this.wl = wl;
        this.context = context;
    }

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
        imageView.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.screen_images:
                ((Activity) context).finish();
                if (wl != null)
                    wl.release();
                break;
            default:
                break;
        }
    }
}

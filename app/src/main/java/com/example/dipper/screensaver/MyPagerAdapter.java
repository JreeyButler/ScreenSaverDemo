package com.example.dipper.screensaver;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipper on 2017/10/20.
 */

public class MyPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private ImageView imageView;
    private Context context;
    private PowerManager.WakeLock wl;
    private int[] images;
    private Animation animation;

    public MyPagerAdapter(Context context, PowerManager.WakeLock wl) {
        this.wl = wl;
        this.context = context;
        getDemoImages();
    }

    public MyPagerAdapter(Context context) {
        this.context = context;
        getDemoImages();
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
        animation = AnimationUtils.loadAnimation(context, R.anim.scale);
        imageView.setAnimation(animation);
        imageView.setOnClickListener(this);
        Glide.with(context).load(images[position]).into(imageView);
        container.addView(view1);
        return view1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
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

    private void getDemoImages() {
        if (Utils.hasDemoImages()) {
            getExternalDemoImages();
        } else {
            getDefaultDemoImages();
        }
    }

    /**
     * 获取默认屏保壁纸
     */
    private void getDefaultDemoImages() {
        images = new int[]{R.drawable.slideshow_1, R.drawable.slideshow_2, R.drawable.slideshow_3,
                R.drawable.slideshow_4, R.drawable.slideshow_5, R.drawable.slideshow_6,
                R.drawable.slideshow_7, R.drawable.slideshow_8, R.drawable.slideshow_9,
                R.drawable.slideshow_10};
    }

    /**
     * 获取自定义屏保壁纸
     */
    private void getExternalDemoImages() {
        File[] images = new File(Utils.IMAGES_FILES_PATH).listFiles();
        for (int i = 0; i < images.length; i++) {
            //demoBitmaps.add(BitmapFactory.decodeFile(Utils.IMAGES_FILES_PATH + images[i].getName()));
        }
    }
}

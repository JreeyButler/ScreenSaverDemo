package com.example.dipper.screensaver;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.util.List;

/**
 * Created by dipper on 2017/10/23.
 */

public class Utils {
    public static final String IMAGES_DIR_PATH = Environment.getExternalStorageDirectory().getPath() + "/DemoImages";
    public static final String IMAGES_FILES_PATH = IMAGES_DIR_PATH + "/";

    /**
     * 生成屏保壁纸文件夹
     */
    public static void makeImagesDir() {
        File dirImagesFolder = new File(IMAGES_DIR_PATH);
        if (!dirImagesFolder.exists()) {
            dirImagesFolder.mkdir();
        }
    }

    /**
     * 判断是否存在自定义屏保壁纸
     *
     * @return
     */
    public static boolean hasDemoImages() {
        File[] images = new File(IMAGES_FILES_PATH).listFiles();
        if (images != null && images.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 释放所有Bitmap内存，防止OOM发生
     *
     * @param bitmaps bitmap序列
     */
    public static void recycleAllDemoImageBitmap(List<Bitmap> bitmaps) {
        if (bitmaps != null && bitmaps.size() > 0) {
            for (int i = 0; i < bitmaps.size(); i++) {
                Bitmap bitmap = bitmaps.get(i);
                bitmap.recycle();
            }
        }
    }
}

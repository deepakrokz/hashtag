package com.hashtag;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Deepak on 10/26/2016.
 */

public class AppController_HashTag extends Application {

    private static AppController_HashTag mInstance;
    private static SharedPreferences sp_userinfo;
    /** for Image Loader  */
    public static ImageLoader imageLoader;
    private static DisplayImageOptions options_cover_pic;


    public AppController_HashTag(){
        mInstance = this;
    }

    /** used to get instance globally */
    public static synchronized AppController_HashTag getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        try
        {
            mInstance = this;
            /** to call initialize SharedPreferences  */
            initSharedPreferences();
            /** to call for image loading  */
            initImageLoader();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /** used to initialize instance globally of SharedPreferences  */
    private void initSharedPreferences()
    {
        sp_userinfo = getApplicationContext().getSharedPreferences("sp_userinfo", Context.MODE_PRIVATE);
    }
    /** used to get instance globally of SharedPreferences  */
    public static synchronized SharedPreferences getSpUserInfo()
    {
        return sp_userinfo;
    }

    /** start...used to for image loading  */
    private void initImageLoader()
    {
        try
        {
            imageLoader = ImageLoader.getInstance();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .threadPriority(Thread.MAX_PRIORITY)
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .denyCacheImageMultipleSizesInMemory().threadPoolSize(5)
                    .diskCacheSize(20 * 1024 * 1024)
                    // 20 Mb
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
//												.writeDebugLogs() // Remove for release app
                    .build();

            imageLoader.init(config);


            //for post pic
            options_cover_pic = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .resetViewBeforeLoading(true)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static synchronized ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public static synchronized DisplayImageOptions getImageLoaderOptionsCoverPic()
    {
        return options_cover_pic;
    }
}

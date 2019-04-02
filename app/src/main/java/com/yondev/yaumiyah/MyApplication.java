package com.yondev.yaumiyah;

import android.app.Application;

import com.yondev.yaumiyah.utils.FontsOverride;

/**
 * Created by ThinkPad on 5/7/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this);
    }
}
package com.yts_movies.ytsmovies;

import android.app.Application;
import android.os.Build;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class YtsApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        initCalligraphy();

    }

    private void initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    } // use timber logging


    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    } // set font function
}

package com.example.dell.dbtest;

import android.app.Application;
import timber.log.Timber;
/**
 * Created by dell on 2016/12/5.
 */

public class InstaMaterialApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}

package com.idea_factory.idea_factory;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

/**
 * Created by Admin on 9/17/2018.
 */

public class IdeaFactoryApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /*Fabric.with(this, new Crashlytics());*/
    }

    public static Context getContext() {
        return mContext;
    }
}

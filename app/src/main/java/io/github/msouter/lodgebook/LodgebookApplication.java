package io.github.msouter.lodgebook;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

/**
 * Initialize with the application context.
 *
 * Created by Michael Souter on 2017-12-01.
 */

public class LodgebookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}

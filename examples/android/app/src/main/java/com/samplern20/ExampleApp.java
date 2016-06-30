package com.samplern20;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

/**
 * Created by tim.jiang on 30/6/2016.
 */
public class ExampleApp extends Application {
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
}

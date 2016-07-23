package com.samplern20;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.github.alinz.reactnativewebviewbridge.WebViewBridgePackage;

import java.util.Arrays;
import java.util.List;

public class ExampleApp extends Application implements ReactApplication {
    private ReactNativeHost mHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return true;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new WebViewBridgePackage()
            );
        }
    };

    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mHost;
    }
}

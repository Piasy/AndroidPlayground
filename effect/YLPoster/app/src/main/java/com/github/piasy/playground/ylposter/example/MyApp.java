package com.github.piasy.playground.ylposter.example;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;

/**
 * Created by Piasy{github.com/Piasy} on 24/11/2016.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                .build());

        BigImageViewer.initialize(FrescoImageLoader.with(getApplicationContext()));
    }
}

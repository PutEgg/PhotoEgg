package org.putegg.app.PhotoEgg.file;

import android.app.Application;

import org.putegg.app.PhotoEgg.photo.TestImageLoader;
import org.putegg.app.PhotoEgg.photo.ZoomMediaLoader;

/**
 * Created by yangc on 2017/9/4.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
    }
}

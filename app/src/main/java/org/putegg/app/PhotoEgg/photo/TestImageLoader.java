package org.putegg.app.PhotoEgg.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import org.putegg.app.PhotoEgg.R;
import org.putegg.app.PhotoEgg.IZoomMediaLoader;
import org.putegg.app.PhotoEgg.MySimpleTarget;

/**
 * Created by yangc on 2017/9/4.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class TestImageLoader implements IZoomMediaLoader {

    @Override
    public void displayImage(@NonNull Fragment context,@NonNull String path, final@NonNull MySimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(path).asBitmap() .centerCrop() .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default_image)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        simpleTarget.onResourceReady(resource);
                    }
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        simpleTarget.onLoadStarted();

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        simpleTarget.onLoadFailed(errorDrawable);
                    }
                });
    }

    @Override
    public void onStop(@NonNull Fragment context) {
          Glide.with(context).onStop();

    }

    @Override
    public void clearMemory(@NonNull Context c) {
             Glide.get(c).clearMemory();
    }
}
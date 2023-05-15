package gdut.edu.datingforballsports.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.interfaces.OnCallbackListener;

public class GlideImageEngine implements ImageEngine {
    public GlideImageEngine() {
    }

    private static GlideImageEngine instance;

    public static GlideImageEngine createGlideImageEngine() {
        if (null == instance) {
            synchronized (GlideImageEngine.class) {
                if (null == instance) {
                    instance = new GlideImageEngine();
                }
            }
        }
        return instance;
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void loadImageBitmap(@NonNull Context context, @NonNull String url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {

    }

    @Override
    public void loadAlbumCover(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }


    @Override
    public void pauseRequests(Context context) {

    }

    @Override
    public void resumeRequests(Context context) {

    }
}


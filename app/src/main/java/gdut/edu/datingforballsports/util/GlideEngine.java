package gdut.edu.datingforballsports.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import gdut.edu.datingforballsports.R;

/**
 * 在项目中创建一个GlideEngine类
 * Glide加载引擎
 */
public class GlideEngine implements ImageEngine {

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param url       资源url
     * @param imageView 图片承载控件
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 加载指定url并返回bitmap
     *
     * @param context   上下文
     * @param url       资源url
     * @param maxWidth  资源最大加载尺寸
     * @param maxHeight 资源最大加载尺寸
     * @param call      回调接口
     */
    @Override
    public void loadImageBitmap(@NonNull Context context, @NonNull String url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .override(maxWidth, maxHeight)
                .load(url)
                .into(new CustomTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (call != null) {
                            call.onCall(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        if (call != null) {
                            call.onCall(null);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                });
    }

    /**
     * 加载相册目录封面
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadAlbumCover(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .sizeMultiplier(0.5f)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .placeholder(com.luck.picture.lib.R.drawable.ps_image_placeholder)
                .into(imageView);
    }


    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        imageView.setTag(url);
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .placeholder(com.luck.picture.lib.R.drawable.ps_image_placeholder)
                .into(imageView);
    }

    @Override
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    public void loadItemNetImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.default_personal_image)
                .placeholder(com.luck.picture.lib.R.drawable.ps_image_placeholder)
                .centerCrop()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (imageView.getTag().equals(url)) {
                            imageView.setImageDrawable(resource);
                        }
                        return;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    public void loadNetImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.default_personal_image)
                .placeholder(com.luck.picture.lib.R.drawable.ps_image_placeholder)
                .centerCrop()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (imageView.getTag().equals(url)) {
                            imageView.setImageDrawable(resource);
                        }
                        return;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    public void saveImage(Context context, String sourceUrl, String saveUrl) {
        if(sourceUrl.equals(saveUrl)){
            return;
        }
        Glide.with(context).load(sourceUrl)
                .placeholder(com.luck.picture.lib.R.drawable.ps_image_placeholder)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource == null)
                            return;
                        try {
                            File file = new File(saveUrl);
                            if (file.exists()) {
                                file.delete();
                            }
                            if (!file.exists())
                                file.createNewFile();
                            FileOutputStream out = null;
                            out = new FileOutputStream(file);
                            ((BitmapDrawable) resource).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


    private GlideEngine() {
    }

    private static GlideEngine instance;

    public static GlideEngine createGlideEngine() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }
}

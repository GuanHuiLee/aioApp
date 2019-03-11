package com.zgg.commonlibrary.glide;

/**
 * Created by LiGuanHui on 2018/11/27 09:22
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.zgg.commonlibrary.R;

import java.io.File;

/**
 * 类描述：GlideHelper
 * 创建人：lxx
 * 创建时间：2018/5/24 13:57
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GlideHelper {
    private static int placeholderResId = -1;
    private static int errorResId = -1;

    public static void init(@DrawableRes int placeholder, @DrawableRes int error) {
        placeholderResId = placeholder;
        errorResId = error;
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, url, imageView, options);
    }

    public static void loadBitmap(Context context, ImageView imageView, Bitmap url) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, url, imageView, options);
    }

    public static void loadImageNoneDisk(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, url, imageView, options);
    }


    public static void loadImageNoneCache(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, url, imageView, options);
    }

    public static void loadCircleImage(Context context, ImageView imageView, String url, @DrawableRes int placeholderResId) {
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(placeholderResId);
        load(context, url, imageView, options);
    }

    public static void loadRadiusImage(Context context, ImageView imageView, String url, int radius, @DrawableRes int placeholderResId) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(radius))
                .placeholder(placeholderResId);
        load(context, url, imageView, options);
    }

    public static void loadImage(Context context, ImageView imageView, File fileImage) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop();
        load(context, fileImage, imageView, options);
    }

    public static void loadResImage(Context context, ImageView imageView, Integer fileImage) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop();
        load(context, fileImage, imageView, options);
    }

    public static void loadResImageNoCenterCrop(Context context, ImageView imageView, Integer fileImage) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, fileImage, imageView, options);
    }

    public static void loadImageNoCenterCrop(Context context, ImageView imageView, String fileImage) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, fileImage, imageView, options);
    }


    public static void loadSizeImage(Context context, ImageView imageView, File fileImage, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId)
                .override(width, height)
                .centerCrop();
        load(context, fileImage, imageView, options);
    }

    public static void loadImage(Context context, ImageView imageView, String url,
                                 @DrawableRes int placeholderResId, @DrawableRes int errorResId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
        load(context, url, imageView, options);
    }

    private static void load(Context context, String url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    private static void load(Context context, Bitmap url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    private static void load(Context context, Integer url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    private static void load(Context context, File fileImage, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(fileImage)
                .apply(options)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, ImageView imageView, String url, @DrawableRes int placeholderResId, final LoadedListener listener){
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(placeholderResId);

        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(null != listener){
                            listener.loaded(resource);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    public interface LoadedListener{
        void loaded(Drawable drawable);
    }
}
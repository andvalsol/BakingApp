package com.example.luthiers.bakingapp.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class GlideClass {
    
    //In GlideClass v4 we have to use RequestOptions to include error handling
    private static RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);
    
    public static void setVideoThumbnail(File file, ImageView imageView) {
        Glide.with(imageView)
                .load(file)
                .apply(requestOptions)
                .into(imageView);
    }
    
    private static void useGlide(Object object, ImageView imageView) {
        Glide.with(imageView)
                .load(object)
                .apply(requestOptions)
                .into(imageView);
    }
    
    public static void setVideoThumbnail(String url, ImageView imageView) {
        useGlide(url, imageView);
    }
}
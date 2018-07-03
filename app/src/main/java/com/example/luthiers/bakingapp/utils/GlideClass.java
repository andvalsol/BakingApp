package com.example.luthiers.bakingapp.utils;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class GlideClass {
    public static void setVideoThumbnail(File file, ImageView imageView) {
        //In GlideClass v4 we have to use RequestOptions to include error handling
        RequestOptions requestOptions = new RequestOptions()
                .error(android.support.v4.R.drawable.notification_action_background)
                .centerCrop() //Center the image in the total size of the correspond image view
                .diskCacheStrategy(DiskCacheStrategy.NONE);
    
        Glide.with(imageView)
                .load(file)
                .apply(requestOptions)
                .into(imageView);
    }
    
    private static void useGlide(Object object, ImageView imageView) {
        //In GlideClass v4 we have to use RequestOptions to include error handling
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(android.support.v4.R.drawable.notification_action_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        
        Glide.with(imageView)
                .load(object)
                .apply(requestOptions)
                .into(imageView);
    }
    
    public static void setVideoThumbnail(String url, ImageView imageView) {
        useGlide(url, imageView);
    }
}
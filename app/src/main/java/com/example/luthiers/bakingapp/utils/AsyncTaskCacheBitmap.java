package com.example.luthiers.bakingapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.luthiers.bakingapp.constants.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * We want to download a preview for each video. To accomplish that we could use GlideClass, but
 * GlideClass only shows us previews only for in-memory videos, and our videos comes from a url.
 * Another way to do it (which is the one that we're showing), is to get a video frame and retrieve it,
 * after retrieving the frame, we save that image into a File in the user's device. That image in later
 * set in the proper image view using GlideClass.
 */

public class AsyncTaskCacheBitmap extends AsyncTask<File, File, File> {
    //Create a global constant for the video url
    private String mVideoUrl;
    
    /*Create a global constant for the image view where the image is going to be displayed
    * Use a WeakReference for better context clean up
    * */
    private WeakReference<ImageView> mImageViewWeakReference;

    /*
     * Create a synchronized and static method so that we can use this method and pass the video url
     * and the image view. This method initializes the AsyncTaskCacheBitmap itself.
     * */
    public synchronized static void getVideoFrameFromUrl(String videoUrl, ImageView imageView) {
        //Create a new instance of the AsyncTaskCacheBitmap
        AsyncTaskCacheBitmap asyncTaskCacheBitmap = new AsyncTaskCacheBitmap();
        
        //Assign to each global constant from the AsyncTaskCacheBitmap to each desired value
        asyncTaskCacheBitmap.mVideoUrl = videoUrl;
        
        asyncTaskCacheBitmap.mImageViewWeakReference = new WeakReference<>(imageView);
        
        //Execute the AsyncTask immediately
        asyncTaskCacheBitmap.execute();
    }
    
    @Override
    protected File doInBackground(File... files) {
        //Create the file which will be passed in the onPostExecute
        File file = null;
        
        //Create a MediaMetadataRetriever instance
        MediaMetadataRetriever mediaMetadataRetriever = null;
        
        try {
            //Check that the received video url is not null
            if (!mVideoUrl.isEmpty()) {
                //Get the byte array from the video url and encode it in a Base64 string format
                byte[] byteArray = mVideoUrl.getBytes("UTF-8");
                String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                
                //Create a directory path where the image is going to be saved in the device
                String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Constants.APP_NAME;
                
                //Create a new File with the directory created previously
                File directory = new File(directoryPath);
                
                //If the directory doesn't exist create one
                if (!directory.isDirectory()) directory.mkdirs();
                
                //Create the file on with the image will be saved
                file = new File(directoryPath + File.separator + base64 + ".jpeg");
                
                if (!file.exists()) {
                    file.createNewFile();
                    
                    mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(mVideoUrl, new HashMap<>());
                    
                    Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
                    scaleBitmap(bitmap, file, mImageViewWeakReference.get());
                }
            }
        } catch (IOException ignore) {
            Log.i("AsyncB", "The error is: " + ignore);
            /*It's bad practice to ignore errors, but if in case an error is thrown
             * it will be handled by GlideClass.
             * */
        } finally {
            //Check that the mediaMetadataRetriever is not null so we can release it
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        
        return file;
    }
    
    /*
    * Scale the bitmap since the gotten bitmap can be huge
    * */
    private static void scaleBitmap(Bitmap bitmap, File file, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565; //This RGB is stored in only 2 bytes and doesn't use transparency, yet images seems to be high quality
        
        //The maximum size it's the total width of the image view
        int maxSize = imageView.getWidth();
        
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        
        float bitmapRatio = (float) width / (float) height;
        
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
            
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException ignore) {
            //It's not good practice to ignore errors, but this will be handled by Glide later with a proper error image
        }
        
        //Recycle the bitmap if it's not recycled
        if (!bitmap.isRecycled())
            bitmap.recycle();
    }
    
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        GlideClass.setVideoThumbnail(file, mImageViewWeakReference.get());
    }
}
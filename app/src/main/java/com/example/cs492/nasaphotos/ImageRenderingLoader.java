package com.example.cs492.nasaphotos;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.InputStream;

/**
 * Created by Francisco on 3/10/2018.
 */

public class ImageRenderingLoader extends AsyncTaskLoader<Bitmap> {
    private Bitmap mImageResult;
    private String mImageURl;

    // constructor
    ImageRenderingLoader(Context context, String url){
        super(context);
        mImageURl = url;
    }

    @Override
    protected void onStartLoading(){
        if(mImageURl != null){
            if (mImageResult != null){
                deliverResult(mImageResult);
            }
            else{
                forceLoad();
            }
        }
    }

    @Override
    public Bitmap loadInBackground() {
        try {
            InputStream in = new java.net.URL(mImageURl).openStream();
            mImageResult = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mImageResult;
    }

    @Override
    public void deliverResult(Bitmap data){
        mImageResult = data;
        super.deliverResult(data);
    }
}

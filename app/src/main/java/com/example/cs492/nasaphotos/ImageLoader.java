package com.example.cs492.nasaphotos;

/**
 * Created by 10463 on 3/11/2018.
 */

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.cs492.nasaphotos.utils.ImageofTodayUtil;
import com.example.cs492.nasaphotos.utils.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;

public class ImageLoader extends AsyncTaskLoader<Bitmap> {
    private Bitmap mImageResult;
    private String mImageURl;
    private String mSearchImageURL;

    // constructor
    ImageLoader(Context context, String url){
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
        if(mImageURl != null) {
            try{
                mSearchImageURL = NetworkUtils.doHTTPGet(mImageURl);}
            catch(IOException e){
                Log.d("Loader message","Building URL failed.");
            }
            ImageofTodayUtil.ImageofToday searchResult = ImageofTodayUtil.parseIODResultsJSON(mSearchImageURL);
            Log.d("Loader message","Loading result from URL: "+ searchResult.url);
            try {
                InputStream in = new java.net.URL(searchResult.url).openStream();
                mImageResult = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mImageResult;
        }
        else{
            return null;
        }
    }

    @Override
    public void deliverResult(Bitmap data){
        mImageResult = data;
        super.deliverResult(data);
    }

}

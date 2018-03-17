package com.example.cs492.nasaphotos.MarsRover;

/**
 * Created by 10463 on 3/11/2018.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.cs492.nasaphotos.utils.NetworkUtils;

public class MarsLoader extends AsyncTaskLoader<String>{
    private final static String TAG = MarsLoader.class.getSimpleName();

    String mExploreResultsJSON;


    public MarsLoader(Context context){
        super(context);
    }

    @Override
    protected void onStartLoading(){
        if(mExploreResultsJSON!= null){
            deliverResult(mExploreResultsJSON);
        }else{
            forceLoad();
        }
    }


    @Override
    public String loadInBackground(){
        String flickrExploreURL = FlickrUtils.buildFlickrExploreURL();
        String exploreResults = null;
        try {
            exploreResults = NetworkUtils.doHTTPGet(flickrExploreURL);
        } catch (IOException e) {
            Log.d(TAG, "Error connecting to Flickr", e);
        }
        Log.d("Flickr","Go to URL: "+ flickrExploreURL);
        return exploreResults;
    }

    @Override
    public void deliverResult(String data){
        mExploreResultsJSON= data;
        super.deliverResult(data);
    }

}

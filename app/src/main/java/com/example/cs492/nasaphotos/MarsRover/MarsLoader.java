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

    String mResultsJSON;
    String mMarsURL;

    MarsLoader(Context context, String url){
        super(context);
        mMarsURL = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMarsURL != null) {
            if (mResultsJSON != null) {
                Log.d(TAG, "loader returning cached results");
                deliverResult(mResultsJSON);
            } else {
                forceLoad();
            }
        }
    }

    @Override
    public String loadInBackground() {
        if (mMarsURL != null) {
            Log.d(TAG, "loading results from NASA with URL: " + mMarsURL);
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHTTPGet(mMarsURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Loader", searchResults);
            return searchResults;
        } else {
            return null;
        }
    }

    @Override
    public void deliverResult(String data) {
        mResultsJSON = data;
        super.deliverResult(data);
    }



}

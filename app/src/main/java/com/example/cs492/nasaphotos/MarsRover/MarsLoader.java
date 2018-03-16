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
    private String mMarsURL;
    private String searchURL;


    public MarsLoader(Context context, String MarsURL){
        super(context);
        mMarsURL = MarsURL;
    }

    @Override
    protected void onStartLoading(){
        if(mMarsURL != null){
            if(searchURL != null){
                Log.d(TAG, "Using cached Mars List data");
                deliverResult(searchURL);
            }else{
                forceLoad();
            }
        }
    }

    @Nullable
    @Override
    public String loadInBackground(){
        String SearchJSON = null;
        if(mMarsURL!= null){
            try{
                SearchJSON = NetworkUtils.doHTTPGet(mMarsURL);
            }catch(IOException e){
                Log.d(TAG,"Building image failed!");
            }

            return SearchJSON; //change here!
        }
        else{
        return null;}
    }

    @Override
    public void deliverResult(@Nullable String data){
        searchURL= data;
        super.deliverResult(data);
    }

}

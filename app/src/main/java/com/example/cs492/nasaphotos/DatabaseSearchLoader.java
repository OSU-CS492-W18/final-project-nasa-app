package com.example.cs492.nasaphotos;

import android.support.v4.content.AsyncTaskLoader; 
import android.content.Context;
import java.io.IOException;


/**
 * Created by Francisco on 2/24/2018.
 */

class DatabaseSearchLoader extends AsyncTaskLoader<String> {
    private final static String TAG = DatabaseSearchLoader.class.getSimpleName();

    private String mSearchResultJSON;
    private String mForecastURl;

    // constructor
    DatabaseSearchLoader(Context context, String url){
        super(context);
        mForecastURl = url;
    }

    @Override
    protected void onStartLoading(){
        if(mForecastURl != null){
            if (mSearchResultJSON != null){
                deliverResult(mSearchResultJSON);
            }
            else{
                forceLoad();
            }
        }
    }

    @Override
    public String loadInBackground() {
        if (mForecastURl != null){
            String result =null;
            try {
                result = NetworkUtils.doHTTPGet(mForecastURl);
            }catch (IOException e){
                e.printStackTrace();
            }
            return result;
        }
        else{
            return null;
        }
    }

    @Override
    public void deliverResult(String data){
        mSearchResultJSON = data;
        super.deliverResult(data);
    }
}
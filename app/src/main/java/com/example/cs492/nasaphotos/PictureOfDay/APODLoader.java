package com.example.cs492.nasaphotos.PictureOfDay;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.cs492.nasaphotos.utils.NetworkUtils;

import java.io.IOException;

/**
 * Created by Aless on 3/17/2018.
 */

public class APODLoader extends AsyncTaskLoader<String> {
    private final static String TAG = APODLoader.class.getSimpleName();

    private String mAPODResultJSON;
    private String mAPODurl;

    APODLoader(Context context, String url) {
        super(context);
        mAPODurl = url;
    }

    @Override
    protected void onStartLoading() {
        if(mAPODurl != null){
            if (mAPODResultJSON != null){
                deliverResult(mAPODResultJSON);
            }
            else{
                forceLoad();
            }
        }
    }

    @Override
    public String loadInBackground() {
        if (mAPODurl != null){
            String result =null;
            try {
                result = NetworkUtils.doHTTPGet(mAPODurl);
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
    public void deliverResult(String data) {
        mAPODResultJSON = data;
        super.deliverResult(mAPODResultJSON);
    }
}

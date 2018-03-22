package com.example.cs492.nasaphotos.PictureOfDay;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.cs492.nasaphotos.utils.APODUtil;
import com.example.cs492.nasaphotos.utils.NetworkUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by Aless on 3/17/2018.
 */

class APODLoader extends AsyncTaskLoader<ArrayList<APODUtil.APODItem>> {
    private final static String TAG = APODLoader.class.getSimpleName();

    private ArrayList<APODUtil.APODItem> mAPODResultJSON;
    private Calendar mCalendar;

    APODLoader(Context context, int day, int month, int year) {
        super(context);
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.YEAR, year);
    }

    @Override
    protected void onStartLoading() {
        if(mCalendar != null) {
            if (mAPODResultJSON != null){
                deliverResult(mAPODResultJSON);
            }
            else{
                forceLoad();
            }
        }
    }

    @Override
    public ArrayList<APODUtil.APODItem> loadInBackground() {
        //TODO
        //Hint: https://stackoverflow.com/questions/212321/how-to-subtract-x-days-from-a-date-using-java-calendar
        if (mCalendar != null){
            ArrayList<APODUtil.APODItem> result = new ArrayList<APODUtil.APODItem>();
            try {
                for(int i = 0; i < 20; i++) {
                    int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                    int month = mCalendar.get(Calendar.MONTH);
                    int year = mCalendar.get(Calendar.YEAR);
                    String mAPODUrl = APODUtil.buildAPODURL(day, month, year);
                    result.add(APODUtil.parseAPODJSON(NetworkUtils.doHTTPGet(mAPODUrl)));
                    mCalendar.add(Calendar.DAY_OF_MONTH, -1);
                }
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
    public void deliverResult(ArrayList<APODUtil.APODItem> data) {
        mAPODResultJSON = data;
        super.deliverResult(mAPODResultJSON);
    }
}

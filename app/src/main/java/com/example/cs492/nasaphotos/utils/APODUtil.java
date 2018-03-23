package com.example.cs492.nasaphotos.utils;

/**
 * Created by Aless on 3/13/2018.
 */

import android.net.ParseException;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class APODUtil {
    private final static String APOD_BASE_URl = "https://api.nasa.gov/planetary/apod";
    private final static String APOD_DATE_PARAM = "date";
    private final static String APOD_API_KEY_PARAM= "api_key";
    private final static String APOD_API_KEY_VALUE = "vMrbo2LR9MzZ7gP15CKNUg1hil0YnxQf43dALRiP";


    public static class APODItem implements Serializable {
        public String date;
        public String description;
        public String image_title;
        public String image_url;
        public String media_type;
    }

    public static String buildAPODURL(int day, int month, int year) {
        String date_text = new String(Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day));
        Log.d("BUILDAPODURL", date_text);
        return Uri.parse(APOD_BASE_URl).buildUpon()
                .appendQueryParameter(APOD_DATE_PARAM, date_text)
                .appendQueryParameter(APOD_API_KEY_PARAM, APOD_API_KEY_VALUE)
                .build().toString();
    }

    //Today
    public static APODItem parseAPODJSON(String APODResultJSON){
        //Log.d("APODUtil", APODResultJSON);
        try {

            JSONObject APODObj = new JSONObject(APODResultJSON);

            APODItem ResultItem = new APODItem();
            String itemDate = APODObj.getString("date");
            String itemDescription = APODObj.getString("explanation");
            String itemImageTitle = APODObj.getString("title");
            String itemImageURL = APODObj.getString("url");
            String itemMediaType = APODObj.getString("media_type");


            ResultItem.date = itemDate;
            ResultItem.description = itemDescription;
            ResultItem.image_title = itemImageTitle;
            ResultItem.image_url = itemImageURL;
            ResultItem.media_type = itemMediaType;

            Log.d("APODUtil", itemDate);
            Log.d("APODUtil", itemDescription);
            Log.d("APODUtil", itemImageTitle);
            Log.d("APODUtil", itemImageURL);
            Log.d("APODUtil", itemMediaType);
            if(ResultItem.media_type == "video" || ResultItem.image_url == null) {
                return null;
            }
            return ResultItem;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

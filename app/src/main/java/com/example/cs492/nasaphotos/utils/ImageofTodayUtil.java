package com.example.cs492.nasaphotos.utils;

/**
 * Created by 10463 on 3/11/2018.
 */
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageofTodayUtil {
    final static String DANNY_KEY = "P29hnhHbOt9VpKtIQqFPXisntHkQMdwyj6p0Nb1T";
    final static String IMAGE_OF_TODAY_URL = "https://api.nasa.gov/planetary/apod?api_key=" + DANNY_KEY; //this URL only works for Image of "Today"!

    public static class ImageofToday implements Serializable{
        public String date;
        public String explanation;
        public String image_title;
        public String url;
    }

    public static String buildIODSearchURL(String searchQuery){
        return Uri.parse(IMAGE_OF_TODAY_URL).buildUpon().build().toString();
    }

    //Today
public static ImageofToday parseIODResultsJSON(String IODResultJSON){
        try{
            JSONObject searchResultsObj = new JSONObject(IODResultJSON);
            ImageofToday searchResult = new ImageofToday();
            Log.d("Parse IOD JSON","Processing JSON");
            searchResult.date = searchResultsObj.getString("date");
            Log.d("Parse IOD JSON","Read date");
            searchResult.explanation = searchResultsObj.getString("explanation");
            Log.d("Parse IOD JSON","Read explanation");
            searchResult.image_title = searchResultsObj.getString("title");
            Log.d("Parse IOD JSON","Read title");
            searchResult.url = searchResultsObj.getString("url");
            Log.d("Parse IOD JSON","Read url");
            return searchResult;

        }catch (JSONException e){
            Log.d("Parse IOD JSON","Failed to parse JSON");
            return null;
        }
}

}

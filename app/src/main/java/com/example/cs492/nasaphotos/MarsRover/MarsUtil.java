package com.example.cs492.nasaphotos.MarsRover;

/**
 * Created by 10463 on 3/11/2018.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MarsUtil {
    final static int SOL_NUMBER = 1000;
    final static String DANNY_KEY = "P29hnhHbOt9VpKtIQqFPXisntHkQMdwyj6p0Nb1T";
    final static String MARS_CAMERA_PARAM = "";
    final static String MARS_SOL_PARAM = "sol="+ SOL_NUMBER;
    final static int MARS_PAGE_PARAM = 1;
    final static String BASE_API_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?";

    public static String buildMarsURL(String camera){
        String temp = BASE_API_URL + MARS_SOL_PARAM;
        if(MARS_PAGE_PARAM != 1){
            temp = temp+ "&page=" + MARS_PAGE_PARAM;
        }
        temp = temp +"&"+ MARS_CAMERA_PARAM + "&api_key="+DANNY_KEY;
        return temp;
    }

    public static class Mars implements Serializable{
        //public int image_id;
        //public String image_fullname;
        //public int rover_id;
        //public String earth_date;
        //public String rover_name;
        public String url;
        public Bitmap image;
    }

    private static Bitmap loadingImage(String image_url){
        Bitmap temp = null;
        try{
            InputStream in = new java.net.URL(image_url).openStream();
             temp = BitmapFactory.decodeStream(in);
        }catch(Exception e){
            Log.d("Mars Util Error","Can't read image!");
        }
        return temp;
    }

    public static ArrayList<Mars> parseMarsResultsJSON(String searchResultsJSON){
        try{JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("photos");
            ArrayList<Mars> searchResultsList = new ArrayList<>();
            for(int i = 0; i < searchResultsItems.length(); i++){
                Mars item = new Mars();
                JSONObject temp = searchResultsItems.getJSONObject(i);
                //item.image_id = temp.getInt("id");
                JSONObject subtemp = temp.getJSONObject("camera");
                //item.rover_id = subtemp.getInt("rover_id");
                //item.image_fullname = subtemp.getString("full_name");
                //item.earth_date = temp.getString("earth_date");
                item.url = temp.getString("img_src");
                //item.rover_name = temp.getJSONObject("rover").getString("name");
                item.image = loadingImage(item.url);
                searchResultsList.add(item);
            }
            return searchResultsList;
        }catch(JSONException e){
            Log.d("MarsUtil","JSON read failed!");
            return null;
        }
    }
}

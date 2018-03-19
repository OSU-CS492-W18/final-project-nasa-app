package com.example.cs492.nasaphotos.utils;

import android.net.ParseException;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Francisco on 3/11/2018.
 */

public class DatabaseSearchUtil {
    private final static String DATABASE_BASE_URL = "https://images-api.nasa.gov/search";
    private final static String DATABASE_SEARCH_PARAM = "q";
    private final static String DATABASE_MEDIA_PARAM = "media_type";
    private final static String DATABASE_MEDIA_VALUE = "image";

    public static final String EXTRA_PHOTOS = "photos";
    public static final String EXTRA_PHOTO_IDX = "photoIdx";
    public static final String EXTRA_PHOTO = "photo";

    public static class DatabaseSearchItem implements Serializable {
        public String date;
        public String description;
        public String image_title;
        public String image_url;
        public int index;
    }

    public static String buildDatabaseSearchURL(String searchText){
        return Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendQueryParameter(DATABASE_SEARCH_PARAM, searchText)
                .appendQueryParameter(DATABASE_MEDIA_PARAM, DATABASE_MEDIA_VALUE)
                .build().toString();
    }

    //Today
    public static ArrayList<DatabaseSearchItem> parseDatabaseSearchJSON(String DbSearchResultJSON){
        try {
            JSONObject databaseObj = new JSONObject(DbSearchResultJSON);
            JSONObject collectionObj = databaseObj.getJSONObject("collection");
            JSONArray itemsArray = collectionObj.getJSONArray("items");

            ArrayList<DatabaseSearchItem> SearchItemsList = new ArrayList<DatabaseSearchItem>();
            int mItemListLength = (itemsArray.length()<20)? itemsArray.length():20;

            for (int i = 0; i < mItemListLength; i++) {
                DatabaseSearchItem SearchItem = new DatabaseSearchItem();
                JSONObject itemSearch = itemsArray.getJSONObject(i);

                JSONArray itemREF = itemSearch.getJSONArray("links");
                String imageURL = itemREF.getJSONObject(0).getString("href");

                JSONArray itemData = itemSearch.getJSONArray("data");
                String itemDateCreated = itemData.getJSONObject(0).getString("date_created");
                String itemDescription = itemData.getJSONObject(0).getString("description");
                String itemTitle = itemData.getJSONObject(0).getString("title");

                SearchItem.date = itemDateCreated;
                SearchItem.description = itemDescription;
                SearchItem.image_title = itemTitle;
                SearchItem.image_url = imageURL;

                Log.d("xxxxxxxxxxxxxxxxxxxx", imageURL);
                Log.d("xxxxxxxxxxxxxxxxxxxx", itemDateCreated);
                Log.d("xxxxxxxxxxxxxxxxxxxx", itemDescription);
                Log.d("xxxxxxxxxxxxxxxxxxxx", itemTitle);
                SearchItemsList.add(SearchItem);
            }
            return SearchItemsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

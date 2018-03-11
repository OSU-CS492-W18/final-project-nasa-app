package com.example.cs492.nasaphotos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private final static int SEARCH_API_LOADER_ID = 20;
    private final static String TAG = SearchActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportLoaderManager().initLoader(SEARCH_API_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "URL: " + "https://images-api.nasa.gov/search?q=space" );
        return new DatabaseSearchLoader(this,"https://images-api.nasa.gov/search?q=space" );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null){
            Log.d(TAG, data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}

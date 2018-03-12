package com.example.cs492.nasaphotos.DatabaseSearch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.util.Log;
import android.widget.EditText;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.DatabaseSearchUtil;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private final static int SEARCH_API_LOADER_ID = 20;
    private final static String TAG = SearchActivity.class.getSimpleName();
    private EditText mSearchEditText;
    private String SearchURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchEditText = findViewById(R.id.editText_search);

        SearchURL = "https://images-api.nasa.gov/search?q=space&media_type=image";
        getSupportLoaderManager().initLoader(SEARCH_API_LOADER_ID, null, this);

    }

    public void SearchDatabaseClick(View view){
        String SearchText = mSearchEditText.getText().toString();
        SearchURL = DatabaseSearchUtil.buildDatabaseSearchURL(SearchText);

        if (!TextUtils.isEmpty(SearchText)) {
            getSupportLoaderManager().restartLoader(SEARCH_API_LOADER_ID, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "URL: " + SearchURL );
        return new DatabaseSearchLoader(this, SearchURL );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null){
            Log.d(TAG, data);
            DatabaseSearchUtil.parseDatabaseSearchJSON(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}

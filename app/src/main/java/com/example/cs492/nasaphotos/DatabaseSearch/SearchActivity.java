package com.example.cs492.nasaphotos.DatabaseSearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.util.Log;
import android.widget.EditText;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.DatabaseSearchUtil;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements DatabaseAdapter.OnSearchItemClickListener, LoaderManager.LoaderCallbacks<String>{
    private final static int SEARCH_API_LOADER_ID = 20;
    private final static String TAG = SearchActivity.class.getSimpleName();
    private final static String SEARCH_RESULTS = "searchResult";

    private EditText mSearchEditText;
    private String SearchURL;
    private RecyclerView mImageListRecyclerView;
    private DatabaseAdapter mAdapter;
    private ArrayList<DatabaseSearchUtil.DatabaseSearchItem> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEditText = findViewById(R.id.editText_search);
        mImageListRecyclerView = findViewById(R.id.rv_database_result);

        mImageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mImageListRecyclerView.setHasFixedSize(true);

        mAdapter = new DatabaseAdapter(this);
        mImageListRecyclerView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(SEARCH_API_LOADER_ID,null, this);
    }

    public void SearchDatabaseClick(View view){
        String SearchText = mSearchEditText.getText().toString();
        SearchURL = DatabaseSearchUtil.buildDatabaseSearchURL(SearchText);

        if (!TextUtils.isEmpty(SearchText)) {
            getSupportLoaderManager().restartLoader(SEARCH_API_LOADER_ID, null, this);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchItemClick(DatabaseSearchUtil.DatabaseSearchItem searchResult) {
        Intent detailedSearchResultIntent = new Intent(this, DatabaseSearchDetailedActivity.class);
        detailedSearchResultIntent.putExtra(DatabaseSearchUtil.EXTRA_PHOTOS,mImageList);
        detailedSearchResultIntent.putExtra(DatabaseSearchUtil.EXTRA_PHOTO_IDX, searchResult.index);
        detailedSearchResultIntent.putExtra(DatabaseSearchUtil.EXTRA_PHOTO, searchResult);
        startActivity(detailedSearchResultIntent);
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
            mImageList = DatabaseSearchUtil.parseDatabaseSearchJSON(data);
            mAdapter.updateSearchResult(mImageList);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}

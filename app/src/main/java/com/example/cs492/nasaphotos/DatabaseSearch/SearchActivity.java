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
import android.support.v7.widget.SearchView;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.DatabaseSearchUtil;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements DatabaseAdapter.OnSearchItemClickListener, LoaderManager.LoaderCallbacks<String>, SearchView.OnQueryTextListener{
    private final static int SEARCH_API_LOADER_ID = 20;
    private final static String TAG = SearchActivity.class.getSimpleName();
    private final static String SEARCH_RESULTS = "searchResult";

    private String SearchURL;
    private RecyclerView mImageListRecyclerView;
    private DatabaseAdapter mAdapter;
    private ArrayList<DatabaseSearchUtil.DatabaseSearchItem> mImageList;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mImageListRecyclerView = findViewById(R.id.rv_database_result);
        mSearchView = findViewById(R.id.search_bar);
        mSearchView.setOnQueryTextListener(this);
        EditText searchEditor = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditor.setTextColor(getResources().getColor(R.color.white));
        searchEditor.setHintTextColor(getResources().getColor(R.color.LightWhite));

        mImageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mImageListRecyclerView.setHasFixedSize(true);

        mAdapter = new DatabaseAdapter(this);
        mImageListRecyclerView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(SEARCH_API_LOADER_ID,null, this);
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        SearchURL = DatabaseSearchUtil.buildDatabaseSearchURL(query);
        Log.d(TAG, "SearchDatabaseClick: "+ query);

        if (!TextUtils.isEmpty(query)) {
            getSupportLoaderManager().restartLoader(SEARCH_API_LOADER_ID, null, this);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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

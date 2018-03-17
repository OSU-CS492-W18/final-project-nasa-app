package com.example.cs492.nasaphotos.MarsRover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;

import java.util.ArrayList;

public class MarsRoverActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String TAG = MarsRoverActivity.class.getSimpleName();

    private RecyclerView mMarsListRV;
    private MarsAdapter mMarsAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private static final String MARS_URL_KEY = "marsroverURL";
    private static final int MARS_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover_main);
        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);

        mMarsListRV = (RecyclerView)findViewById(R.id.rv_photos);
        mMarsListRV.setLayoutManager(new LinearLayoutManager(this));
        mMarsListRV.setHasFixedSize(true);
        mMarsAdapter = new MarsAdapter();
        mMarsListRV.setAdapter(mMarsAdapter);

        loadMarsList(true);


    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args){
        //Log.d(TAG, "Test point 2");
        String MarsURL = null;
        if(args != null){
            MarsURL = args.getString(MARS_URL_KEY);
        }
        return new MarsLoader(this, MarsURL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        Log.d(TAG, "loader finished loading");
        if (data != null) {
            ArrayList<MarsUtil.Mars> searchResults = MarsUtil.parseMarsResultsJSON(data);
            if(searchResults == null){
            Log.d(TAG,"searchResult is null");}
            mMarsAdapter.updateMarsData(searchResults);
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mMarsListRV.setVisibility(View.VISIBLE);
        } else {
            mMarsListRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<String> loader) {

        // Nothing to do...
    }

    public void loadMarsList(boolean initialLoad){
        String MarsURL = MarsUtil.buildMarsURL();
        Bundle loaderArgs = new Bundle();
        loaderArgs.putString(MARS_URL_KEY, MarsURL);
        LoaderManager loaderManager = getSupportLoaderManager();
        if(initialLoad){
            loaderManager.initLoader(MARS_LOADER_ID, loaderArgs, this);
        }else{
            loaderManager.restartLoader(MARS_LOADER_ID,loaderArgs,this);
        }
    }

}

package com.example.cs492.nasaphotos.MarsRover;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;

public class MarsRoverActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,FlickrPhotoGridAdapter.OnPhotoItemClickedListener {
    private static final String TAG = MarsRoverActivity.class.getSimpleName();
    private static final int FLICKR_EXPLORE_LOADER_ID = 0;
    private static final int NUM_PHOTO_COLUMNS = 2;

    private RecyclerView mPhotosRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private FlickrPhotoGridAdapter mAdapter;

    private FlickrUtils.FlickrPhoto[] mPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover_main);

        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);
        mPhotosRV = (RecyclerView)findViewById(R.id.rv_photos);

        mAdapter = new FlickrPhotoGridAdapter(this);
        mPhotosRV.setAdapter(mAdapter);

        mPhotosRV.setHasFixedSize(true);
        mPhotosRV.setLayoutManager(new StaggeredGridLayoutManager(NUM_PHOTO_COLUMNS, StaggeredGridLayoutManager.VERTICAL));

        mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(FLICKR_EXPLORE_LOADER_ID, null, this);
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d(TAG,"Create loader");
        return new MarsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (data != null) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mPhotosRV.setVisibility(View.VISIBLE);
            Log.d(TAG,"Go to URL: "+ data);
            mPhotos = FlickrUtils.parseFlickrExploreResultsJSON(data);
            mAdapter.updatePhotos(mPhotos);
            for (FlickrUtils.FlickrPhoto photo : mPhotos) {
                Log.d(TAG, "Got photo: " + photo.url_m);
            }
        } else {
            mPhotosRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // Nothing.
    }

    @Override
    public void onPhotoItemClicked(int photoIdx) {
        Intent intent = new Intent(this, MarsViewActivity.class);
        intent.putExtra(MarsViewActivity.EXTRA_PHOTOS, mPhotos);
        intent.putExtra(MarsViewActivity.EXTRA_PHOTO_IDX, photoIdx);
        startActivity(intent);
    }

}

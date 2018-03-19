package com.example.cs492.nasaphotos.PictureOfDay;

import android.app.LoaderManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.APODUtil;

import java.util.ArrayList;


//API KEY = vMrbo2LR9MzZ7gP15CKNUg1hil0YnxQf43dALRiP
public class PictureOfDayActivity extends AppCompatActivity implements APODAdapter.onAPODItemClickListener, LoaderManager.LoaderCallbacks<String> {
    private final static int APOD_API_LOADER_ID = 12;
    private final static String TAG = PictureOfDayActivity.class.getSimpleName();
    private RecyclerView mAPODListRV;
    private String APODURL;
    private APODAdapter mAPODAdapter;

    private ArrayList<APODUtil.APODItem> mImageList;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_of_day);

        mAPODListRV = (RecyclerView) findViewById(R.id.rv_APOD_result);
        mAPODListRV.setLayoutManager(new LinearLayoutManager(this));
        mAPODListRV.setHasFixedSize(true);

        mAPODAdapter = new APODAdapter(this);
        mAPODListRV.setAdapter(mAPODAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle args = new Bundle();
        getSupportLoaderManager().restartLoader(APOD_API_LOADER_ID, args, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "url: " + APODURL);
        return new APODLoader(this, APODURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data!=null) {
            Log.d(TAG, data);
            mImageList = APODUtil.parseAPODJSON(data);
            mAPODAdapter.updateAPODlist(mImageList);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onAPODItemClick()
}

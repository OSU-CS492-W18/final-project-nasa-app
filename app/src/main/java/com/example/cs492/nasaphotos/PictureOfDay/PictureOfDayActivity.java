package com.example.cs492.nasaphotos.PictureOfDay;

import android.support.v4.app.LoaderManager;
import android.content.Intent;
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
import java.util.Calendar;


//API KEY = vMrbo2LR9MzZ7gP15CKNUg1hil0YnxQf43dALRiP
public class PictureOfDayActivity extends AppCompatActivity implements APODAdapter.onAPODItemClickListener, LoaderManager.LoaderCallbacks<ArrayList<APODUtil.APODItem>> {
    private final static int APOD_API_LOADER_ID = 12;
    private final static String TAG = PictureOfDayActivity.class.getSimpleName();
    private RecyclerView mAPODListRV;
    private String APODURL;
    private APODAdapter mAPODAdapter;

    private ArrayList<APODUtil.APODItem> mImageList;

    private Calendar mCalendar;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_of_day);

        mAPODListRV = (RecyclerView) findViewById(R.id.rv_APOD_result);
        mAPODListRV.setLayoutManager(new LinearLayoutManager(this));
        mAPODListRV.setHasFixedSize(true);

        mAPODAdapter = new APODAdapter(this);
        mAPODListRV.setAdapter(mAPODAdapter);

        mCalendar = Calendar.getInstance();
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        loadAPOD(day, month, year,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle args = new Bundle();
        getSupportLoaderManager().restartLoader(APOD_API_LOADER_ID, args, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<APODUtil.APODItem>> onCreateLoader(int id, @Nullable Bundle args) {
        int day = 0;
        int month = 0;
        int year = 0;
        if(args != null) {

            day = args.getInt("day");
            month = args.getInt("month");
            year = args.getInt("year");
        }
        return new APODLoader(this, day,month,year);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<APODUtil.APODItem>> loader, ArrayList<APODUtil.APODItem> data) {
        if(data!=null) {
            //Log.d(TAG, data);
            mImageList = data;
            mAPODAdapter.updateAPODlist(mImageList);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<APODUtil.APODItem>> loader) {

    }

    @Override
    public void onAPODItemClick(APODUtil.APODItem APODresult) {
        Intent detailedAPODIntent = new Intent(this, APODDetailedActivity.class);
        detailedAPODIntent.putExtra("photo", APODresult);

    }

    public void loadAPOD(int day, int month, int year, boolean initialLoad) {
        Bundle loaderArgs = new Bundle();
        loaderArgs.putInt("day", day);
        loaderArgs.putInt("month", month);
        loaderArgs.putInt("year", year);
        //TODO
        LoaderManager loaderManager = getSupportLoaderManager();
        if(!initialLoad) {
            loaderManager.initLoader(APOD_API_LOADER_ID, loaderArgs, this);
        }
        else {
            loaderManager.restartLoader(APOD_API_LOADER_ID, loaderArgs, this);
        }
    }
}

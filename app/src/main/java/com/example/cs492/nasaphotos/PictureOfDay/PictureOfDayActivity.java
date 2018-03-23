package com.example.cs492.nasaphotos.PictureOfDay;

import android.support.v4.app.DialogFragment;
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
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.APODUtil;

import java.util.ArrayList;
import java.util.Calendar;


//API KEY = vMrbo2LR9MzZ7gP15CKNUg1hil0YnxQf43dALRiP
public class PictureOfDayActivity extends AppCompatActivity implements DatePickerFragment.DatePickerFragmentListener, APODAdapter.onAPODItemClickListener, LoaderManager.LoaderCallbacks<ArrayList<APODUtil.APODItem>> {
    private final static int APOD_API_LOADER_ID = 12;
    private final static String TAG = PictureOfDayActivity.class.getSimpleName();
    private RecyclerView mAPODListRV;
    private String APODURL;
    private APODAdapter mAPODAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;


    private ArrayList<APODUtil.APODItem> mImageList;

    private Calendar mCalendar;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_of_day);

        mAPODListRV = (RecyclerView) findViewById(R.id.rv_APOD_result);
        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);
        mAPODListRV.setLayoutManager(new LinearLayoutManager(this));
        mAPODListRV.setHasFixedSize(true);

        mAPODAdapter = new APODAdapter(this);
        mAPODListRV.setAdapter(mAPODAdapter);

        mCalendar = Calendar.getInstance();
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        loadAPOD(day, month, year,true);
        DatePickerFragment fragment = DatePickerFragment.newInstance(this);

        //https://stackoverflow.com/questions/36127734/detect-when-recyclerview-reaches-the-bottom-most-position-while-scrolling
        /*mAPODListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if()
            }
        });*/
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Bundle args = new Bundle();
        //getSupportLoaderManager().restartLoader(APOD_API_LOADER_ID, args, this);
    }*/

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
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        }
        else {
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<APODUtil.APODItem>> loader) {

    }

    @Override
    public void onAPODItemClick(APODUtil.APODItem APODresult) {
        Log.d(TAG, "Picture clicked");
        Intent detailedAPODIntent = new Intent(this, APODDetailedActivity.class);
        detailedAPODIntent.putExtra("photo", APODresult);
        startActivity(detailedAPODIntent);

    }

    public void loadAPOD(int day, int month, int year, boolean initialLoad) {
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        Bundle loaderArgs = new Bundle();
        loaderArgs.putInt("day", day);
        loaderArgs.putInt("month", month);
        loaderArgs.putInt("year", year);
        //TODO
        LoaderManager loaderManager = getSupportLoaderManager();
        if(initialLoad) {
            Log.d(TAG, "Initializing Loader");
            loaderManager.initLoader(APOD_API_LOADER_ID, loaderArgs, this);
        }
        else {
            Log.d(TAG, "Restarting Loader");
            loaderManager.restartLoader(APOD_API_LOADER_ID, loaderArgs, this);
        }
    }

    public void showDatePickerDialog(View V) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(this);
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.d(TAG, "onDateSet called");
        loadAPOD(day, month, year, false);
    }
}

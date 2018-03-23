package com.example.cs492.nasaphotos.MarsRover;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.cs492.nasaphotos.MarsRover.Settings.SettingsActivity;
import com.example.cs492.nasaphotos.R;
import java.util.ArrayList;

public class MarsRoverActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,SharedPreferences.OnSharedPreferenceChangeListener, MarsAdapter.OnMarsItemClickListener {
    private static final String TAG = MarsRoverActivity.class.getSimpleName();

    private RecyclerView mMarsListRV;
    private MarsAdapter mMarsAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private static final String MARS_URL_KEY = "marsroverURL";
    private static final int MARS_LOADER_ID = 0;
    private TextView mCameraTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover_main);
        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);
        mCameraTV = findViewById(R.id.tv_camera_name);

        mMarsListRV = (RecyclerView)findViewById(R.id.rv_photos);
        mMarsListRV.setLayoutManager(new LinearLayoutManager(this));
        mMarsListRV.setHasFixedSize(true);
        mMarsAdapter = new MarsAdapter(this, this);
        mMarsListRV.setAdapter(mMarsAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        loadMarsList(sharedPreferences,true);


    }

    //Loader
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
        Log.d(TAG, "loader finished loading");
        if (data != null) {
            ArrayList<MarsUtil.Mars> searchResults = MarsUtil.parseMarsResultsJSON(data);
            if(searchResults == null){
            Log.d(TAG,"searchResult is null");}
            if(searchResults.size()==0){
                Log.d(TAG, "No image return by this camera");
            }
            mMarsAdapter.updateMarsData(searchResults);
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mMarsListRV.setVisibility(View.VISIBLE);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        } else {
            mMarsListRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<String> loader) {

        // Nothing to do...
    }

    //function that activing loader. Mars List has many image, so Loader only loads JSON file, not image.
    public void loadMarsList(SharedPreferences sharedPreferences, boolean initialLoad){
        String camera = sharedPreferences.getString(getString(R.string.pref_camera_key),getString(R.string.pref_camera_default_value));
        mCameraTV.setText(Camera_name(camera));
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);

        String MarsURL = MarsUtil.buildMarsURL(camera);
        Bundle loaderArgs = new Bundle();
        loaderArgs.putString(MARS_URL_KEY, MarsURL);
        LoaderManager loaderManager = getSupportLoaderManager();
        if(initialLoad){
            loaderManager.initLoader(MARS_LOADER_ID, loaderArgs, this);
        }else{
            loaderManager.restartLoader(MARS_LOADER_ID,loaderArgs,this);
        }
    }

    //change Camera name from short name to full name
    public String Camera_name(String camera){
        switch(camera){
            case "FHAZ":
                return "Front Hazard Avoidance Camera";
            case "RHAZ":
                return "Rear Hazard Avoidance Camera";
            case "MAST":
                return "Mast Camera";
            case "CHEMCAM":
                return "Chemistry and Camera Complex";
            case "MAHLI":
                return "Mars Hand Lens Imager";
            case "MARDI":
                return "Mars Descent Imager";
            case "NAVCAM":
                return "Navigation Camera";
            case "PANCAM":
                return "Panoramic Camera";
            case "MINITES":
                return "Miniature Thermal Emission Spectrometer (Mini-TES)";
            case "":
                return "All camera";
            default:
                return "All camera";
        }

    }


    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadMarsList(sharedPreferences, false);
    }

    //to detail activity
    @Override
    public void onMarsItemClick(MarsUtil.Mars item){
        Intent intent = new Intent(this, MarsViewActivity.class);
        intent.putExtra(MarsUtil.MARS_PHOTO, item);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        loadMarsList(sharedPreferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mars_main, menu);
        return true;
    }


    //to setting Option Item
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}

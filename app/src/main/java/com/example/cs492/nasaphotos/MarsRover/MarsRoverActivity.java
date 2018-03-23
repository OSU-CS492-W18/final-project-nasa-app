package com.example.cs492.nasaphotos.MarsRover;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs492.nasaphotos.MarsRover.Settings.SettingsActivity;
import com.example.cs492.nasaphotos.MarsRover.Settings.SettingsFragment;
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
    private ImageView imageView;
    boolean isImageFitToScreen;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitleNV;
    private Toolbar toolbar;
    private TextView mCameraTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover_main);
        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);
        mTitleNV = getTitle();
        mCameraTV = findViewById(R.id.tv_camera_name);

        mMarsListRV = (RecyclerView)findViewById(R.id.rv_photos);
        mMarsListRV.setLayoutManager(new LinearLayoutManager(this));
        mMarsListRV.setHasFixedSize(true);
        mMarsAdapter = new MarsAdapter(this, this);
        mMarsListRV.setAdapter(mMarsAdapter);
        imageView = (ImageView)findViewById(R.id.imageView);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        loadMarsList(sharedPreferences,true);


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


    /*
    <string name = "pref_camera_all">All cameras</string>
    <string name = "pref_camera_fhaz">Front Hazard Avoidance Camera</string>
    <string name = "pref_camera_rhaz">Rear Hazard Avoidance Camera</string>
    <string name = "pref_camera_mast">Mast Camera</string>
    <string name = "pref_camera_chemcam">Chemistry and Camera Complex</string>
    <string name = "pref_camera_mahli">Mars Hand Lens Imager</string>
    <string name = "pref_camera_mardi">Mars Descent Imager</string>
    <string name = "pref_camera_navcam">Navigation Camera</string>
    <string name = "pref_camera_pancam">Panoramic Camera</string>
    <string name = "pref_camera_minites">Miniature Thermal Emission Spectrometer (Mini-TES)	</string>
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }*/

    @Override
    protected void onResume(){
        super.onResume();
    }

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

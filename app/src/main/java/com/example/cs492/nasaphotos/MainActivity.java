package com.example.cs492.nasaphotos;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492.nasaphotos.DatabaseSearch.SearchActivity;
import com.example.cs492.nasaphotos.PictureOfDay.PictureOfDayActivity;
import com.example.cs492.nasaphotos.MarsRover.MarsRoverActivity;
import com.example.cs492.nasaphotos.utils.ImageofTodayUtil;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap>, NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int IMAGE_MAIN_LOADER= 0;
    private ImageView mImageview;
    private String mImageUrl;
    private Intent mSearchIntent;
    private TextView mLoadingErrorMessage;
    private Intent mMarsIntent;

    private Intent mAPODIntent;

    //AsyncTask
    private ProgressBar mLoadingProgressBar;


    //MainActivity values for Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // example URL
        mLoadingProgressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessage = (TextView)findViewById(R.id.tv_loading_error);
        String ImageofTodayURL = ImageofTodayUtil.buildIODSearchURL();
        //new ImageTask().execute(ImageofTodayURL);
        mImageUrl = ImageofTodayURL;

        mImageview = findViewById(R.id.image_1);
        //mImageUrl = "https://apod.nasa.gov/apod/image/1803/Cycle-Panel-1200px.jpg";
        //NavigationView code here:
        mDrawerLayout =
                (DrawerLayout)findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout,
                        R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        NavigationView navigationView = findViewById(R.id.nv_navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        //Navigation code end here.

        //loader for image
        getSupportLoaderManager().initLoader(IMAGE_MAIN_LOADER,null, this);
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new ImageLoader(this, mImageUrl);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        if(data !=null){
            Log.d(TAG, "got results from loader");
            //attaches result to image view
            mImageview.setImageBitmap(data);
        }
        else{
            //failed search
            Log.d(TAG, "got results from loader");
        }
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
        // nothing
    }

    //Function for Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Function for Navigation Drawer
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //Function for Navigation Drawer
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    //Using id to create new Intent and open new activity. This is the easy way to do our project for now.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_iod_list:
                mAPODIntent = new Intent( this, PictureOfDayActivity.class);
                startActivity(mAPODIntent);
                return true;

            case R.id.nav_mars_list:
                mMarsIntent = new Intent(this, MarsRoverActivity.class);
                startActivity(mMarsIntent);
                return true;

            case R.id.nav_search:
                mSearchIntent = new Intent(this, SearchActivity.class);
                startActivity(mSearchIntent);
                return true;

            default:
                return false;
        }
    }

}

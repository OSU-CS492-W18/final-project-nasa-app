package com.example.cs492.nasaphotos.MarsRover;

import android.support.v4.app.LoaderManager;
import android.content.Loader;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;


import java.util.ArrayList;

public class MarsRoverActivity extends AppCompatActivity {

    private final static String TAG = MarsRoverActivity.class.getSimpleName();
    private final static int MARS_LOADER_ID = 0;
    private RecyclerView mSearchResultRV;

    private ProgressBar mLoadingProgressBar;
    private TextView mLoadingErrorMessage;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mCameraTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover_main);

        //mCameraTV = findViewById(R.id.tv_camera_name);

        mLoadingProgressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessage = (TextView)findViewById(R.id.tv_loading_error);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }



}

package com.example.cs492.nasaphotos;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap>{
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int IMAGE_MAIN_LOADER= 0;
    private ImageView mImageview;
    private String mImageUrl;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageview = findViewById(R.id.image_1);
        // example URL
        mImageUrl = "https://apod.nasa.gov/apod/image/1803/Cycle-Panel-1200px.jpg";

        //loader for image
        getSupportLoaderManager().initLoader(IMAGE_MAIN_LOADER,null, this);
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new ImageRenderingLoader(this, mImageUrl);
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
}

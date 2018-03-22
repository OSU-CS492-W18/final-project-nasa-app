package com.example.cs492.nasaphotos.MarsRover;

/**
 * Created by 10463 on 3/17/2018.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.MarsRover.MarsUtil;





    public class MarsViewActivity extends AppCompatActivity{
        /**
         * Whether or not the system UI should be auto-hidden after
         * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
         */
        private static final boolean AUTO_HIDE = true;

        /**
         * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private static final int UI_ANIMATION_DELAY = 300;
        private final Handler mHideHandler = new Handler();
        private View mContentView;
        private final Runnable mHidePart2Runnable = new Runnable() {
            @SuppressLint("InlinedApi")
            @Override
            public void run() {
                // Delayed removal of status and navigation bar

                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        };


        private final Runnable mShowPart2Runnable = new Runnable() {
            @Override
            public void run() {
                // Delayed display of UI elements
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.show();
                }
            }
        };

        private boolean mVisible;
        private final Runnable mHideRunnable = new Runnable() {
            @Override
            public void run() {
                hide();
            }
        };
        /**
         * Touch listener to use for in-layout UI controls to delay hiding the
         * system UI. This is to prevent the jarring behavior of controls going away
         * while interacting with activity UI.
         */
        private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (AUTO_HIDE) {
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                }
                return false;
            }
        };

        private ImageView mPhotoFS;
        private TextView mTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_mars_rover_photo_view);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            mVisible = true;
            mContentView = findViewById(R.id.fullscreen_content);
            mPhotoFS = findViewById(R.id.fullscreen_photo);
            mTextView = findViewById(R.id.fullscreen_description);

            // Set up the user interaction to manually show or hide the system UI.
            mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle();
                }
            });


            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(MarsUtil.MARS_PHOTO)) {
                MarsUtil.Mars photo = (MarsUtil.Mars) intent.getSerializableExtra(MarsUtil.MARS_PHOTO);
                //DatabaseSearchUtil.DatabaseSearchItem photo = (DatabaseSearchUtil.DatabaseSearchItem) intent.getSerializableExtra(DatabaseSearchUtil.EXTRA_PHOTO);
                Glide.with(mPhotoFS.getContext())
                        .load(photo.url)
                        .into(mPhotoFS);
                //String imageInfo = photo.image_title + ": \n" + photo.date + "\n" + photo.description;
                //mTextView.setText(imageInfo);
            }
        }

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            // Trigger the initial hide() shortly after the activity has been
            // created, to briefly hint to the user that UI controls
            // are available.
            delayedHide(100);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;

                case R.id.mars_share:
                    shareImage();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.view_mars, menu);
            return true;

        }

        private void toggle() {
            if (mVisible) {
                hide();
            } else {
                show();
            }
        }

        private void hide() {
            // Hide UI first
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
            //mTextView.setVisibility(View.INVISIBLE);

            // Schedule a runnable to remove the status and navigation bar after a delay
            mHideHandler.removeCallbacks(mShowPart2Runnable);
            mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        }

        @SuppressLint("InlinedApi")
        private void show() {
            // Show the system bar
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            mVisible = true;
            //mTextView.setVisibility(View.VISIBLE);
            // Schedule a runnable to display UI elements after a delay
            mHideHandler.removeCallbacks(mHidePart2Runnable);
            mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
        }

        /**
         * Schedules a call to hide() in delay milliseconds, canceling any
         * previously scheduled calls.
         */
        private void delayedHide(int delayMillis) {
            mHideHandler.removeCallbacks(mHideRunnable);
            mHideHandler.postDelayed(mHideRunnable, delayMillis);
        }

        private void shareImage(){
            Intent intent = getIntent();
            MarsUtil.Mars photo = (MarsUtil.Mars) intent.getSerializableExtra(MarsUtil.MARS_PHOTO);
            String messageText = photo.url + "\n";
            ShareCompat.IntentBuilder.from(this)
                    .setChooserTitle("choose method of sharing your Image")
                    .setType("text/plain")
                    .setText(messageText)
                    .startChooser();
        }


    }



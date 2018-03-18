package com.example.cs492.nasaphotos.PictureOfDay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.APODUtil;

import java.util.ArrayList;


//API KEY = vMrbo2LR9MzZ7gP15CKNUg1hil0YnxQf43dALRiP
public class PictureOfDayActivity extends AppCompatActivity {
    private ArrayList<APODUtil.APODItem> APODlist;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_of_day);
    }
}

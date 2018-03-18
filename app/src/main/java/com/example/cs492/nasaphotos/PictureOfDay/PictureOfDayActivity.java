package com.example.cs492.nasaphotos.PictureOfDay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.APODUtil;

import java.util.ArrayList;


//API KEY = vMrbo2LR9MzZ7gP15CKNUg1hil0YnxQf43dALRiP
public class PictureOfDayActivity extends AppCompatActivity implements APODAdapter.onAPODItemClickListener {
    private final static int APOD_API_LOADER_ID = 12;
    private RecyclerView mAPODListRV;
    private APODAdapter mAPODAdapter;
    private ProgressBar mLoadingIndicatorPB;
    

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
}

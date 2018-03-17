package com.example.cs492.nasaphotos.MarsRover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cs492.nasaphotos.R;

/**
 * Created by 10463 on 3/17/2018.
 */

public class FlickrPhotoPagerAdapter extends FragmentStatePagerAdapter {
    public FlickrPhotoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private static FlickrUtils.FlickrPhoto[] mPhotos;
    @Override
    public int getCount() {
        if (mPhotos != null) {
            return mPhotos.length;
        } else {
            return 0;
        }
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FlickrPhotoFragment();
        Bundle args = new Bundle();
        args.putString(FlickrPhotoFragment.ARG_PHOTO_URL,
                mPhotos[position].url_l);
        fragment.setArguments(args);
        return fragment;
    }

    public void updatePhotos(FlickrUtils.FlickrPhoto[] photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    public static class FlickrPhotoFragment extends Fragment{
        public static final String ARG_PHOTO_URL = "photoURL";
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            View rootView =
                    inflater.inflate(R.layout.mars_photo_grid_item,
                            container, false);
            Bundle args = getArguments();
            ImageView photoIV =
                    (ImageView)rootView.findViewById(R.id.iv_photo);
            Glide.with(photoIV.getContext())
                    .load(args.getString(ARG_PHOTO_URL))
                    .into(photoIV);
            return rootView;
        }


    }

}

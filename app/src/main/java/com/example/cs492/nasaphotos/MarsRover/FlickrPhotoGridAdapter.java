package com.example.cs492.nasaphotos.MarsRover;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cs492.nasaphotos.R;

/**
 * Created by 10463 on 3/16/2018.
 */

public class FlickrPhotoGridAdapter extends RecyclerView.Adapter<FlickrPhotoGridAdapter.FlickrPhotoViewHolder>{
    private FlickrUtils.FlickrPhoto[] mPhotos;

    public void updatePhotos(FlickrUtils.FlickrPhoto[] photos){
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPhotos != null) {
            return mPhotos.length;
        } else {
            return 0;
        }
    }

    class FlickrPhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView mPhotoIV;

        public FlickrPhotoViewHolder(View itemView){
            super(itemView);
            mPhotoIV =(ImageView)itemView.findViewById(R.id.iv_photo);
        }

        public void bind(FlickrUtils.FlickrPhoto photo){
            Glide.with(mPhotoIV.getContext()).load(photo.url_m).into(mPhotoIV);
        }
    }

    @Override
    public FlickrPhotoViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mars_photo_grid_item,
                parent, false);
        return new FlickrPhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlickrPhotoViewHolder holder,
                                 int position) {
        holder.bind(mPhotos[position]);

    }
}

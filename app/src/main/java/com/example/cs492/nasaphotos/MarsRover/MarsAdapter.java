package com.example.cs492.nasaphotos.MarsRover;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;

import java.util.ArrayList;

/**
 * Created by 10463 on 3/17/2018.
 */

public class MarsAdapter extends RecyclerView.Adapter<MarsAdapter.MarsItemViewHolder> {
    private ArrayList<MarsUtil.Mars> mMarsList;

    public MarsAdapter(){
    }

    public void updateMarsData(ArrayList<MarsUtil.Mars> MarsData){
        mMarsList = MarsData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mMarsList!=null){
            return mMarsList.size();
        }else {
            return 0;
        }
    }

    @Override
    public MarsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mars_list_item, parent, false);
        return new MarsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarsItemViewHolder holder, int position){
        holder.bind(mMarsList.get(position));
    }

    class MarsItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mMarsTextView;

        public MarsItemViewHolder(View itemView){
            super(itemView);
            mMarsTextView = (TextView)itemView.findViewById(R.id.tv_image_id);

        }
        public void bind(MarsUtil.Mars mars){
            if(mars==null){
                Log.d("MarsAdapter","bind a null value");
            }
            else{
            mMarsTextView.setText("image id: "+mars.image_id);}


        }
    }

}

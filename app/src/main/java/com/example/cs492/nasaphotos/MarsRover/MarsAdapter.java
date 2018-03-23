package com.example.cs492.nasaphotos.MarsRover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.cs492.nasaphotos.R;

import java.util.ArrayList;

/**
 * Created by 10463 on 3/17/2018.
 */

public class MarsAdapter extends RecyclerView.Adapter<MarsAdapter.MarsItemViewHolder> {
    private ArrayList<MarsUtil.Mars> mMarsList;
    private OnMarsItemClickListener mMarsItemClickListener;
    private Context mContext;

    public MarsAdapter(Context context, OnMarsItemClickListener clickListener){
        mContext = context;
        mMarsItemClickListener = clickListener;
    }

    public interface OnMarsItemClickListener{
        void onMarsItemClick(MarsUtil.Mars item);
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

    class MarsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //private TextView mMarsTextView;
        private ImageView mMarsIV;

        public MarsItemViewHolder(View itemView){
            super(itemView);
            mMarsIV = (ImageView) itemView.findViewById(R.id.tv_image_id);
            itemView.setOnClickListener(this);

        }
        public void bind(MarsUtil.Mars mars){
            Glide.with(mMarsIV.getContext()).load(mars.url).into(mMarsIV);


        }

        @Override
        public void onClick(View v){
            MarsUtil.Mars item = mMarsList.get(getAdapterPosition());
            Log.d("onClick","Click on image id:"+item.image_id);
            mMarsItemClickListener.onMarsItemClick(item);
        }
    }

}

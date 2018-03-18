package com.example.cs492.nasaphotos.PictureOfDay;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.APODUtil;

import java.util.ArrayList;

/**
 * Created by Aless on 3/13/2018.
 */

public class APODAdapter extends RecyclerView.Adapter<APODAdapter.APODViewHolder> {
    private ArrayList<APODUtil.APODItem> mAPODlist;
    onAPODItemClickListener mAPODItemClickListener;

    APODAdapter(onAPODItemClickListener APODItemClickListener) {
        mAPODItemClickListener = APODItemClickListener;
    }

    public interface onAPODItemClickListener {
        void onAPODItemClick(APODUtil.APODItem pictureitem);
    }

    public void updateAPODlist(ArrayList<APODUtil.APODItem> newlist) {
        mAPODlist = newlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAPODlist != null) {
            return mAPODlist.size();
        }
        else {
            return 0;
        }
    }

    @NonNull
    @Override
    public APODViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.apod_list_item, parent, false);
        return new APODViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull APODViewHolder holder, int position) {
        holder.bind(mAPODlist.get(position));
    }

    public class APODViewHolder extends RecyclerView.ViewHolder{
        private TextView mAPODResultTV;

        public APODViewHolder(View itemView) {
            super(itemView);
            mAPODResultTV = itemView.findViewById(R.id.tv_date_result);
        }

        public void bind(APODUtil.APODItem image) {
            mAPODResultTV.setText(image.image_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APODUtil.APODItem APODResult = mAPODlist.get(getAdapterPosition());
                    mAPODItemClickListener.onAPODItemClick(APODResult);
                }
            });
        }
    }
}
package com.example.cs492.nasaphotos.DatabaseSearch;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs492.nasaphotos.R;
import com.example.cs492.nasaphotos.utils.DatabaseSearchUtil;

import java.util.ArrayList;
/**
 * Created by Francisco on 3/11/2018.
 */

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.DatabaseViewHolder>{

    private ArrayList<DatabaseSearchUtil.DatabaseSearchItem> mSearchList;
    OnSearchItemClickListener mSeachItemClickListener;


    DatabaseAdapter(OnSearchItemClickListener searchItemClickListener){
        mSeachItemClickListener = searchItemClickListener;
    }

    public interface OnSearchItemClickListener {
        void onSearchItemClick(DatabaseSearchUtil.DatabaseSearchItem searchResult);
    }

    public void updateSearchResult(ArrayList<DatabaseSearchUtil.DatabaseSearchItem> searchList){
        mSearchList = searchList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSearchList != null) {
            return mSearchList.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public DatabaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.database_list_item,parent,false);
        return new DatabaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseViewHolder holder, int position) {
        holder.bind(mSearchList.get(position));
    }


    public class DatabaseViewHolder extends RecyclerView.ViewHolder{

        private TextView mImageResultTV;

        public DatabaseViewHolder(View itemView) {
            super(itemView);
            mImageResultTV = itemView.findViewById(R.id.tv_image_result);
        }

        public void bind(DatabaseSearchUtil.DatabaseSearchItem image) {
            mImageResultTV.setText(image.image_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseSearchUtil.DatabaseSearchItem searchResult = mSearchList.get(getAdapterPosition());
                    mSeachItemClickListener.onSearchItemClick(searchResult);
                }
            });
        }
    }
}

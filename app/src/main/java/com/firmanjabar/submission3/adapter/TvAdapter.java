package com.firmanjabar.submission3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firmanjabar.submission3.MovieDetailsActivity;
import com.firmanjabar.submission3.R;
import com.firmanjabar.submission3.TvDetaislActivity;
import com.firmanjabar.submission3.model.MovieModel;
import com.firmanjabar.submission3.model.TvModel;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TvModel> mData;

    public TvAdapter ( Context mContext ) {
        this.mContext = mContext;
    }

    public ArrayList<TvModel> getmData () {
        return mData;
    }

    public void setmData ( ArrayList<TvModel> mData ) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder ( @NonNull ViewGroup viewGroup, int i ) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder ( @NonNull MyViewHolder myViewHolder, int i ) {
        TvModel series = getmData().get(i);
        myViewHolder.tvTitle.setText(series.getTitleTv());
        myViewHolder.tvDate.setText(series.getDateTv());
        myViewHolder.tvDesc.setText(series.getDescriptionTv());
        myViewHolder.tvRating.setRating((float) (series.getRateTv() / 2));
        Glide
                .with(mContext)
                .load("https://image.tmdb.org/t/p/original/"+mData.get(i).getImgPosterTv())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(myViewHolder.imgPoster);
    }

    @Override
    public int getItemCount () {
        return getmData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDate, tvDesc;
        ImageView imgPoster;
        RatingBar tvRating;
        public MyViewHolder ( @NonNull View itemView ) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title);
            tvDate = itemView.findViewById(R.id.date);
            tvDesc = itemView.findViewById(R.id.desc);
            tvRating = itemView.findViewById(R.id.rate);
            imgPoster = itemView.findViewById(R.id.img_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(mContext, TvDetaislActivity.class);
                    intent.putExtra("tv", mData.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}

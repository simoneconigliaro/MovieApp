package com.project.simoneconigliaro.movieapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.simoneconigliaro.movieapp.R;
import com.project.simoneconigliaro.movieapp.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private List<Trailer> mTrailers;
    private TrailerAdapterOnClickHandler mOnClickHandler;

    private static final String YOUTUBE_URL = "http://img.youtube.com/vi/";
    private static final String TRAILER_RESOLUTION = "/maxresdefault.jpg";

    public TrailerAdapter(TrailerAdapterOnClickHandler onClickHandler) {
        this.mOnClickHandler = onClickHandler;
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        String trailerUrl =  YOUTUBE_URL + mTrailers.get(position).getUrl() + TRAILER_RESOLUTION;
        Picasso.with(holder.trailerImageView.getContext()).load(trailerUrl).placeholder(R.drawable.placeholder_backdrop).placeholder(R.drawable.placeholder_backdrop).into(holder.trailerImageView);
    }

    @Override
    public int getItemCount() {
        if (mTrailers == null) return 0;
        return mTrailers.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        @BindView(R.id.iv_trailer)
        ImageView trailerImageView;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Trailer currentTrailer = mTrailers.get(getAdapterPosition());
            mOnClickHandler.onItemClick(currentTrailer);
        }
    }

    public interface TrailerAdapterOnClickHandler{
        void onItemClick(Trailer currentTrailer);
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }
}

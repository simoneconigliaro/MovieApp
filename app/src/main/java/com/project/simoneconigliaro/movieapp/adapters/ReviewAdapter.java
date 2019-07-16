package com.project.simoneconigliaro.movieapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.simoneconigliaro.movieapp.R;
import com.project.simoneconigliaro.movieapp.models.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    List<Review> mReviews;

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapterViewHolder holder, int position) {

        holder.authorTextView.setText(mReviews.get(position).getAuthor());
        holder.contentTextView.setText(mReviews.get(position).getContent());

        // hide and show the arrow image view depending on how many lines the content text view contains
        holder.contentTextView.post(new Runnable() {
            @Override
            public void run() {
                int lines = holder.contentTextView.getLineCount();
                if(lines <= 3) {
                    holder.arrowImageView.setVisibility(View.INVISIBLE);
                } else {
                    holder.contentTextView.setMaxLines(3);
                    holder.arrowImageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mReviews == null) return 0;
        return mReviews.size();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author)
        TextView authorTextView;
        @BindView(R.id.tv_content)
        TextView contentTextView;
        @BindView(R.id.iv_arrow)
        ImageView arrowImageView;

        boolean showReview = false;

        public ReviewAdapterViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // change image resource of arrow image view depending on whether the content review is fully shown or not
                    if(!showReview){
                        contentTextView.setMaxLines(Integer.MAX_VALUE);
                        arrowImageView.setImageResource(R.drawable.ic_arrow_drop_up_24dp);
                        showReview = true;
                    } else {
                        contentTextView.setMaxLines(3);
                        arrowImageView.setImageResource(R.drawable.ic_arrow_drop_down_dp);
                        showReview = false;
                    }
                }
            });
        }
    }

    public void setReviews(List<Review> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }
}

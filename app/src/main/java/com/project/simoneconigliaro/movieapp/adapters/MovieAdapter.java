package com.project.simoneconigliaro.movieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.simoneconigliaro.movieapp.models.Movie;
import com.project.simoneconigliaro.movieapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * {@link MovieAdapter} exposes a list of movies to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    // OnClickHandler used to make it easy for an Activity to interface with our RecyclerView
    MovieAdapterOnClickHandler mClickHandler;
    // ArrayList to store our movie objects
    private List<Movie> mMovies;

    // MovieAdapter constructor
    // This single handler is called when an item is clicked
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * onCreateViewHolder gets called when each new ViewHolder is created. .
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String currentMoviePicture = mMovies.get(position).getPicturePath();
        Picasso.with(movieAdapterViewHolder.mMovieImageView.getContext()).load(currentMoviePicture).placeholder(R.drawable.placeholder_poster).error(R.drawable.placeholder_poster).into(movieAdapterViewHolder.mMovieImageView);

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     */
    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    /**
     * This method is used to set the movies on a MovieAdapter if we've already
     * created one.
     */
    public void setMoviesData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    // The interface that receives onClick messages.
    public interface MovieAdapterOnClickHandler {
        void onListItemClick(Movie currentMovieObjectClicked);
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Declare and get reference of the ImageView to display movie's pictures
        @BindView(R.id.iv_movie)
        ImageView mMovieImageView;

        /**
         * Constructor for MovieAdapterViewHolder.
         * This single handler is called when an item is clicked.
         */
        public MovieAdapterViewHolder(View view) {
            super(view);
            // Bind ImageView
            ButterKnife.bind(this, view);
            // Call setOnClickListener on the view passed into the constructor
            view.setOnClickListener(this);
        }

        // This gets called by the child views during a click.
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie currentMovieObjectClicked = mMovies.get(adapterPosition);
            mClickHandler.onListItemClick(currentMovieObjectClicked);
        }
    }
}

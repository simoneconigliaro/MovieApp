package com.project.simoneconigliaro.movieapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.simoneconigliaro.movieapp.MainActivity;
import com.project.simoneconigliaro.movieapp.models.Movie;
import com.project.simoneconigliaro.movieapp.adapters.MovieAdapter;
import com.project.simoneconigliaro.movieapp.R;
import com.project.simoneconigliaro.movieapp.data.MovieContract.MovieEntry;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * FetchMoviesTask implements the methods of the abstract class AsyncTask
 * to fetch our data in background.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecycleView;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private Context context;
    private String pathMoviesToDisplay;
    private Parcelable mLayoutState;

    public FetchMoviesTask(Activity mainActivity, MovieAdapter movieAdapter, Parcelable layoutState) {
        context = mainActivity.getApplicationContext();
        this.mMovieAdapter = movieAdapter;
        this.mLayoutState = layoutState;
        mLoadingIndicator = (ProgressBar) mainActivity.findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) mainActivity.findViewById(R.id.tv_error_message_display);
        mRecycleView = (RecyclerView) mainActivity.findViewById(R.id.rv_movies);
    }

    // Show the Progress bar before the data is loaded
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRecycleView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    // Get movies data in background
    @Override
    protected List<Movie> doInBackground(String... params) {
        pathMoviesToDisplay = params[0];

        // Check
        if(pathMoviesToDisplay.equals(MainActivity.FAVORITE_MOVIES)){
            List<Movie> movies = loadFavoriteMovies();
            return movies;
        } else {
            URL moviesUrl = NetworkUtils.buildUrl(pathMoviesToDisplay);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(moviesUrl);
                List<Movie> movies = OpenMovieJsonUtils.getMoviesFromJson(jsonMovieResponse);
                return movies;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    // Set movies data into the MovieAdapter
    @Override
    protected void onPostExecute(List<Movie> movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecycleView.scrollToPosition(0);
        if (movies != null) {
            if (!movies.isEmpty()){
                mMovieAdapter.setMoviesData(movies);
                showMoviesData();
            } else {
                showErrorMessage();
            }
        } else {
            showErrorMessage();
        }
        if (mLayoutState != null){
            mRecycleView.getLayoutManager().onRestoreInstanceState(mLayoutState);
        }
    }

    // This method will make the view for the movies data visible and hide the error message
    public void showMoviesData() {
        mRecycleView.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    // This method will make the error message visible and hide the movie view
    public void showErrorMessage() {
        mRecycleView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        if(pathMoviesToDisplay.equals(MainActivity.FAVORITE_MOVIES)){
            mErrorMessageDisplay.setText(R.string.add_favorite_movies);
        }
    }

    // This method will get the list of favorite movies from the database
    public List<Movie> loadFavoriteMovies (){
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){

            String movieId  = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_ID));
            String title  = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_TITLE));
            String picturePath  = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_PICTURE_PATH));
            String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_BACKDROP_PATH));
            String overview  = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_OVERVIEW));
            String userRating  = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_USER_RATING));
            String releaseDate  = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_RELEASE_DATE));

            movies.add(new Movie(movieId, title, picturePath, backdropPath, overview, userRating, releaseDate));
        }
        return movies;
    }
}
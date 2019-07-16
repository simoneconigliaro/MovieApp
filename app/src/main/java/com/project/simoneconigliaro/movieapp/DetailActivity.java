package com.project.simoneconigliaro.movieapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.simoneconigliaro.movieapp.adapters.ReviewAdapter;
import com.project.simoneconigliaro.movieapp.adapters.TrailerAdapter;
import com.project.simoneconigliaro.movieapp.models.Movie;
import com.project.simoneconigliaro.movieapp.models.Review;
import com.project.simoneconigliaro.movieapp.models.Trailer;
import com.project.simoneconigliaro.movieapp.utilities.FetchReviewsTask;
import com.project.simoneconigliaro.movieapp.utilities.FetchTrailersTask;
import com.squareup.picasso.Picasso;
import com.project.simoneconigliaro.movieapp.data.MovieContract.MovieEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    // Declare and get reference of all our views to show our movie data to the user
    @BindView(R.id.tv_title_movie)
    TextView titleMovieTextView;
    @BindView(R.id.iv_movie_detail)
    ImageView pictureMovieImageView;
    @BindView(R.id.iv_background_image)
    ImageView backgroundImageView;
    @BindView(R.id.tv_overview)
    TextView overviewMovieTextView;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_release_date)
    TextView releaseDateMovieTextView;
    @BindView(R.id.fab_favourite)
    FloatingActionButton favouriteFab;
    @BindView(R.id.container_reviews)
    LinearLayout containerReviews;
    @BindView(R.id.container_trailers)
    LinearLayout containerTrailers;
    @BindView(R.id.scroll_view_detail)
    ScrollView mScrollView;
    @BindView(R.id.rv_trailers)
    RecyclerView trailersRecyclerView;
    @BindView(R.id.rv_reviews)
    RecyclerView reviewsRecyclerView;

    Movie mMovie;
    String mId;
    private int scrollPosition;

    private int mStatusMovie;
    private final static int MOVIE_UNSAVED = 0;
    private final static int MOVIE_SAVED = 1;

    public static final String SCROLL_POSITION_KEY = "scroll_position";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Bind Views
        ButterKnife.bind(this);

        // Get the intent that started this activity
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            // Extract the movie object from the intent and save its content in these strings
            mMovie = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_KEY);

            // Set all the views to show the movie data to the user
            titleMovieTextView.setText(mMovie.getTitle());
            Picasso.with(this).load(mMovie.getPicturePath()).placeholder(R.drawable.placeholder_poster).error(R.drawable.placeholder_poster).into(pictureMovieImageView);
            Log.d("DETAILS", "onCreate: " + mMovie.getPicturePath() + " " + mMovie.getBackdropPath());
            Picasso.with(this).load(mMovie.getBackdropPath()).placeholder(R.drawable.placeholder_backdrop).error(R.drawable.placeholder_backdrop).into(backgroundImageView);
            overviewMovieTextView.setText(mMovie.getOverview());
            releaseDateMovieTextView.setText(mMovie.getReleaseDate());
            float rating = (5 * Float.valueOf(mMovie.getUserRating()) / 10);
            ratingBar.setRating(rating);

            checkIfMovieIsAlreadyStored();

            favouriteFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStatusMovie == MOVIE_UNSAVED) {
                        addFavoriteMovie();
                    } else {
                        deleteFavoriteMovie();
                    }
                }
            });

            initTrailers();
            initReviews();
            restorePosition();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        scrollPosition = mScrollView.getScrollY();
        outState.putInt(SCROLL_POSITION_KEY, scrollPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        scrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initTrailers() {
        final TrailerAdapter trailerAdapter = new TrailerAdapter(this);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setAdapter(trailerAdapter);

        new FetchTrailersTask() {
            @Override
            protected void onPostExecute(List<Trailer> trailers) {
                if (trailers != null && !trailers.isEmpty()) {
                    trailerAdapter.setTrailers(trailers);
                    restorePosition();
                } else {
                    containerTrailers.setVisibility(View.GONE);
                }
            }
        }.execute(mMovie.getMovieId());
    }

    private void initReviews() {

        final ReviewAdapter reviewAdapter = new ReviewAdapter();
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setAdapter(reviewAdapter);

        new FetchReviewsTask() {

            @Override
            protected void onPostExecute(List<Review> reviews) {
                if (reviews != null && !reviews.isEmpty()) {
                    reviewAdapter.setReviews(reviews);
                    restorePosition();
                } else {
                    containerReviews.setVisibility(View.GONE);
                }
            }
        }.execute(mMovie.getMovieId());
    }

    private void addFavoriteMovie() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_MOVIE_ID, mMovie.getMovieId());
        contentValues.put(MovieEntry.COLUMN_TITLE, mMovie.getTitle());
        contentValues.put(MovieEntry.COLUMN_PICTURE_PATH, mMovie.getPicturePath());
        contentValues.put(MovieEntry.COLUMN_BACKDROP_PATH, mMovie.getBackdropPath());
        contentValues.put(MovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());
        contentValues.put(MovieEntry.COLUMN_USER_RATING, mMovie.getUserRating());
        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
        Uri uri = getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            mStatusMovie = MOVIE_SAVED;
            checkIfMovieIsAlreadyStored();
        } else {
            mStatusMovie = MOVIE_UNSAVED;
            checkIfMovieIsAlreadyStored();
        }
    }

    private void deleteFavoriteMovie() {
        if (mId != null) {
            Uri uri = MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(mId).build();
            getContentResolver().delete(uri, null, null);
            mStatusMovie = MOVIE_UNSAVED;
            changeIconFavoriteMovie(mStatusMovie);
        }
    }

    private void checkIfMovieIsAlreadyStored() {
        Cursor cursor = getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String idMovie = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_ID));
            if (mMovie.getMovieId().equals(idMovie)) {
                mId = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry._ID));
                mStatusMovie = MOVIE_SAVED;
                changeIconFavoriteMovie(mStatusMovie);
                return;
            }
        }
        mStatusMovie = MOVIE_UNSAVED;
        changeIconFavoriteMovie(mStatusMovie);
    }

    private void changeIconFavoriteMovie(int status) {
        switch (status) {
            case MOVIE_SAVED:
                favouriteFab.setImageResource(R.drawable.ic_favorite_full_24dp);
                break;
            case MOVIE_UNSAVED:
                favouriteFab.setImageResource(R.drawable.ic_favorite_border_24dp);
                break;
        }
    }

    private void restorePosition() {
        mScrollView.post(new Runnable() {
            public void run() {
                mScrollView.scrollTo(0, scrollPosition);
            }
        });
    }

    @Override
    public void onItemClick(Trailer currentTrailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(YOUTUBE_URL + currentTrailer.getUrl()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

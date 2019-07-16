package com.project.simoneconigliaro.movieapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.simoneconigliaro.movieapp.adapters.MovieAdapter;
import com.project.simoneconigliaro.movieapp.models.Movie;
import com.project.simoneconigliaro.movieapp.utilities.FetchMoviesTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    // Declare RecyclerView variable called mRecyclerView
    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;
    // Declare MovieAdapter variable called mMovieAdapter
    private MovieAdapter mMovieAdapter;
    // String used as a parameter to fetch popular movies or top-rated movies
    private String mMoviesToDisplay;
    // Parcelable used to store the state of the layout (recyclerview scroll position)
    public Parcelable mLayoutState;

    // String used to make the uri to fetch popular movies
    public static final String POPULAR_MOVIES = "popular";
    // String used to make the uri to fetch top rated movies
    public static final String TOP_RATED_MOVIES = "top_rated";
    // String used to make the uri to fetch favorite movies
    public static final String FAVORITE_MOVIES = "favorite";
    // String used as a key to retrieve previous path movies from saveInstanceState
    public static final String PATH_MOVIES_KEY = "path_movies";
    // String used as a key to retrieve previous layout state from saveInstanceState
    public static final String LAYOUT_STATE_KEY = "layout_state";
    // String used as a key to sent the movie selected to the detail activity
    public static final String MOVIE_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.main_activity_toolbar));
        ButterKnife.bind(this);

        initViews();

        // Check if mPathMoviesToDisplay isn't null, if so get previous value from the savedInstanceState
        if (mMoviesToDisplay == null) {
            mMoviesToDisplay = POPULAR_MOVIES;
        } else {
            mMoviesToDisplay = savedInstanceState.getString(PATH_MOVIES_KEY);
        }

        // Load movies data
        loadMoviesData(mMoviesToDisplay);
    }

    // Keep the category of movies to display when rotate the screen
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(PATH_MOVIES_KEY, mMoviesToDisplay);
        mLayoutState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable(LAYOUT_STATE_KEY, mLayoutState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMoviesToDisplay = savedInstanceState.getString(PATH_MOVIES_KEY);
        mLayoutState = savedInstanceState.getParcelable(LAYOUT_STATE_KEY);
        loadMoviesData(mMoviesToDisplay);
    }

    private void initViews() {
        // Set number of columns of the GridLayout depending on the rotation of the screen
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        // Initialize mMovieAdapter
        mMovieAdapter = new MovieAdapter(this);
        // Set the Adapter to the RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    /**
     * This method will get the path for popular movies or top-rated movies, and then pass it to some
     * background method to get the movies data in the background.
     */
    private void loadMoviesData(String path) {
        new FetchMoviesTask(this, mMovieAdapter, mLayoutState).execute(path);
        mLayoutState = null;
    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
     *
     * @param currentMovieObjectClicked The movie's picture that was clicked
     */
    @Override
    public void onListItemClick(Movie currentMovieObjectClicked) {
        // Intent to open the DetailActivity
        Intent intent = new Intent(this, DetailActivity.class);
        // Add the current movie object as parcel to the intent
        intent.putExtra(MOVIE_KEY, currentMovieObjectClicked);

        startActivity(intent);
    }

    // Options Menu for settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.settings, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    // Order the list of movies by popular or top-rated movies depending on the menu item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular_movies) {
            mMoviesToDisplay = POPULAR_MOVIES;
            loadMoviesData(mMoviesToDisplay);
            return true;
        }
        if (id == R.id.top_rated_movies) {
            mMoviesToDisplay = TOP_RATED_MOVIES;
            loadMoviesData(mMoviesToDisplay);
            return true;
        }
        if (id == R.id.favorite_movies) {
            mMoviesToDisplay = FAVORITE_MOVIES;
            loadMoviesData(mMoviesToDisplay);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMoviesToDisplay.equals(FAVORITE_MOVIES)){
            loadMoviesData(FAVORITE_MOVIES);
        }
    }
}

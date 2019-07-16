package com.project.simoneconigliaro.movieapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Simone on 05/11/2017.
 */

public class MovieContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.project.simoneconigliaro.movieapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "movies" directory
    public static final String PATH_MOVIES = "movies";

    /* MovieEntry is an inner class that defines the contents of the movie table */
    public static final class MovieEntry implements BaseColumns {

        // MovieEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        // Movie table and column names
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movieId";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_PICTURE_PATH = "picturePath";

        public static final String COLUMN_BACKDROP_PATH = "backdropPath";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_USER_RATING = "userRating";

        public static final String COLUMN_RELEASE_DATE = "releaseDate";

    }

}


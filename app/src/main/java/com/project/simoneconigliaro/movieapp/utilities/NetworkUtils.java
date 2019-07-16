package com.project.simoneconigliaro.movieapp.utilities;

import android.net.Uri;
import android.util.Log;

import com.project.simoneconigliaro.movieapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the API from themoviedb.org
 */
public class NetworkUtils {

    final static String KEY_PARAM = "api_key";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL =
            "https://api.themoviedb.org/3/movie/";
    /* personal key to access to themoviedb API */
    private static final String key = BuildConfig.THE_MOVIE_DB_API_KEY;

    /**
     * Builds the URL used to get the data from the server. This path is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param pathMovies indicates what data we want to get back, popular or top rated movies.
     * @return The URL to use to query the server.
     */
    public static URL buildUrl(String pathMovies) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(pathMovies)
                .appendQueryParameter(KEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * Builds the URL used to get the video trailers or reviews depending on the path of a single movie
     * @param id of a single movie
     * @param path it can be "movies" or "reviews" depending on what data we want to get back
     * @return
     */
    public static URL buildTrailersOrReviewsUrl(String id, String path) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(id)
                .appendPath(path)
                .appendQueryParameter(KEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
        return url;

    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}

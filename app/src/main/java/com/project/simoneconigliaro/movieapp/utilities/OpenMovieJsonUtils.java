package com.project.simoneconigliaro.movieapp.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.project.simoneconigliaro.movieapp.models.Movie;
import com.project.simoneconigliaro.movieapp.models.Review;
import com.project.simoneconigliaro.movieapp.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility function to handle themoviedb JSON data.
 */
public class OpenMovieJsonUtils {

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Movie> getMoviesFromJson(String moviesJsonStr) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(moviesJsonStr)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject moviesJson = new JSONObject(moviesJsonStr);

            // Extract the JSONArray associated with the key called "results",
            JSONArray results = moviesJson.getJSONArray("results");

            // Loop through the JSONArray results to get data about every movie and store them in the list of movies.
            for (int i = 0; i < results.length(); i++) {

                // Get the JSONObject at the current position in the results JSONArray and store it the currentMovie JSONObject
                JSONObject currentMovie = results.getJSONObject(i);

                // Get the String value with key "id" and store it in the string movieId.
                String movieId = currentMovie.optString("id");

                // Get the String value with key "title" and store it in the string title.
                String title = currentMovie.optString("title");

                // Get the String value with key "poster_path" and store it in the string picturePath.
                String picturePath = "http://image.tmdb.org/t/p/w342" + currentMovie.optString("poster_path");

                // Get the String value with key "backdrop_path" and store it in the string backdropPath.
                String backdropPath = "http://image.tmdb.org/t/p/w780" + currentMovie.optString("backdrop_path");

                // Get the String value with key "overview" and store it in the string overview.
                String overview = currentMovie.optString("overview");

                // Get the String value with key "vote_average" and store it in the string userRating.
                String userRating = currentMovie.getString("vote_average");

                // Get the String value with key "release_date" and store it in the string releaseDate.
                String releaseDate = currentMovie.optString("release_date").substring(0,4);

                // Add those data about a single movie to the list of movies.
                movies.add(new Movie(movieId, title, picturePath, backdropPath, overview, userRating, releaseDate));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("OpenMovieJsonUtils", "Problem parsing the movie JSON results", e);
        }

        // return the list of movies
        return movies;
    }

    /**
     * Return a list of {@link Trailer} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Trailer> getTrailersFromJson(String trailersJsonStr) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(trailersJsonStr)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding trailers to
        List<Trailer> trailers = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject moviesJson = new JSONObject(trailersJsonStr);

            // Extract the JSONArray associated with the key called "results",
            JSONArray results = moviesJson.getJSONArray("results");

            // Loop through the JSONArray results to get data about every trailer and store them in the list of trailers.
            for (int i = 0; i < results.length(); i++) {

                // Get the JSONObject at the current position in the results JSONArray and store it the currentTrailer JSONObject
                JSONObject currentTrailer = results.getJSONObject(i);

                // Get the String value with key "name" and store it in the string title.
                String title = currentTrailer.optString("name");

                // Get the String value with key "key" and add the complete path to open a youtube video and store it in the string url.
                String url = currentTrailer.optString("key");

                // Add those information about a single trailer to the list of trailer
                trailers.add(new Trailer(title, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("OpenMovieJsonUtils", "Problem parsing the trailer JSON results", e);
        }

        // return the list of trailers
        return trailers;
    }

    /**
     * Return a list of {@link Review} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Review> getReviewsFromJson(String reviewsJsonStr) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(reviewsJsonStr)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding reviews to
        List<Review> reviews = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject moviesJson = new JSONObject(reviewsJsonStr);

            // Extract the JSONArray associated with the key called "results",
            JSONArray results = moviesJson.getJSONArray("results");

            // Loop through the JSONArray results to get data about every reviews and store them in the list of reviews.
            for (int i = 0; i < results.length(); i++) {

                // Get the JSONObject at the current position in the results JSONArray and store it the currentReview JSONObject
                JSONObject currentReview = results.getJSONObject(i);

                // Get the String value with key "author" and store it in the string author.
                String author = currentReview.optString("author");

                // Get the String value with key "content" and store it in the string content.
                String content = currentReview.optString("content");

                // Add those information about a single review to the list of review
                reviews.add(new Review(author, content));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("OpenMovieJsonUtils", "Problem parsing the review JSON results", e);
        }

        // return the list of reviews
        return reviews;
    }

}

package com.project.simoneconigliaro.movieapp.utilities;

import android.os.AsyncTask;

import com.project.simoneconigliaro.movieapp.models.Review;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * FetchReviewsTask implements the methods of the abstract class AsyncTask
 * to fetch our reviews in background.
 */
public class FetchReviewsTask extends AsyncTask<String, Void, List<Review>> {

    @Override
    protected List<Review> doInBackground(String... params) {
        String movieId = params[0];
        URL reviewsUrl = NetworkUtils.buildTrailersOrReviewsUrl(movieId, "reviews");
        try {
            String jsonReviewsResponse = NetworkUtils.getResponseFromHttpUrl(reviewsUrl);
            List<Review> reviews = OpenMovieJsonUtils.getReviewsFromJson(jsonReviewsResponse);
            return reviews;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

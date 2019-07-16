package com.project.simoneconigliaro.movieapp.utilities;

import android.os.AsyncTask;
import com.project.simoneconigliaro.movieapp.models.Trailer;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * FetchTrailersTask implements the methods of the abstract class AsyncTask
 * to fetch our trailers in background.
 */
public class FetchTrailersTask extends AsyncTask<String, Void, List<Trailer>> {

    @Override
    protected List<Trailer> doInBackground(String... params) {
        String movieId = params[0];
        URL trailersUrl = NetworkUtils.buildTrailersOrReviewsUrl(movieId, "videos");
        try {
            String jsonTrailersResponse = NetworkUtils.getResponseFromHttpUrl(trailersUrl);
            List<Trailer> trailers = OpenMovieJsonUtils.getTrailersFromJson(jsonTrailersResponse);
            return trailers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

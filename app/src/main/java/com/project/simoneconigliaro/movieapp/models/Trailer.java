package com.project.simoneconigliaro.movieapp.models;

/**
 * An {@link Trailer} object contains information related to a single trailer.
 */

public class Trailer {

    /* Title of the trailer */
    private String mTitle;
    /* Url of the trailer */
    private String mUrl;

    /**
     * Constructs a new {@link Trailer} object
     */
    public Trailer (String title, String url) {

        mTitle = title;
        mUrl = url;
    }

    /* return the title of the trailer */
    public String getTitle(){return mTitle;}
    /* return the url of the trailer */
    public String getUrl(){return mUrl;}

}

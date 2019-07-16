package com.project.simoneconigliaro.movieapp.models;

/**
 * An {@link Review} object contains information related to a single review.
 */

public class Review {

    /* Author of the Review */
    private String mAuthor;
    /* Content of the Review */
    private String mContent;

    /**
     * Constructs a new {@link Review} object
     */
    public Review (String author, String content) {

        mAuthor = author;
        mContent = content;
    }

    /* return the author of the review */
    public String getAuthor(){return mAuthor;}
    /* return the content of the review */
    public String getContent(){return mContent;}

}

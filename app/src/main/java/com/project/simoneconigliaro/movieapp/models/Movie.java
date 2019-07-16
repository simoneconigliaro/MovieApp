package com.project.simoneconigliaro.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An {@link Movie} object contains information related to a single movie.
 */

public class Movie implements Parcelable{

    /* Id of the movie */
    private String mMovieId;
    /* Title of the movie */
    private String mTitle;
    /* Picture path of the movie */
    private String mPicturePath;
    /* Picture path background image of the movie */
    private String mBackdropPath;
    /* Overview of the movie */
    private String mOverview;
    /* User Rating of the movie */
    private String mUserRating;
    /* Release Date of the movie */
    private String mReleaseDate;

    /**
     * Constructs a new {@link Movie} object
     */
    public Movie (String movieId, String title, String picturePath, String backdropPath, String overview, String userRating, String releaseDate) {

        mMovieId = movieId;
        mTitle = title;
        mPicturePath = picturePath;
        mBackdropPath = backdropPath;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

    /**
     * Constructor to use when re-constructing object
     * from a parcel.
     *
     * @param in a parcel from which to read this object.
     */
    public Movie(Parcel in) {
        this.mMovieId = in.readString();
        this.mTitle = in.readString();
        this.mPicturePath = in.readString();
        this.mBackdropPath = in.readString();
        this.mOverview = in.readString();
        this.mUserRating = in.readString();
        this.mReleaseDate = in.readString();
    }

    /* return the title of the movie */
    public String getMovieId(){return mMovieId;}
    /* return the title of the movie */
    public String getTitle(){return mTitle;}
    /* return the picture path of the movie */
    public String getPicturePath(){return mPicturePath;}
    /* return the picture of the background image */
    public String getBackdropPath(){return mBackdropPath;}
    /* return the overview of the movie */
    public String getOverview(){return mOverview;}
    /* return the user rating of the movie */
    public String getUserRating(){return mUserRating;}
    /* return the release date of the movie */
    public String getReleaseDate(){return mReleaseDate;}

    @Override
    public int describeContents() {
        return 0;
    }

    // Write movies fields to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mPicturePath);
        dest.writeString(mBackdropPath);
        dest.writeString(mOverview);
        dest.writeString(mUserRating);
        dest.writeString(mReleaseDate);
    }

    // Parcelable.Creator that is used to create an instance of the class from the Parcel data
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}

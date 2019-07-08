package com.firmanjabar.submission3.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MovieModel implements Parcelable {

    private int movieID;
    private String title, date, description, imgPoster, imgBD;
    private double rate;

    public MovieModel ( JSONObject obj ) {
        try{
            Integer movieID = obj.getInt("id");
            String title = obj.getString("title");
            String date = obj.getString("release_date");
            String description = obj.getString("overview");
            String imgPoster = obj.getString("poster_path");
            String imgBD = obj.getString("backdrop_path");
            double rate = obj.getDouble("vote_average");

            this.movieID = movieID;
            this.title = title;
            this.date = date;
            this.description = description;
            this.imgPoster = imgPoster;
            this.imgBD = imgBD;
            this.rate = rate;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getMovieID () {
        return movieID;
    }

    public void setMovieID ( int movieID ) {
        this.movieID = movieID;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getDate () {
        return date;
    }

    public void setDate ( String date ) {
        this.date = date;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public String getImgPoster () {
        return imgPoster;
    }

    public void setImgPoster ( String imgPoster ) {
        this.imgPoster = imgPoster;
    }

    public String getImgBD () {
        return imgBD;
    }

    public void setImgBD ( String imgBD ) {
        this.imgBD = imgBD;
    }

    public double getRate () {
        return rate;
    }

    public void setRate ( double rate ) {
        this.rate = rate;
    }


    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel ( Parcel dest, int flags ) {
        dest.writeInt(this.movieID);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.imgPoster);
        dest.writeString(this.imgBD);
        dest.writeDouble(this.rate);
    }

    protected MovieModel ( Parcel in ) {
        this.movieID = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.imgPoster = in.readString();
        this.imgBD = in.readString();
        this.rate = in.readDouble();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel ( Parcel source ) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray ( int size ) {
            return new MovieModel[size];
        }
    };
}
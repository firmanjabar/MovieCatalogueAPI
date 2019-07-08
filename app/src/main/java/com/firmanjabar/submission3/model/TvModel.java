package com.firmanjabar.submission3.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvModel implements Parcelable {

    private int idTv;
    private String titleTv, dateTv, descriptionTv, imgPosterTv, imgBdTv;
    private double rateTv;

    public TvModel ( JSONObject obj ) {
        try{
            Integer id = obj.getInt("id");
            String title = obj.getString("name");
            String date = obj.getString("first_air_date");
            String description = obj.getString("overview");
            String imgPoster = obj.getString("poster_path");
            String imgBD = obj.getString("backdrop_path");
            double rate = obj.getDouble("vote_average");

            this.idTv = id;
            this.titleTv = title;
            this.dateTv = date;
            this.descriptionTv = description;
            this.imgPosterTv = imgPoster;
            this.imgBdTv = imgBD;
            this.rateTv = rate;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getIdTv () {
        return idTv;
    }

    public void setIdTv ( int idTv ) {
        this.idTv = idTv;
    }

    public String getTitleTv () {
        return titleTv;
    }

    public void setTitleTv ( String titleTv ) {
        this.titleTv = titleTv;
    }

    public String getDateTv () {
        return dateTv;
    }

    public void setDateTv ( String dateTv ) {
        this.dateTv = dateTv;
    }

    public String getDescriptionTv () {
        return descriptionTv;
    }

    public void setDescriptionTv ( String descriptionTv ) {
        this.descriptionTv = descriptionTv;
    }

    public String getImgPosterTv () {
        return imgPosterTv;
    }

    public void setImgPosterTv ( String imgPosterTv ) {
        this.imgPosterTv = imgPosterTv;
    }

    public String getImgBdTv () {
        return imgBdTv;
    }

    public void setImgBdTv ( String imgBdTv ) {
        this.imgBdTv = imgBdTv;
    }

    public double getRateTv () {
        return rateTv;
    }

    public void setRateTv ( double rateTv ) {
        this.rateTv = rateTv;
    }


    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel ( Parcel dest, int flags ) {
        dest.writeInt(this.idTv);
        dest.writeString(this.titleTv);
        dest.writeString(this.dateTv);
        dest.writeString(this.descriptionTv);
        dest.writeString(this.imgPosterTv);
        dest.writeString(this.imgBdTv);
        dest.writeDouble(this.rateTv);
    }

    protected TvModel ( Parcel in ) {
        this.idTv = in.readInt();
        this.titleTv = in.readString();
        this.dateTv = in.readString();
        this.descriptionTv = in.readString();
        this.imgPosterTv = in.readString();
        this.imgBdTv = in.readString();
        this.rateTv = in.readDouble();
    }

    public static final Parcelable.Creator<TvModel> CREATOR = new Parcelable.Creator<TvModel>() {
        @Override
        public TvModel createFromParcel ( Parcel source ) {
            return new TvModel(source);
        }

        @Override
        public TvModel[] newArray ( int size ) {
            return new TvModel[size];
        }
    };
}

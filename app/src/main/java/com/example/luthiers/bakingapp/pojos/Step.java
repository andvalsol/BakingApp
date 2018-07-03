package com.example.luthiers.bakingapp.pojos;


import android.os.Parcel;
import android.os.Parcelable;

/*
* This pojo needs:
* -> id: type: int
* -> shortDescription: type: String
* -> description: type: String
* -> videoURL: type: String
* -> thumbnailURL: type: String
*/
public class Step implements Parcelable{
    
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;
    
    //Add a public constructor
    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }
    
    private Step(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }
    
    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }
        
        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    
    public int getId() {
        return mId;
    }
    
    public void setId(int id) {
        mId = id;
    }
    
    public String getShortDescription() {
        return mShortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public void setDescription(String description) {
        mDescription = description;
    }
    
    public String getVideoURL() {
        return mVideoURL;
    }
    
    public void setVideoURL(String videoURL) {
        mVideoURL = videoURL;
    }
    
    public String getThumbnailURL() {
        return mThumbnailURL;
    }
    
    public void setThumbnailURL(String thumbnailURL) {
        mThumbnailURL = thumbnailURL;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoURL);
        dest.writeString(mThumbnailURL);
    }
}
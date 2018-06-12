package com.example.luthiers.bakingapp.pojos;


/*
* This pojo needs:
* -> id: type: int
* -> shortDescription: type: String
* -> description: type: String
* -> videoURL: type: String
* -> thumbnailURL: type: String
*/
public class Step {
    
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
    
    //Add all getters and setters
    
    
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
    
}
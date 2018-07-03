package com.example.luthiers.bakingapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/*
* This pojo needs:
* -> quantity: type: float
* -> measure: type: String
* -> ingredient: type: String
*/
public class Ingredient implements Parcelable {
    
    private Float mQuantity;
    private String mMeasure;
    private String mIngredient;
    
    //Add a public constructor
    public Ingredient(Float quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }
    
    //Add all getters and setters
    
    private Ingredient(Parcel in) {
        if (in.readByte() == 0) {
            mQuantity = null;
        } else {
            mQuantity = in.readFloat();
        }
        mMeasure = in.readString();
        mIngredient = in.readString();
    }
    
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }
        
        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    
    public Float getQuantity() {
        return mQuantity;
    }
    
    public void setQuantity(Float quantity) {
        mQuantity = quantity;
    }
    
    public String getMeasure() {
        return mMeasure;
    }
    
    public void setMeasure(String measure) {
        mMeasure = measure;
    }
    
    public String getIngredient() {
        return mIngredient;
    }
    
    public void setIngredient(String ingredient) {
        mIngredient = ingredient;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        if (mQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(mQuantity);
        }
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
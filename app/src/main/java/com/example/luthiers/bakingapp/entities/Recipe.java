package com.example.luthiers.bakingapp.entities;


/*
 * The pojo needs:
 * -> id: type: int
 * -> name: type: String
 * -> ingredients: type: List<Ingredient>
 * -> steps: type: List<Step>
 * -> servings: type: int
 * -> imageUrl: type: String
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.example.luthiers.bakingapp.pojos.Ingredient;
import com.example.luthiers.bakingapp.pojos.Step;

import java.util.List;

public class Recipe implements Parcelable {
    
    private int mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private int mServings;
    private String mImageUrl;
    
    //Add a public constructor
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String imageUrl) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImageUrl = imageUrl;
    }
    
    private Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        mSteps = in.createTypedArrayList(Step.CREATOR);
        mServings = in.readInt();
        mImageUrl = in.readString();
    }
    
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }
        
        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    
    public int getId() {
        return mId;
    }
    
    public void setId(int id) {
        mId = id;
    }
    
    public String getName() {
        return mName;
    }
    
    public void setName(String name) {
        mName = name;
    }
    
    public List<Ingredient> getIngredients() {
        return mIngredients;
    }
    
    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
    
    public List<Step> getSteps() {
        return mSteps;
    }
    
    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }
    
    public int getServings() {
        return mServings;
    }
    
    public void setServings(int servings) {
        mServings = servings;
    }
    
    public String getImageUrl() {
        return mImageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
        dest.writeInt(mServings);
        dest.writeString(mImageUrl);
    }
}
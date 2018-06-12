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

import com.example.luthiers.bakingapp.pojos.Ingredient;
import com.example.luthiers.bakingapp.pojos.Step;

import java.util.List;

public class Recipe {
    
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
    
    //Add all the corresponding getters and setters
    
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
    
}